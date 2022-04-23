package com.warehouse.repository;

import com.warehouse.entity.Input;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InputRepository extends JpaRepository<Input, Long> {
    boolean existsByCode(String code);
}
