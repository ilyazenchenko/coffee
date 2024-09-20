package ru.zenchenko.coffee.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zenchenko.coffee.model.Ingredient;
import ru.zenchenko.coffee.model.Receipt;

@Repository
public interface IngredientsRepository extends JpaRepository<Ingredient, Long> {
}
