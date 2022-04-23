package com.warehouse.service;

import com.warehouse.entity.*;
import com.warehouse.payload.OutputDto;
import com.warehouse.payload.Result;
import com.warehouse.repository.ClientRepository;
import com.warehouse.repository.CurrencyRepository;
import com.warehouse.repository.OutputRepository;
import com.warehouse.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OutputService {

    @Autowired
    OutputRepository outputRepository;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    WarehouseRepository warehouseRepository;

    public ResponseEntity<Result> addOutput(OutputDto outputDto) {
        boolean byPhoneNumber = outputRepository.existsByCode(outputDto.getCode());
        if (byPhoneNumber)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Result("Input  saved before with this  Code", false));

        Optional<Currency> optionalCurrency = currencyRepository.findById(outputDto.getCurrencyID());
        if (!optionalCurrency.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Currency not found with this  Id", false));

        Optional<Clients> optionalClient = clientRepository.findById(outputDto.getClientId());
        if(!optionalClient.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Client not found with this  Id", false));

        Optional<WareHouse> optionalWareHouse = warehouseRepository.findById(outputDto.getWareHouseId());
        if(!optionalWareHouse.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Warehouse not found with this  Id", false));

        Output output = new Output();
        output.setCode(outputDto.getCode());
        output.setTimestamp(outputDto.getTimestamp());
        output.setFactureNumber(outputDto.getFactureNumberId());
        output.setCurrency(optionalCurrency.get());
        output.setClient(optionalClient.get());
        output.setWareHouse(optionalWareHouse.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(new Result("Output saved successfully", true));
    }

    public ResponseEntity<?> geOutputById(Long id) {
        Optional<Output> byId = outputRepository.findById(id);
        if (!byId.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Output not found with this ID:" + id,false));
        return ResponseEntity.ok(byId.get());
    }

    public ResponseEntity<List<Output>> getAllOutput() {
        return  ResponseEntity.ok(outputRepository.findAll());
    }

    public ResponseEntity<Result> deleteOutputById(Long id) {
        Optional<Output> byId = outputRepository.findById(id);
        if (!byId.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Output not found with this ID:" + id,false));
        outputRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Result("Output deleted Successfully", true));
    }

    public ResponseEntity<Result> updateOutput(Long id, OutputDto outputDto) {

        Optional<Output> byId = outputRepository.findById(id);
        if (!byId.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Output not found with this ID:" + id,false));

        boolean byPhoneNumber = outputRepository.existsByCode(outputDto.getCode());
        if (byPhoneNumber)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Result("Input  saved before with this  Code", false));

        Optional<Currency> optionalCurrency = currencyRepository.findById(outputDto.getCurrencyID());
        if (!optionalCurrency.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Currency not found with this  Id", false));

        Optional<Clients> optionalClient = clientRepository.findById(outputDto.getClientId());
        if(!optionalClient.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Client not found with this  Id", false));

        Optional<WareHouse> optionalWareHouse = warehouseRepository.findById(outputDto.getWareHouseId());
        if(!optionalWareHouse.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Warehouse not found with this  Id", false));

        Output output = byId.get();
        output.setCode(outputDto.getCode());
        output.setTimestamp(outputDto.getTimestamp());
        output.setFactureNumber(outputDto.getFactureNumberId());
        output.setCurrency(optionalCurrency.get());
        output.setClient(optionalClient.get());
        output.setWareHouse(optionalWareHouse.get());
        outputRepository.save(output);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Result("Output edited successfully", true));
    }
}
