package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    //find existing users by email
    Optional<UserEntity> findByEmail(String email);
    Boolean existsByEmail(String email);
    
}