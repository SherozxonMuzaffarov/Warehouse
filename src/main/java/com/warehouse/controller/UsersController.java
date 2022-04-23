package com.warehouse.controller;

import com.warehouse.entity.Users;
import com.warehouse.entity.WareHouse;
import com.warehouse.payload.Result;
import com.warehouse.payload.UsersDto;
import com.warehouse.service.UsersService;
import com.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UsersController {

    @Autowired
    UsersService usersService;

    @PostMapping("/add")
    public ResponseEntity<Result> addUser(@RequestBody UsersDto usersDto)  {
        return usersService.addUser(usersDto);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> geUserById(@PathVariable("id") Long id) {
        return usersService.getUserById(id);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Users>> getAllUser() {
        return usersService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteUserById(@PathVariable("id") Long id){
        return usersService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Result> updateUser(@RequestBody  UsersDto usersDto, @PathVariable("id") Long id) {
        return usersService.updateUser(id, usersDto);
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
