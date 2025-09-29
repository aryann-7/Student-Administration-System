package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Parent;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {

    // Find parent by phone number
    Optional<Parent> findByPhoneNumber(String phoneNumber);

    // Find parent by student ID
    @Query("SELECT p FROM Parent p JOIN p.children s WHERE s.id = :studentId")
    Optional<Parent> findByStudentId(@Param("studentId") Long studentId);

    // Parents in a specific class
    @Query("SELECT DISTINCT p FROM Parent p JOIN p.children s WHERE s.schoolClass.id = :classId")
    List<Parent> findParentsByClassId(@Param("classId") Long classId);

    // Search parents by name (access via UserEntity)
    @Query("SELECT p FROM Parent p WHERE LOWER(p.user.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(p.user.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Parent> searchByName(@Param("searchTerm") String searchTerm);

    // Find active parents
    List<Parent> findByIsActiveTrue();

    // Parents with multiple children
    @Query("SELECT p FROM Parent p WHERE SIZE(p.children) > 1")
    List<Parent> findParentsWithMultipleChildren();

    // Parents by occupation
    @Query("SELECT p FROM Parent p WHERE LOWER(p.occupation) LIKE LOWER(CONCAT('%', :occupation, '%'))")
    List<Parent> findByOccupation(@Param("occupation") String occupation);

    // Count total active parents
    Long countByIsActiveTrue();

    // Parents needing contact (fixed)
    @Query("SELECT p FROM Parent p WHERE p.lastContactDate < :date OR p.lastContactDate IS NULL")
    List<Parent> findParentsNeedingContact(@Param("date") java.time.LocalDate date);

    // Parents by location
    @Query("SELECT p FROM Parent p WHERE LOWER(p.address) LIKE LOWER(CONCAT('%', :location, '%'))")
    List<Parent> findByLocation(@Param("location") String location);

    // Parents whose children have low attendance
    @Query("SELECT DISTINCT p FROM Parent p JOIN p.children s JOIN s.attendances a " +
       "WHERE a.term.id = :termId " +
       "GROUP BY p, s " +
       "HAVING (SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END) * 100.0 / COUNT(a)) < :percentage")
    List<Parent> findParentsOfLowAttendanceStudents(@Param("termId") Long termId, @Param("percentage") Double percentage);
}
