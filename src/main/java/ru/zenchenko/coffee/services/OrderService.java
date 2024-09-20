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

    public boolean isNameValid(String name) {
        return receiptsRepository.findAll().stream().map(Receipt::getName).toList().contains(name);
    }

    public boolean orderValidCoffee(String name) {
        Receipt receipt = receiptsRepository.findByName(name).get();

        List<ReceiptIngredient> receiptIngredients = receipt.getReceiptIngredients();
        List<Ingredient> allIngredients = ingredientsRepository.findAll();

        boolean result = checkIngredientsAndMakeACoffee(receiptIngredients, allIngredients);

        if (!result) return false;

        ingredientsRepository.saveAll(allIngredients);

        return true;
    }

    private boolean checkIngredientsAndMakeACoffee(List<ReceiptIngredient> receiptIngredients, List<Ingredient> allIngredients) {
        if (receiptIngredients == null || allIngredients == null) return false;

        if (!checkIngredientsAmount(receiptIngredients, allIngredients)) return false;

        receiptIngredients = receiptIngredients.stream().sorted(Comparator.comparingInt(ReceiptIngredient::getOrderInReceipt)).toList();

        for (ReceiptIngredient receiptIngredient : receiptIngredients) {
            Ingredient ingredient = allIngredients.stream().filter(i -> Objects.equals(i.getId(), receiptIngredient.getIngredient().getId())).findAny().orElseThrow();
            ingredient.setStock(ingredient.getStock() - receiptIngredient.getAmount());
        }

        return true;
    }

    private boolean checkIngredientsAmount(List<ReceiptIngredient> receiptIngredients, List<Ingredient> allIngredients) {
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
