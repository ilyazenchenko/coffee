package ru.zenchenko.coffee.services;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.zenchenko.coffee.model.Ingredient;
import ru.zenchenko.coffee.model.Receipt;
import ru.zenchenko.coffee.model.ReceiptIngredient;
import ru.zenchenko.coffee.repositories.IngredientsRepository;
import ru.zenchenko.coffee.repositories.ReceiptsRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Data
public class OrderService {

    private final ReceiptsRepository receiptsRepository;

    private final IngredientsRepository ingredientsRepository;

    /**
     * проверяем, есть ли переданное имя в рецептах
     */
    public boolean isNameValid(String name) {
        return receiptsRepository.findAll().stream().map(Receipt::getName).toList().contains(name);
    }

    /**
     * заказ кофе, которое есть в рецептах
     * @param name - название кофе
     * @return - результат true - успешно
     */
    public boolean orderValidCoffee(String name) {
        //ищем рецепт по имени
        Receipt receipt = receiptsRepository.findByName(name).get();

        //ингредиенты рецепта
        List<ReceiptIngredient> receiptIngredients = receipt.getReceiptIngredients();
        //ингредиенты в машине
        List<Ingredient> allIngredients = ingredientsRepository.findAll();

        boolean result = checkIngredientsAndMakeACoffee(receiptIngredients, allIngredients);

        if (!result) return false;

        //если получилось сделать кофе,
        //сохраняем измененное кол-во ингредиентов
        ingredientsRepository.saveAll(allIngredients);

        return true;
    }

    private boolean checkIngredientsAndMakeACoffee(List<ReceiptIngredient> receiptIngredients, List<Ingredient> allIngredients) {
        if (receiptIngredients == null || allIngredients == null) return false;

        //проверяем что в машине достаточно ингредиентов
        if (!checkIngredientsAmount(receiptIngredients, allIngredients)) return false;

        //сортируем по порядку в рецепте
        receiptIngredients = receiptIngredients.stream().sorted(Comparator.comparingInt(ReceiptIngredient::getOrderInReceipt)).toList();

        //вычитаем ингредиенты из машинки
        for (ReceiptIngredient receiptIngredient : receiptIngredients) {
            Ingredient ingredient = allIngredients.stream().filter(i -> Objects.equals(i.getId(), receiptIngredient.getIngredient().getId())).findAny().orElseThrow();
            ingredient.setStock(ingredient.getStock() - receiptIngredient.getAmount());
        }

        return true;
    }

    private boolean checkIngredientsAmount(List<ReceiptIngredient> receiptIngredients, List<Ingredient> allIngredients) {
        //удобнее проверять наличие с Map
        Map<Long, Double> allIngredientsAmounts = allIngredients.stream().collect(Collectors.toMap(Ingredient::getId, Ingredient::getStock));

        for (ReceiptIngredient receiptIngredient : receiptIngredients) {
            Double receiptAmount = receiptIngredient.getAmount();
            Double stockAmount = allIngredientsAmounts.get(receiptIngredient.getIngredient().getId());

            if (receiptAmount > stockAmount)
                return false;

        }

        return true;
    }
}
