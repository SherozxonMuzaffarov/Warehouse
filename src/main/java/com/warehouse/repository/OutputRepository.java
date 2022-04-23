package com.warehouse.repository;

import com.warehouse.entity.Output;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutputRepository extends JpaRepository<Output, Long> {
    boolean existsByCode(String name);
}
