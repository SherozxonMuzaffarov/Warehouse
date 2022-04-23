package com.warehouse.service;

import com.warehouse.entity.WareHouse;
import com.warehouse.payload.Result;
import com.warehouse.repository.WarehouseRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService {

    @Autowired
    WarehouseRepository warehouseRepository;

    public ResponseEntity<Result> addSupplier(WareHouse wareHouse) {
        if (warehouseRepository.existsByName(wareHouse.getName()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Result("Warehouse  saved before with this  name", false));
        WareHouse wareHouse1 = new WareHouse();
        wareHouse1.setName(wareHouse.getName());
        warehouseRepository.save(wareHouse1);

        return ResponseEntity.status(HttpStatus.CREATED).body(new Result("Warehouse saved successfully", true));
    }

    public ResponseEntity<?> getWarehouseById(Long id) {
        Optional<WareHouse> byId = warehouseRepository.findById(id);
        if (!byId.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Warehouse not found with this ID:" + id,false));
        return ResponseEntity.ok(byId.get());
    }

    public ResponseEntity<List<WareHouse>> getAll() {
        return ResponseEntity.ok(warehouseRepository.findAll());
    }

    public ResponseEntity<Result> deleteById(Long id) {
        Optional<WareHouse> byId = warehouseRepository.findById(id);
        if (!byId.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Warehouse not found with this id: " + id, false));
        warehouseRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Result("wareHouse deleted Successfully", true));
    }

    public ResponseEntity<Result> updateWarehouse(Long id, WareHouse wareHouse) {
        Optional<WareHouse> byId = warehouseRepository.findById(id);
        if (!byId.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Warehouse not found with this ID:" + id,false));

        WareHouse wareHouse1 = byId.get();
        wareHouse1.setName(wareHouse.getName());
        warehouseRepository.save(wareHouse1);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Result("Warehouse edited successfully", true));
    }
}
