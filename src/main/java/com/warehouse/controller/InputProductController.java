package com.warehouse.controller;

import com.warehouse.entity.Input;
import com.warehouse.entity.InputProduct;
import com.warehouse.payload.InputProductDto;
import com.warehouse.payload.Result;
import com.warehouse.service.InputProductService;
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
@RequestMapping("/api/inputProduct")
public class InputProductController {

    @Autowired
    InputProductService inputProductService;

    @PostMapping("/add")
    public ResponseEntity<Result> addInputProduct(@Valid @RequestBody InputProductDto inputProductDto)  {
        return inputProductService.addInputProduct(inputProductDto);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> geInputProductById(@PathVariable("id") Long id) {
        return inputProductService.getInputProductById(id);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<InputProduct>> getAllInputProduct() {
        return inputProductService.getAllInputProduct();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteInputProductById(@PathVariable("id") Long id){
        return inputProductService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Result> updateInputProduct(@Valid @RequestBody  InputProductDto inputProductDto, @PathVariable("id") Long id) {
        return inputProductService.updateInputProduct(id, inputProductDto);
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
