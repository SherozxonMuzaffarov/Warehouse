package com.warehouse.controller;

import com.warehouse.entity.Measurement;
import com.warehouse.payload.ProductDto;
import com.warehouse.payload.Result;
import com.warehouse.service.MeasurementService;
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
@RequestMapping("/measurement")
public class MeasurementController {

    @Autowired
    MeasurementService measurementService;

    @PostMapping("/addd")
    public ResponseEntity<Result> addMeasurement(@Valid @RequestBody Measurement measurement){
        return measurementService.addMeasurementService(measurement);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getMeasurementById(@PathVariable("id") Long id){
        return measurementService.getMeasurementById(id);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Measurement>> getAllMeasurement(){
        return measurementService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteMeasurementById(@PathVariable("id") Long id){
        return measurementService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Result> updateMeasurement(@Valid @RequestBody  Measurement measurement, @PathVariable("id") Long id){
        return measurementService.updateMeasurement(measurement,id);
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
