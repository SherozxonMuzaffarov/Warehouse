package com.warehouse.controller;

import com.warehouse.entity.InputProduct;
import com.warehouse.entity.Output;
import com.warehouse.entity.OutputProduct;
import com.warehouse.payload.InputProductDto;
import com.warehouse.payload.OutputProductDto;
import com.warehouse.payload.Result;
import com.warehouse.service.InputProductService;
import com.warehouse.service.OutputProductService;
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
@RequestMapping("/")
public class OutputProductController {

    @Autowired
    OutputProductService outputProductService;

    @PostMapping("/add")
    public ResponseEntity<Result> addOutputProduct(@Valid @RequestBody OutputProductDto outputProductDto)  {
        return outputProductService.addOutputProduct(outputProductDto);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> geOutputProductById(@PathVariable("id") Long id) {
        return outputProductService.geOutputProductById(id);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<OutputProduct>> getAllOutputProduct() {
        return outputProductService.getAllOutputProduct();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteOutputProduct(@PathVariable("id") Long id){
        return outputProductService.deleteOutputProduct(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Result> updateOutputProduct(@Valid @RequestBody OutputProductDto outputProductDto, @PathVariable("id") Long id) {
        return outputProductService.updateOutputProduct(id, outputProductDto);
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
