package com.project.foodjet.repository;

import com.project.foodjet.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer getByEmail(String email);
    // test commit
}
