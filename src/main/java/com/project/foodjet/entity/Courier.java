package com.project.foodjet.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.annotation.processing.Generated;

@Entity
@Data
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;

    @Enumerated(EnumType.STRING)
    private CourierStatus status;


    public Courier(String email, String password) {
        this.email=email;
        this.password=password;
    }

    public Courier() {

    }
}
