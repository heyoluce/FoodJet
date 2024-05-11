package com.project.foodjet.repository;

import com.project.foodjet.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<MyUser, Long> {
    MyUser findByUsername(String username);
}
