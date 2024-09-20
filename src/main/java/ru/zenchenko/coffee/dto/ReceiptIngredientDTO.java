package ru.zenchenko.coffee.dto;

import jakarta.persistence.*;
import lombok.Data;
import ru.zenchenko.coffee.model.Ingredient;
import ru.zenchenko.coffee.model.Receipt;
import ru.zenchenko.coffee.model.ReceiptIngredient;

import java.util.Optional;

@Data
public class ReceiptIngredientDTO {

    private Long id;

    private Long receiptID;

    private Long ingredientID;

    private Double amount;

    private Integer orderInReceipt;

    public ReceiptIngredientDTO(ReceiptIngredient receiptIngredient) {
        this.id = receiptIngredient.getId();
        this.receiptID = Optional.ofNullable(receiptIngredient.getReceipt()).orElseThrow().getId();
        this.ingredientID = Optional.ofNullable(receiptIngredient.getIngredient()).orElseThrow().getId();
        this.amount = receiptIngredient.getAmount();
        this.orderInReceipt = receiptIngredient.getOrderInReceipt();
    }
}
