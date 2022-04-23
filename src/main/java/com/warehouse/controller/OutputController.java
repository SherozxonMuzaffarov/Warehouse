package com.warehouse.controller;

import com.warehouse.entity.Input;
import com.warehouse.entity.Output;
import com.warehouse.payload.InputDto;
import com.warehouse.payload.OutputDto;
import com.warehouse.payload.Result;
import com.warehouse.service.InputService;
import com.warehouse.service.OutputService;
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
@RequestMapping("/api/output")
public class OutputController {

    @Autowired
    OutputService outputService;

    @PostMapping("/add")
    public ResponseEntity<Result> addOutput(@Valid @RequestBody OutputDto outputDto)  {
        return outputService.addOutput(outputDto);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> geOutputById(@PathVariable("id") Long id) {
        return outputService.geOutputById(id);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Output>> getAllOutput() {
        return outputService.getAllOutput();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteOutputById(@PathVariable("id") Long id){
        return outputService.deleteOutputById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Result> updateOutput(@Valid @RequestBody OutputDto outputDto, @PathVariable("id") Long id) {
        return outputService.updateOutput(id, outputDto);
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
