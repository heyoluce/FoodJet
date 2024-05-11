package com.project.foodjet.repository;

import com.project.foodjet.entity.Cart;
import com.project.foodjet.entity.CartStatus;
import com.project.foodjet.entity.Customer;
import com.project.foodjet.entity.Item;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO cart_items (cart_id, items_id) VALUES (:cartId, :itemId)", nativeQuery = true)
    void addItem(Long cartId, Long itemId);

    Cart getByCustomer(Customer customer);

    @Transactional
    @Modifying
    @Query("UPDATE Cart c SET c.status = 'INACTIVE' WHERE c.customer = :customer")
    void removeCartByCustomer(Customer customer);

    Cart getByCustomerAndStatus(Customer customer, CartStatus status);
}
