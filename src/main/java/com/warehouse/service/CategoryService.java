package com.warehouse.service;

import com.warehouse.entity.Category;
import com.warehouse.entity.Measurement;
import com.warehouse.payload.CategoryDto;
import com.warehouse.payload.Result;
import com.warehouse.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public ResponseEntity<Result> addCategory(CategoryDto categoryDto){
        Category category = new Category();
        category.setName(categoryDto.getName());
        if(categoryDto.getParentCategoryId()!=null){
            Optional<Category> optionalParentCategory = categoryRepository.findById(categoryDto.getParentCategoryId());
            if (!optionalParentCategory.isPresent())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Parent category not found", false));
            category.setParentCategory(optionalParentCategory.get());
        }
        categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Result("Done Successfully",true));
    }

    public ResponseEntity<?> getCategoryById(Long id) {
        Optional<Category> byId = categoryRepository.findById(id);
        if (!byId.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Nothing there", false));
        return ResponseEntity.ok(byId.get());
    }

    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    public ResponseEntity<Result> deleteById(Long id) {
        Optional<Category> byId = categoryRepository.findById(id);
        if (!byId.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Nothing there", false));
        categoryRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Result("Done Successfully", true));
    }

    public ResponseEntity<Result> updateCategory(CategoryDto categoryDto, Long id) {

        Optional<Category> byId = categoryRepository.findById(id);
        if (!byId.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("Category not found", false));

        Category category = byId.get();

        if (category.getParentCategory()!= null)
            category.setParentCategory(categoryRepository.findById(categoryDto.getParentCategoryId()).get());
        category.setName(categoryDto.getName());
        categoryRepository.save(category);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Result("Edited Successfully", true));
    }
}
