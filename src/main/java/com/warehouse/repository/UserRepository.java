package com.warehouse.repository;

import com.warehouse.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
    boolean existsByPhoneNumber(String number);
}
