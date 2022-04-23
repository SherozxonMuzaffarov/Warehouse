package com.warehouse.service;

import com.warehouse.entity.*;
import com.warehouse.payload.OutputProductDto;
import com.warehouse.payload.Result;
import com.warehouse.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OutputProductService {

    @Autowired
    OutputProductRepository outputProductRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OutputRepository outputRepository;

    public ResponseEntity<Result> addOutputProduct(OutputProductDto outputProductDto) {

        Optional<Product> optionalProduct = productRepository.findById(outputProductDto.getProductID());
        if (!optionalProduct.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(" Product not found ",false));

        Optional<Output> optionalOutput = outputRepository.findById(outputProductDto.getOutputID());
        if (!optionalOutput.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Output not found:" ,false));

        OutputProduct outputProduct = new OutputProduct();
        outputProduct.setPrice(outputProductDto.getPrice());
        outputProduct.setAmount(outputProductDto.getAmount());
        outputProduct.setProduct(optionalProduct.get());
        outputProduct.setOutput(optionalOutput.get());
        outputProductRepository.save(outputProduct);

        return ResponseEntity.status(HttpStatus.CREATED).body(new Result("Output product saved successfully", true));
    }

    public ResponseEntity<?> geOutputProductById(Long id) {
        Optional<OutputProduct> byId = outputProductRepository.findById(id);
        if (!byId.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Output product not found with this ID:" + id,false));
        return ResponseEntity.ok(byId.get());
    }

    public ResponseEntity<List<OutputProduct>> getAllOutputProduct() {
        return  ResponseEntity.ok(outputProductRepository.findAll());
    }

    public ResponseEntity<Result> deleteOutputProduct(Long id) {
        Optional<OutputProduct> byId = outputProductRepository.findById(id);
        if (!byId.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Output product not found with this id: " + id, false));
        outputProductRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Result("Output product deleted Successfully", true));
    }

    public ResponseEntity<Result> updateOutputProduct(Long id, OutputProductDto outputProductDto) {
        Optional<OutputProduct> byId = outputProductRepository.findById(id);
        if (!byId.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Output product not found with this ID:" + id,false));

        Optional<Product> optionalProduct = productRepository.findById(outputProductDto.getProductID());
        if (!optionalProduct.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(" Product not found ",false));

        Optional<Output> optionalOutput = outputRepository.findById(outputProductDto.getOutputID());
        if (!optionalOutput.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Output not found:" ,false));

        OutputProduct outputProduct = byId.get();
        outputProduct.setPrice(outputProductDto.getPrice());
        outputProduct.setAmount(outputProductDto.getAmount());
        outputProduct.setProduct(optionalProduct.get());
        outputProduct.setOutput(optionalOutput.get());
        outputProductRepository.save(outputProduct);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Result("Input product edited successfully", true));
    }
}
