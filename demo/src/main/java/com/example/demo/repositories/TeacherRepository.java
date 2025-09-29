package com.example.demo.repositories;

import com.example.demo.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    // Find teacher by email
    Optional<Teacher> findByEmail(String email);

    // Find teacher by employee ID
    Optional<Teacher> findByEmployeeId(String employeeId);

    // Find teacher by phone number
    Optional<Teacher> findByPhoneNumber(String phoneNumber);

    // Search teachers by first name or last name (case-insensitive)
    @Query("SELECT t FROM Teacher t WHERE LOWER(t.user.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(t.user.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Teacher> searchByName(@Param("name") String name);

    // Find all active teachers
    List<Teacher> findByIsActiveTrue();

    // Find all inactive teachers
    List<Teacher> findByIsActiveFalse();

    // Find teachers by subject/specialization
    @Query("SELECT t FROM Teacher t WHERE LOWER(t.subject) LIKE LOWER(CONCAT('%', :subject, '%'))")
    List<Teacher> findBySubject(@Param("subject") String subject);

    // Find teachers by department
    List<Teacher> findByDepartment(String department);

    // Find class teacher (homeroom teacher) for a specific class
    @Query("SELECT t FROM Teacher t WHERE t.assigned.id = :classId")
    Optional<Teacher> findClassTeacherByClassId(@Param("classId") Long classId);

    // Find teachers assigned to teach a specific class (subject teachers)
    @Query("SELECT DISTINCT t FROM Teacher t JOIN t.teachingClasses tc WHERE tc.id = :classId")
    List<Teacher> findTeachersByClassId(@Param("classId") Long classId);

    // Find teachers with no class assignment
    @Query("SELECT t FROM Teacher t WHERE t.assigned IS NULL AND t.isActive = true")
    List<Teacher> findUnassignedTeachers();

    // Count total active teachers
    Long countByIsActiveTrue();

    // Count teachers by department
    Long countByDepartment(String department);

    // Find teachers by qualification level
    List<Teacher> findByQualification(String qualification);

    // Find teachers by gender
    List<Teacher> findByGender(String gender);

    // Find teachers hired in a specific year
    @Query("SELECT t FROM Teacher t WHERE YEAR(t.hireDate) = :year")
    List<Teacher> findByHireYear(@Param("year") Integer year);

    // Find teachers by experience (years of service greater than specified)
    @Query("SELECT t FROM Teacher t WHERE YEAR(CURRENT_DATE) - YEAR(t.hireDate) >= :years")
    List<Teacher> findByMinimumExperience(@Param("years") Integer years);

    // Check if email already exists (for validation)
    boolean existsByEmail(String email);

    // Check if employee ID already exists
    boolean existsByEmployeeId(String employeeId);

    // Check if phone number already exists
    boolean existsByPhoneNumber(String phoneNumber);

    // Find teachers available for class assignment (active and not assigned as class teacher)
    @Query("SELECT t FROM Teacher t WHERE t.isActive = true AND t.assigned IS NULL")
    List<Teacher> findAvailableForClassAssignment();
}
