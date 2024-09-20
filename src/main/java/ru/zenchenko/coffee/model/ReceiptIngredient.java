package ru.zenchenko.coffee.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ReceiptIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "receipt_id", referencedColumnName = "id")
    private Receipt receipt;

    @ManyToOne
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
    private Ingredient ingredient;

    private Double amount;

    private Integer orderInReceipt;

}
