package com.project.foodjet.repository;

import com.project.foodjet.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierRepository extends JpaRepository<Courier, Long> {
    Courier getByEmail(String email);
}
