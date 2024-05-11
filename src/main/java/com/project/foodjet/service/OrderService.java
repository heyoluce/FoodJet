package com.project.foodjet.service;

import com.project.foodjet.entity.Customer;
import com.project.foodjet.entity.OrderStatus;
import com.project.foodjet.entity.Orders;
import com.project.foodjet.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements CrudListener<Orders> {
    private final OrderRepository orderRepository;


    public List<Orders> findByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    public List<Orders> findByCustomer(Customer customer) {
        return orderRepository.findByCustomer(customer);
    }

    @Override
    public List<Orders> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Orders add(Orders orders) {
        return orderRepository.save(orders);
    }

    @Override
    public Orders update(Orders orders) {
        return orderRepository.save(orders);
    }

    @Override
    public void delete(Orders orders) {
        orderRepository.delete(orders);
    }
}
