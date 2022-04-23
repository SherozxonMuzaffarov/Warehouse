package com.warehouse.controller;

import com.warehouse.entity.Clients;
import com.warehouse.payload.ClientDto;
import com.warehouse.payload.Result;
import com.warehouse.service.ClientService;
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
@RequestMapping("/client")
public class ClientController {
    @Autowired
    ClientService clientService;

    @PostMapping("/add")
    public ResponseEntity<Result> addClient(@Valid @RequestBody ClientDto clientDto)  {
        return clientService.addClient(clientDto);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getClientById(@PathVariable("id") Long id) {
        return clientService.getClientById(id);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Clients>> getAllClient() {
        return clientService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteClienttById(@PathVariable("id") Long id){
        return clientService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Result> updateClient(@Valid @RequestBody ClientDto clientDto, @PathVariable("id") Long id) {
        return clientService.updateClient(id, clientDto);
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
