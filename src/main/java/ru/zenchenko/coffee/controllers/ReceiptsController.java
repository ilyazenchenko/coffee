package ru.zenchenko.coffee.controllers;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zenchenko.coffee.dto.ReceiptDTO;
import ru.zenchenko.coffee.model.Receipt;
import ru.zenchenko.coffee.services.ReceiptsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Data
@RequestMapping("/receipts")
public class ReceiptsController {

    private final ReceiptsService receiptsService;

    @GetMapping
    public ResponseEntity<List<ReceiptDTO>> getAll() {
        List<ReceiptDTO> receiptDTOS = receiptsService.findAll().stream().map(ReceiptDTO::new).toList();
        return new ResponseEntity<>(receiptDTOS, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> addReceipt(@RequestBody Receipt receipt) {
        receiptsService.addReceipt(receipt);
        return Map.of("message", "created");
    }
}
