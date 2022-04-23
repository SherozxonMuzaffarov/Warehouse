package com.warehouse.controller;

import com.warehouse.entity.Category;
import com.warehouse.entity.Measurement;
import com.warehouse.payload.CategoryDto;
import com.warehouse.payload.Result;
import com.warehouse.service.CategoryService;
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
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<Result> addCategory(@Valid @RequestBody CategoryDto categoryDto){
        return categoryService.addCategory(categoryDto);

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Long id){
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllCategory(){
        return categoryService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteCategorytById(@PathVariable("id") Long id){
        return categoryService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Result> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("id") Long id){
        return categoryService.updateCategory( categoryDto,id);
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
