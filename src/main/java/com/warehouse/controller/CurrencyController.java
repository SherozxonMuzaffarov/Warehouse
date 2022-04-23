package com.warehouse.controller;

import com.warehouse.entity.Currency;
import com.warehouse.entity.Supplier;
import com.warehouse.payload.Result;
import com.warehouse.payload.SupplierDto;
import com.warehouse.service.CurrencyService;
import com.warehouse.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {

    @Autowired
    CurrencyService currencyService;

    @PostMapping("/save")
    public ResponseEntity<Result> addCurrency(@Valid @RequestBody Currency currency)  {
        return currencyService.addCurrency(currency);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getCurrencyById(@PathVariable("id") Long id) {
        return currencyService.getCurrencyById(id);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Currency>> getAllCurrency() {
        return currencyService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteCurrencyById(@PathVariable("id") Long id){
        return currencyService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Result> updateCurrency(@Valid @RequestBody  Currency currency, @PathVariable("id") Long id) {
        return currencyService.updateCurrency(id, currency);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
