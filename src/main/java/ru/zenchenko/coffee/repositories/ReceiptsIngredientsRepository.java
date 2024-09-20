package ru.zenchenko.coffee.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zenchenko.coffee.model.ReceiptIngredient;

import java.util.List;

@Repository
public interface ReceiptsIngredientsRepository extends JpaRepository<ReceiptIngredient, Long> {
}
