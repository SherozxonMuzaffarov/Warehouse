package com.warehouse.repository;

import com.warehouse.entity.Clients;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Clients, Long> {
    boolean existsByPhoneNumber(String number);
}
