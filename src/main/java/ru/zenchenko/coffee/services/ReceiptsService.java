package ru.zenchenko.coffee.services;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;
import ru.zenchenko.coffee.dto.ReceiptDTO;
import ru.zenchenko.coffee.model.Ingredient;
import ru.zenchenko.coffee.model.Receipt;
import ru.zenchenko.coffee.model.ReceiptIngredient;
import ru.zenchenko.coffee.repositories.IngredientsRepository;
import ru.zenchenko.coffee.repositories.ReceiptsIngredientsRepository;
import ru.zenchenko.coffee.repositories.ReceiptsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class ReceiptsService {

    private final ReceiptsRepository receiptsRepository;

    private final IngredientsRepository ingredientsRepository;

    private final ReceiptsIngredientsRepository receiptsIngredientsRepository;

    private final EntityManager entityManager;

    public List<Receipt> findAll() {
        return receiptsRepository.findAll();
    }

    public void addReceipt(Receipt receipt) {
        receipt.setTimesOrdered(0);
        receipt.getReceiptIngredients().forEach(receiptIngredient -> {
            Ingredient ingredient = ingredientsRepository.findById(receiptIngredient.getIngredient().getId()).orElseThrow(RuntimeException::new);
            receiptIngredient.setIngredient(ingredient);
            receiptIngredient.setReceipt(receipt);
        });
        receiptsRepository.save(receipt);
    }
}
