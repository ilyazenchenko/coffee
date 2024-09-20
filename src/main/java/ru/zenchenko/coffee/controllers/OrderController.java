package ru.zenchenko.coffee.controllers;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.zenchenko.coffee.services.OrderService;
import ru.zenchenko.coffee.services.ReceiptsService;

import java.util.Map;

@RestController
@Data
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    private final ReceiptsService receiptsService;

    @GetMapping
    public ResponseEntity<Map<String, String>> orderCoffee(@RequestParam("name") String name) {
        if (!orderService.isNameValid(name)) {
            return new ResponseEntity<>(Map.of("error", "name not found"), HttpStatus.NOT_FOUND);
        }
        boolean isSuccess = orderService.orderValidCoffee(name);
        if (isSuccess) {
            return new ResponseEntity<>(Map.of("message", "take your " + name + "! :)"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("message", "not enough ingredients! :("), HttpStatus.CONFLICT);
        }
    }
}
