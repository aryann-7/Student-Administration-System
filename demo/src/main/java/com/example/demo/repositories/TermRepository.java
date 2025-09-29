package com.example.demo.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Term;

@Repository
public interface TermRepository extends JpaRepository<Term, Long> {
    
    // Find term by name (e.g., "Fall 2024", "Spring 2025")
    Optional<Term> findByTermName(String termName);
    
    // Find term by name ignoring case
    Optional<Term> findByTermNameIgnoreCase(String termName);
    
    // Check if term exists by name
    boolean existsByTermName(String termName);
    
    // Find all active terms
    List<Term> findByIsActiveTrue();
    
    // Find all inactive terms
    List<Term> findByIsActiveFalse();
    
    // Find the current active term (assuming only one should be active at a time)
    Optional<Term> findByIsActiveTrueAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
        LocalDate startDate, LocalDate endDate);
    
    // Find terms by year (assuming termName contains year)
    List<Term> findByTermNameContaining(String year);
    
    // Find terms within a date range
    List<Term> findByStartDateBetween(LocalDate startDate, LocalDate endDate);
    
    // Find terms that end after a specific date
    List<Term> findByEndDateAfter(LocalDate date);
    
    // Find terms that start before a specific date
    List<Term> findByStartDateBefore(LocalDate date);
    
    // Get all terms ordered by start date (newest first)
    List<Term> findAllByOrderByStartDateDesc();
    
    // Get all terms ordered by start date (oldest first)
    List<Term> findAllByOrderByStartDateAsc();
    
    // Find terms by start and end date range
    List<Term> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(
        LocalDate checkDate1, LocalDate checkDate2);
    
    // Custom query to find current active term
    @Query("SELECT t FROM Term t WHERE t.isActive = true AND :currentDate BETWEEN t.startDate AND t.endDate")
    Optional<Term> findCurrentActiveTerm(@Param("currentDate") LocalDate currentDate);
    
    // Custom query to find terms by year
    @Query("SELECT t FROM Term t WHERE YEAR(t.startDate) = :year")
    List<Term> findByYear(@Param("year") int year);
    
    // Custom query to find upcoming terms
    @Query("SELECT t FROM Term t WHERE t.startDate > :currentDate ORDER BY t.startDate ASC")
    List<Term> findUpcomingTerms(@Param("currentDate") LocalDate currentDate);
    
    // Custom query to find past terms
    @Query("SELECT t FROM Term t WHERE t.endDate < :currentDate ORDER BY t.endDate DESC")
    List<Term> findPastTerms(@Param("currentDate") LocalDate currentDate);
    
    // Find overlapping terms (useful for validation)
    @Query("SELECT t FROM Term t WHERE " +
           "(t.startDate <= :endDate AND t.endDate >= :startDate) " +
           "AND (:termId IS NULL OR t.id != :termId)")
    List<Term> findOverlappingTerms(@Param("startDate") LocalDate startDate, 
                                   @Param("endDate") LocalDate endDate,
                                   @Param("termId") Long termId);
    
    // Count terms by active status
    @Query("SELECT COUNT(t) FROM Term t WHERE t.isActive = :isActive")
    long countByActiveStatus(@Param("isActive") boolean isActive);
    
    // Find terms with classes (assuming SchoolClass has term relationship)
    @Query("SELECT DISTINCT t FROM Term t JOIN SchoolClass sc ON sc.term.id = t.id")
    List<Term> findTermsWithClasses();
    
    // Count classes in a term
    @Query("SELECT COUNT(sc) FROM SchoolClass sc WHERE sc.term.id = :termId")
    long countClassesInTerm(@Param("termId") Long termId);
    
    // Find most recent term
    @Query("SELECT t FROM Term t ORDER BY t.startDate DESC LIMIT 1")
    Optional<Term> findMostRecentTerm();
}