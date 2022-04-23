package com.warehouse.service;

import com.warehouse.entity.*;
import com.warehouse.payload.InputDto;
import com.warehouse.payload.Result;
import com.warehouse.repository.CurrencyRepository;
import com.warehouse.repository.InputRepository;
import com.warehouse.repository.SupplierRepository;
import com.warehouse.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InputService {

    @Autowired
    InputRepository inputRepository;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    WarehouseRepository warehouseRepository;

    public ResponseEntity<Result> addInput(InputDto inputDto) {
        boolean byPhoneNumber = inputRepository.existsByCode(inputDto.getCode());
        if (byPhoneNumber)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Result("Input  saved before with this  Code", false));

        Optional<Currency> optionalCurrency = currencyRepository.findById(inputDto.getCurrencyID());
        if (!optionalCurrency.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Currency not found with this  Id", false));

        Optional<Supplier> optionalSupplier = supplierRepository.findById(inputDto.getSupplier());
        if(!optionalSupplier.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Supplier not found with this  Id", false));

        Optional<WareHouse> optionalWareHouse = warehouseRepository.findById(inputDto.getWareHouseId());
        if(!optionalWareHouse.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Warehouse not found with this  Id", false));

        Input input = new Input();
        input.setCode(inputDto.getCode());
        input.setTimestamp(inputDto.getTimestamp());
        input.setFactureNumber(inputDto.getFactureNumberId());
        input.setCurrency(optionalCurrency.get());
        input.setWareHouse(optionalWareHouse.get());
        input.setSupplier(optionalSupplier.get());
        inputRepository.save(input);

        return ResponseEntity.status(HttpStatus.CREATED).body(new Result("Input saved successfully", true));

    }

    public ResponseEntity<?> getInputById(Long id) {
        Optional<Input> byId = inputRepository.findById(id);
        if (!byId.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Input not found with this ID:" + id,false));
        return ResponseEntity.ok(byId.get());
    }

    public ResponseEntity<List<Input>> getAll() {
        return  ResponseEntity.ok(inputRepository.findAll());
    }

    public ResponseEntity<Result> deleteById(Long id) {
        Optional<Input> byId = inputRepository.findById(id);
        if (!byId.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Input not found with this ID:" + id,false));
        inputRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Result("Input deleted Successfully", true));
    }

    public ResponseEntity<Result> updateInput(Long id, InputDto inputDto) {

        Optional<Input> byId = inputRepository.findById(id);
        if (!byId.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Input not found with this ID:" + id,false));

        boolean byPhoneNumber = inputRepository.existsByCode(inputDto.getCode());
        if (byPhoneNumber)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Result("Input  saved before with this  Code", false));

        Optional<Currency> optionalCurrency = currencyRepository.findById(inputDto.getCurrencyID());
        if (!optionalCurrency.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Currency not found with this  Id", false));

        Optional<Supplier> optionalSupplier = supplierRepository.findById(inputDto.getSupplier());
        if(!optionalSupplier.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Supplier not found with this  Id", false));

        Optional<WareHouse> optionalWareHouse = warehouseRepository.findById(inputDto.getWareHouseId());
        if(!optionalWareHouse.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Warehouse not found with this  Id", false));

        Input input = byId.get();
        input.setCode(inputDto.getCode());
        input.setFactureNumber(inputDto.getFactureNumberId());
        input.setTimestamp(inputDto.getTimestamp());
        input.setCurrency(optionalCurrency.get());
        input.setSupplier(optionalSupplier.get());
        input.setWareHouse(optionalWareHouse.get());
        inputRepository.save(input);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Result("Input edited successfully", true));
    }
}
