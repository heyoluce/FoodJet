package com.project.foodjet.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Customer  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;
    private String password;

    private String address;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Cart> cart;

    private String phone;


    public Customer(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
