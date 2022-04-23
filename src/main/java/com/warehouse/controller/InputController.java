package com.warehouse.controller;

import com.warehouse.entity.Input;
import com.warehouse.entity.Users;
import com.warehouse.payload.InputDto;
import com.warehouse.payload.Result;
import com.warehouse.payload.UsersDto;
import com.warehouse.service.InputService;
import com.warehouse.service.UsersService;
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
@RequestMapping("/api/input")
public class InputController {

    @Autowired
    InputService inputService;

    @PostMapping("/add")
    public ResponseEntity<Result> addInput(@Valid @RequestBody InputDto inputDto)  {
        return inputService.addInput(inputDto);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> geInputById(@PathVariable("id") Long id) {
        return inputService.getInputById(id);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Input>> getAllInput() {
        return inputService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteInputById(@PathVariable("id") Long id){
        return inputService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Result> updateInput(@Valid @RequestBody  InputDto inputDto, @PathVariable("id") Long id) {
        return inputService.updateInput(id, inputDto);
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
