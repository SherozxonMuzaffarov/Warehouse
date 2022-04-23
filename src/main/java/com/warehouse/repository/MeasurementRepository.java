package com.warehouse.repository;

import com.warehouse.entity.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {

    boolean existsByname(String name);
}
