package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.models.Teacher;

import jakarta.transaction.Transactional;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    
    // Check duplicate emails
    boolean existsByEmail(String email);
    
    
    //find a teacher by email
    Teacher findByEmail(String email); // Fetch
    
    // Delete teacher by email
    void deleteByEmail(String email);
    
    List<Teacher> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Teacher t WHERE t.id = :userId")
    void deleteByUserId(int userId);
}