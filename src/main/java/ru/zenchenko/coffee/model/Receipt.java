package ru.zenchenko.coffee.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Data
@Entity
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer timesOrdered;

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL)
    private List<ReceiptIngredient> receiptIngredients;
}
