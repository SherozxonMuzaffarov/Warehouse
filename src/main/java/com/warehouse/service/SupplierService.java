package com.warehouse.service;

import com.warehouse.entity.Clients;
import com.warehouse.entity.Supplier;
import com.warehouse.payload.Result;
import com.warehouse.payload.SupplierDto;
import com.warehouse.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    SupplierRepository supplierRepository;

    public ResponseEntity<Result> addSupplier(SupplierDto supplierDto) {

        boolean byPhoneNumber = supplierRepository.findByPhoneNumber(supplierDto.getPhoneNumber());
        if (byPhoneNumber)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Result("Supplier saved before with this phoneNumber", false));

        Supplier supplier1 = new Supplier();
        supplier1.setName(supplierDto.getName());
        supplier1.setPhoneNumber(supplierDto.getPhoneNumber());
        supplier1.setActive(true);
        supplierRepository.save(supplier1);

        return ResponseEntity.status(HttpStatus.CREATED).body(new Result("Supplier saved successfully", true));
    }

    public ResponseEntity<?> getSupplierById(Long id) {
        Optional<Supplier> byId = supplierRepository.findById(id);
        if (!byId.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Supplier not found with this ID:" + id,false));
        return ResponseEntity.ok(byId.get());
    }

    public ResponseEntity<List<Supplier>> getAll() {
        return ResponseEntity.ok(supplierRepository.findAll());
    }

    public ResponseEntity<Result> deleteById(Long id) {
        Optional<Supplier> byId = supplierRepository.findById(id);
        Supplier supplier = byId.get();
        if (!byId.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Supplier not found with this ID:" + id,false));

        supplierRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Result("Supplier deleted successfully", true));
    }

    public ResponseEntity<Result> updateSupplier(Long id,SupplierDto supplierDto) {
        Optional<Supplier> byId = supplierRepository.findById(id);
        if (!byId.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Supplier not found with this ID:" + id,false));

        boolean byPhoneNumber = supplierRepository.findByPhoneNumber(supplierDto.getPhoneNumber());
        if (byPhoneNumber )
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Result("Supplier saved before with this phoneNumber", false));

        Supplier supplier = byId.get();
        supplier.setName(supplierDto.getName());
        supplier.setActive(true);
        supplier.setPhoneNumber(supplierDto.getPhoneNumber());
        supplierRepository.save(supplier);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Result("Supplier edited successfully", true));
    }
}
