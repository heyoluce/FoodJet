package com.project.foodjet.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Orders {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        private Courier courier;

        @ManyToOne
        private Customer customer;

        @OneToOne
        private Cart cart;

        private String restaurant;

        private LocalDateTime orderDate;

        private LocalDateTime deliveryDate;

        @Enumerated(EnumType.STRING)
        private OrderStatus status;

    }



