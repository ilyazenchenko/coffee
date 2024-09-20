package ru.zenchenko.coffee.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Data
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private Double stock;

    @Setter(AccessLevel.NONE)
    private Double maxStock;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    public Ingredient() {

    }

    public enum Unit {
        gr, ml
    }

    public Ingredient(Long id) {
        this.id = id;
    }
}
