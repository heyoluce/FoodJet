package com.project.foodjet.service;

import com.project.foodjet.entity.Cart;
import com.project.foodjet.entity.CartStatus;
import com.project.foodjet.entity.Customer;
import com.project.foodjet.entity.Item;
import com.project.foodjet.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService implements CrudListener<Cart> {

    private final CartRepository cartRepository;

    @Override
    public List<Cart> findAll() {
        return (List<Cart>) cartRepository.findAll();
    }


    public void addItem(Cart cart, Long itemId) {
        cartRepository.addItem(cart.getId(), itemId);
    }


    public Cart getByCustomer(Customer customer) {
        return cartRepository.getByCustomer(customer);
    }
    @Override
    public Cart add(Cart cart) {
        return cartRepository.save(cart);
    }

    public void clearCart(Customer customer) {
        cartRepository.removeCartByCustomer(customer);
    }

    @Override
    public Cart update(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public void delete(Cart cart) {
        cartRepository.delete(cart);
    }

    public Cart getByCustomerAndStatus(Customer customer, CartStatus status) {
        return cartRepository.getByCustomerAndStatus(customer, status);
    }
}