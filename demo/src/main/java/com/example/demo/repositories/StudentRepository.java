package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.models.SchoolClass;
import com.example.demo.models.Student;

import jakarta.transaction.Transactional;
public interface StudentRepository extends JpaRepository<Student, Integer> {
    
    //search for students by first or last name
    List<Student> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);

    //find a student by email
    Student findByEmail(String email);

    //find students by their assigned class
    List<Student> findBySchoolClass(SchoolClass schoolClass);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Student s WHERE s.id = :userId")
    void deleteByUserId(int userId);
}