package com.project.foodjet.entity;

import com.project.foodjet.entity.Item;
import com.project.foodjet.service.CustomerService;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.springframework.data.repository.cdi.Eager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Item> items;

    @Enumerated(EnumType.STRING)
    private CartStatus status;

}
