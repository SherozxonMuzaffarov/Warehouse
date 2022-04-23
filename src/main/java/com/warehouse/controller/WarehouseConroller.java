package com.warehouse.controller;

import com.warehouse.entity.Supplier;
import com.warehouse.entity.WareHouse;
import com.warehouse.payload.Result;
import com.warehouse.service.SupplierService;
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
@RequestMapping("/warehouse")
public class WarehouseConroller {

    @Autowired
    WarehouseService warehouseService;

    @PostMapping("/savew")
    public ResponseEntity<Result> addWarehouse(@RequestBody WareHouse wareHouse)  {
        return warehouseService.addSupplier(wareHouse);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getWarehouseById(@PathVariable("id") Long id) {
        return warehouseService.getWarehouseById(id);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<WareHouse>> getAllWarehouse() {
        return warehouseService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteWarehouseById(@PathVariable("id") Long id){
        return warehouseService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Result> updateWarehouse(@RequestBody  WareHouse wareHouse, @PathVariable("id") Long id) {
        return warehouseService.updateWarehouse(id, wareHouse);
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
