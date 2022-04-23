package com.warehouse.service;

import com.warehouse.entity.Clients;
import com.warehouse.entity.Currency;
import com.warehouse.payload.Result;
import com.warehouse.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {

    @Autowired
    CurrencyRepository currencyRepository;

    public ResponseEntity<Result> addCurrency(Currency currency) {
        boolean existsByName = currencyRepository.existsByName(currency.getName());
        if (existsByName)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Result("Currency  saved before with name", false));

        Currency currency1 = new Currency();
        currency1.setName(currency.getName());
        currencyRepository.save(currency1);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Result("Currency saved successfully", true));
    }

    public ResponseEntity<?> getCurrencyById(Long id) {
        Optional<Currency> byId = currencyRepository.findById(id);
        if (!byId.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Currency not found with this ID:" + id,false));
        return ResponseEntity.ok(byId.get());

    }

    public ResponseEntity<List<Currency>> getAll() {
        return  ResponseEntity.ok(currencyRepository.findAll());
    }

    public ResponseEntity<Result> deleteById(Long id) {
        Optional<Currency> byId = currencyRepository.findById(id);
        if (!byId.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Currency not found with this id: " + id, false));
        currencyRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Result("currencyRepository deleted Successfully", true));
    }

    public ResponseEntity<Result> updateCurrency(Long id, Currency currency) {
        Optional<Currency> byId = currencyRepository.findById(id);
        if (!byId.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Currency not found with this ID:" + id,false));

        Currency currency1 = byId.get();
        currency1.setName(currency.getName());
        currencyRepository.save(currency1);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Result("Currency edited successfully", true));
    }
}
