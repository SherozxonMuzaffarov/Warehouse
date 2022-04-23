package com.warehouse.controller;

import com.warehouse.entity.Product;
import com.warehouse.payload.ProductDto;
import com.warehouse.payload.Result;
import com.warehouse.service.ProductService;
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
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<Result> addProduct(@Valid @RequestBody ProductDto productDto){
        return productService.addProduct(productDto);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id){
        return productService.getProductById(id);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Product>> getAllProduct(){
        return productService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteProductById(@PathVariable("id") Long id){
        return productService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Result> updateProduct(@Valid @RequestBody ProductDto productDto,@PathVariable("id") Long id){
        return productService.updateProduct(productDto,id);
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
