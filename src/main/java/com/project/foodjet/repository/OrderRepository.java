package com.project.foodjet.repository;

import com.project.foodjet.entity.Customer;
import com.project.foodjet.entity.OrderStatus;
import com.project.foodjet.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByStatus(OrderStatus status);

    List<Orders> findByCustomer(Customer customer);
}
