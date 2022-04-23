package com.warehouse.service;

import com.warehouse.entity.Clients;
import com.warehouse.entity.Input;
import com.warehouse.entity.InputProduct;
import com.warehouse.entity.Product;
import com.warehouse.payload.InputProductDto;
import com.warehouse.payload.Result;
import com.warehouse.repository.InputProductRepository;
import com.warehouse.repository.InputRepository;
import com.warehouse.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InputProductService {

    @Autowired
    InputProductRepository inputProductRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    InputRepository inputRepository;

    public ResponseEntity<Result> addInputProduct(InputProductDto inputProductDto) {

        Optional<Product> optionalProduct = productRepository.findById(inputProductDto.getProductId());
        if (!optionalProduct.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(" Product not found ",false));

        Optional<Input> optionalInput = inputRepository.findById(inputProductDto.getInputId());
        if (!optionalInput.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Input not found:" ,false));


        InputProduct inputProduct = new InputProduct();
        inputProduct.setAmount(inputProductDto.getAmount());
        inputProduct.setPrice(inputProductDto.getPrice());
        inputProduct.setExpireDate(inputProductDto.getExpireDate());
        inputProduct.setProduct(optionalProduct.get());
        inputProduct.setInput(optionalInput.get());
        inputProductRepository.save(inputProduct);

        return ResponseEntity.status(HttpStatus.CREATED).body(new Result("Input product saved successfully", true));
    }

    public ResponseEntity<?> getInputProductById(Long id) {
        Optional<InputProduct> byId = inputProductRepository.findById(id);
        if (!byId.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("input product not found with this ID:" + id,false));
        return ResponseEntity.ok(byId.get());
    }

    public ResponseEntity<List<InputProduct>> getAllInputProduct() {
        return  ResponseEntity.ok(inputProductRepository.findAll());
    }

    public ResponseEntity<Result> deleteById(Long id) {
        Optional<InputProduct> byId = inputProductRepository.findById(id);
        if (!byId.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Input product not found with this id: " + id, false));
        inputProductRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Result("Input product deleted Successfully", true));
    }

    public ResponseEntity<Result> updateInputProduct(Long id, InputProductDto inputProductDto) {
        Optional<InputProduct> byId = inputProductRepository.findById(id);
        if (!byId.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Input product  not found with this ID:" + id,false));

        Optional<Product> optionalProduct = productRepository.findById(inputProductDto.getProductId());
        if (!optionalProduct.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(" Product not found ",false));

        Optional<Input> optionalInput = inputRepository.findById(inputProductDto.getInputId());
        if (!optionalInput.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Input not found:" ,false));

        InputProduct inputProduct = byId.get();
        inputProduct.setAmount(inputProductDto.getAmount());
        inputProduct.setPrice(inputProductDto.getPrice());
        inputProduct.setExpireDate(inputProductDto.getExpireDate());
        inputProduct.setProduct(optionalProduct.get());
        inputProduct.setInput(optionalInput.get());
        inputProductRepository.save(inputProduct);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Result("Input product edited successfully", true));
    }
}
