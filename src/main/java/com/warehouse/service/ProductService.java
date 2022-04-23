package com.warehouse.service;

import com.warehouse.entity.Attachment;
import com.warehouse.entity.Category;
import com.warehouse.entity.Measurement;
import com.warehouse.entity.Product;
import com.warehouse.payload.ProductDto;
import com.warehouse.payload.Result;
import com.warehouse.repository.AttachmentRepository;
import com.warehouse.repository.CategoryRepository;
import com.warehouse.repository.MeasurementRepository;
import com.warehouse.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    MeasurementRepository measurementRepository;
    @Autowired
    AttachmentRepository attachmentRepository;

   public ResponseEntity<Result> addProduct(ProductDto productDto){
       Product product = new Product();

       boolean byNameAndCategoryId = productRepository.findByNameAndCategoryId(productDto.getName(), productDto.getCategoryId());
       if(byNameAndCategoryId)
           return ResponseEntity.status(HttpStatus.CONFLICT).body(new Result("Product  saved before with this  name and category", false));

       //Check Category id from database
       Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
       if(!optionalCategory.isPresent())
           return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Category not found with this ID:" + productDto.getCategoryId(),false));

       //Check Measurement id from database
       Optional<Measurement> optionalMeasurement = measurementRepository.findById(productDto.getMeasurementId());
       if(!optionalCategory.isPresent())
           return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Measurement not found with this ID:" + productDto.getMeasurementId(),false));

       //Check Attachment id from database
       Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getPhotoId());
       if(!optionalCategory.isPresent())
           return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Attachment not found with this ID:" + productDto.getMeasurementId(),false));

       product.setName(productDto.getName());
       product.setCode(productDto.getCode());
       product.setCategory(optionalCategory.get());
       product.setMeasurement(optionalMeasurement.get());
       product.setPhoto(optionalAttachment.get());
       product.setCode("product_code");
       productRepository.save(product);

       return ResponseEntity.status(HttpStatus.CREATED).body(new Result("product saved successfully",true));
   }

    public ResponseEntity<?> getProductById(Long id) {
       Optional<Product> product = productRepository.findById(id);
       if (!product.isPresent())
           return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Product not found with this ID:" + id,false));
        return ResponseEntity.ok(product.get());
    }

    public ResponseEntity<List<Product>> getAll() {
        return  ResponseEntity.ok(productRepository.findAll());
    }

    public ResponseEntity<Result> updateProduct(ProductDto productDto, Long id) {

       Optional<Product> byId = productRepository.findById(id);
        if (!byId.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Product not found with this ID:" + id,false));

        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if(!optionalCategory.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Category not found with this ID:" + productDto.getCategoryId(),false));

        Optional<Measurement> optionalMeasurement = measurementRepository.findById(productDto.getMeasurementId());
        if(!optionalCategory.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Measure not found with this ID:" + productDto.getMeasurementId(),false));

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getPhotoId());
        if(!optionalCategory.isPresent())
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Attachment not found with this ID:" + productDto.getPhotoId(),false));

        Product product = byId.get();
        product.setName(productDto.getName());
        product.setCategory(optionalCategory.get());
        product.setMeasurement(optionalMeasurement.get());
        product.setPhoto(optionalAttachment.get());
        productRepository.save(product);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Result("Product edited successfully",true));

    }

    public ResponseEntity<Result> deleteById(Long id) {
        Optional<Product> byId = productRepository.findById(id);
        if (!byId.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Product not found with this id: " + id, false));
        productRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Result("Product deleted Successfully", true));
    }
}
