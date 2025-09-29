package com.example.demo.repositories;

import com.example.demo.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    // Find student by user email
    @Query("SELECT s FROM Student s WHERE s.user.email = :email")
    Optional<Student> findByEmail(@Param("email") String email);
    
    // Find student by student ID
    Optional<Student> findByStudentId(String studentId);
    
    // Find all students in a specific class
    @Query("SELECT s FROM Student s WHERE s.schoolClass.id = :classId")
    List<Student> findBySchoolClassId(@Param("classId") Long classId);
    
    // Find all students by parent
    @Query("SELECT s FROM Student s JOIN s.parents p WHERE p.id = :parentId")
    List<Student> findByParentId(@Param("parentId") Long parentId);
    
    // Search students by first name or last name (through user relationship)
    @Query("SELECT s FROM Student s WHERE LOWER(s.user.firstName) LIKE LOWER(CONCAT('%', :name, '%')) " +
           "OR LOWER(s.user.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Student> searchByName(@Param("name") String name);
    
    // Find all active students
    List<Student> findByIsActiveTrue();
    
    // Find all inactive students
    List<Student> findByIsActiveFalse();
    
    // Count total students in a class
    @Query("SELECT COUNT(s) FROM Student s WHERE s.schoolClass.id = :classId")
    Long countStudentsByClassId(@Param("classId") Long classId);
    
    // Count total active students
    Long countByIsActiveTrue();
    
    // Find students by grade level
    List<Student> findByGradeLevel(String gradeLevel);
    
    // Check if student ID already exists
    boolean existsByStudentId(String studentId);
}