package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.models.SchoolClass;
import com.example.demo.models.Term;
import com.example.demo.models.UserEntity;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
    
    // Find class by name
    Optional<SchoolClass> findByClassName(String className);
    
    // Find class by name ignoring case
    Optional<SchoolClass> findByClassNameIgnoreCase(String className);
    
    // Check if class exists by name
    boolean existsByClassName(String className);
    
    // Find classes by term
    List<SchoolClass> findByTerm(Term term);
    
    // Find classes by term ID
    List<SchoolClass> findByTermId(Long termId);
    
    // Find classes by grade/year level
    List<SchoolClass> findByGradeLevel(String gradeLevel);
    
    // Find classes by grade level and term
    List<SchoolClass> findByGradeLevelAndTerm(String gradeLevel, Term term);
    
    // Find classes with capacity greater than or equal to specified number
    List<SchoolClass> findByCapacityGreaterThanEqual(int capacity);
    
    // Find classes with capacity less than specified number
    List<SchoolClass> findByCapacityLessThan(int capacity);
    
    // Find classes by teacher (assuming teacher relationship exists)
    List<SchoolClass> findByTeacher(UserEntity teacher);
    
    // Find classes by teacher ID
    List<SchoolClass> findByTeacherId(Long teacherId);
    
    // Find classes containing specific text in name
    List<SchoolClass> findByClassNameContainingIgnoreCase(String searchText);
    
    // Get all classes ordered by class name
    List<SchoolClass> findAllByOrderByClassNameAsc();
    
    // Get all classes ordered by grade level then class name
    List<SchoolClass> findAllByOrderByGradeLevelAscClassNameAsc();
    
    // Custom query to find classes in current active term
    @Query("SELECT sc FROM SchoolClass sc JOIN sc.term t WHERE t.isActive = true")
    List<SchoolClass> findClassesInActiveTerm();
    
    // Custom query to find classes by teacher username
    @Query("SELECT sc FROM SchoolClass sc WHERE sc.teacher.username = :username")
    List<SchoolClass> findByTeacherUsername(@Param("username") String username);
    
    // Custom query to count students in a class
    @Query("SELECT COUNT(s) FROM Student s WHERE s.schoolClass.id = :classId")
    long countStudentsInClass(@Param("classId") Long classId);
    
    // Custom query to find classes with students enrolled
    @Query("SELECT DISTINCT sc FROM SchoolClass sc JOIN Student s ON s.schoolClass.id = sc.id")
    List<SchoolClass> findClassesWithStudents();
    
    // Custom query to find classes without students
    @Query("SELECT sc FROM SchoolClass sc WHERE sc.id NOT IN " +
           "(SELECT DISTINCT s.schoolClass.id FROM Student s WHERE s.schoolClass IS NOT NULL)")
    List<SchoolClass> findClassesWithoutStudents();
    
    // Find classes by term and teacher
    @Query("SELECT sc FROM SchoolClass sc WHERE sc.term.id = :termId AND sc.teacher.id = :teacherId")
    List<SchoolClass> findByTermIdAndTeacherId(@Param("termId") Long termId, @Param("teacherId") Long teacherId);
    
    // Find classes by term name
    @Query("SELECT sc FROM SchoolClass sc WHERE sc.term.termName = :termName")
    List<SchoolClass> findByTermName(@Param("termName") String termName);
    
    // Custom query to get class statistics
    @Query("SELECT sc.className, sc.capacity, COUNT(s.id) as enrolledCount " +
           "FROM SchoolClass sc LEFT JOIN Student s ON s.schoolClass.id = sc.id " +
           "GROUP BY sc.id, sc.className, sc.capacity")
    List<Object[]> getClassStatistics();
    
    // Find available classes (classes with remaining capacity)
    @Query("SELECT sc FROM SchoolClass sc WHERE " +
           "(SELECT COUNT(s) FROM Student s WHERE s.schoolClass.id = sc.id) < sc.capacity")
    List<SchoolClass> findAvailableClasses();
    
    // Find full classes (classes at capacity)
    @Query("SELECT sc FROM SchoolClass sc WHERE " +
           "(SELECT COUNT(s) FROM Student s WHERE s.schoolClass.id = sc.id) >= sc.capacity")
    List<SchoolClass> findFullClasses();
    
    // Count classes by term
    @Query("SELECT COUNT(sc) FROM SchoolClass sc WHERE sc.term.id = :termId")
    long countByTermId(@Param("termId") Long termId);
    
    // Count classes by teacher
    @Query("SELECT COUNT(sc) FROM SchoolClass sc WHERE sc.teacher.id = :teacherId")
    long countByTeacherId(@Param("teacherId") Long teacherId);
    
    // Find classes by grade level in active term
    @Query("SELECT sc FROM SchoolClass sc JOIN sc.term t WHERE t.isActive = true AND sc.gradeLevel = :gradeLevel")
    List<SchoolClass> findByGradeLevelInActiveTerm(@Param("gradeLevel") String gradeLevel);
    
    // Check if teacher has classes in a specific term
    @Query("SELECT CASE WHEN COUNT(sc) > 0 THEN true ELSE false END " +
           "FROM SchoolClass sc WHERE sc.teacher.id = :teacherId AND sc.term.id = :termId")
    boolean teacherHasClassesInTerm(@Param("teacherId") Long teacherId, @Param("termId") Long termId);
    
    // Find classes that need attention (low enrollment)
    @Query("SELECT sc FROM SchoolClass sc WHERE " +
           "(SELECT COUNT(s) FROM Student s WHERE s.schoolClass.id = sc.id) < :minEnrollment")
    List<SchoolClass> findClassesWithLowEnrollment(@Param("minEnrollment") int minEnrollment);
}