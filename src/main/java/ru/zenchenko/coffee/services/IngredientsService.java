package ru.zenchenko.coffee.services;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.zenchenko.coffee.model.Ingredient;
import ru.zenchenko.coffee.repositories.IngredientsRepository;

import java.util.List;

@Service
@Data
public class IngredientsService {

    private final IngredientsRepository ingredientsRepository;

    public List<Ingredient> findAll() {
        return ingredientsRepository.findAll();
    }

    public void refillAll() {
        List<Ingredient> ingredients = ingredientsRepository.findAll();
        ingredients.forEach(ingredient -> ingredient.setStock(ingredient.getMaxStock()));
        ingredientsRepository.saveAll(ingredients);
    }
}
