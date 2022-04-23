package com.warehouse.repository;

import com.warehouse.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    boolean existsByName(String name);
}
