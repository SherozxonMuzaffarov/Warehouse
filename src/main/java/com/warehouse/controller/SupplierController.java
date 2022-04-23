package com.warehouse.controller;

import com.warehouse.entity.Supplier;
import com.warehouse.payload.Result;
import com.warehouse.payload.SupplierDto;
import com.warehouse.service.AttachmentService;
import com.warehouse.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @PostMapping("/save")
    public ResponseEntity<Result> addSupplier(@Valid @RequestBody SupplierDto supplierDto)  {
        return supplierService.addSupplier(supplierDto);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getSupplierById(@PathVariable("id") Long id) {
        return supplierService.getSupplierById(id);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Supplier>> getAllSupplier() {
        return supplierService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteSupplierById(@PathVariable("id") Long id){
        return supplierService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Result> updateSupplier(@Valid @RequestBody  SupplierDto supplierDto, @PathVariable("id") Long id) {
        return supplierService.updateSupplier(id, supplierDto);
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
