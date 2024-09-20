package ru.zenchenko.coffee.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import ru.zenchenko.coffee.model.Receipt;
import ru.zenchenko.coffee.model.ReceiptIngredient;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReceiptDTO {

    private Long id;

    private String name;

    private Integer timesOrdered;

    @JsonProperty("receiptIngredients")
    private List<ReceiptIngredientDTO> receiptIngredientsDTOs = null;

    public ReceiptDTO(Receipt receipt) {
        this.id = receipt.getId();
        this.name = receipt.getName();
        this.timesOrdered = receipt.getTimesOrdered();
        if (receipt.getReceiptIngredients() != null) {
            receiptIngredientsDTOs = new ArrayList<>();
            receipt.getReceiptIngredients().forEach(receiptIngredient -> {
                receiptIngredientsDTOs.add(new ReceiptIngredientDTO(receiptIngredient));
            });
        }
    }
}
