package com.warehouse.repository;

import com.warehouse.entity.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<WareHouse, Long> {
    boolean existsByName(String name);
}
