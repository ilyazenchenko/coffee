package ru.zenchenko.coffee.controllers;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zenchenko.coffee.model.Ingredient;
import ru.zenchenko.coffee.services.IngredientsService;

import java.util.List;
import java.util.Map;

@Data
@RestController
@RequestMapping("/ingredients")
public class IngredientsController {

    private final IngredientsService ingredientsService;

    /**
     *  получить кол-во всех ингредиентов в машине
     * @return список ингредиентов
     */
    @GetMapping
    public ResponseEntity<List<Ingredient>> getAll() {
        return new ResponseEntity<>(ingredientsService.findAll(), HttpStatus.OK);
    }

    /**
     * Перезаполнить все ингредиенты
     * @return сообщение
     */
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> refillAll() {
        ingredientsService.refillAll();
        return Map.of("message", "refilled");
    }
}
