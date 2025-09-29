package com.example.demo.repositories;

import com.example.demo.models.Attendance;
import com.example.demo.models.Student;
import com.example.demo.models.Teacher;
import com.example.demo.models.SchoolClass;
import com.example.demo.models.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // Find attendance by student and date
    Optional<Attendance> findByStudentAndAttendanceDate(Student student, LocalDate attendanceDate);

    // Find all attendance records for a specific student
    List<Attendance> findByStudent(Student student);

    // Find all attendance records for a specific student within a date range
    List<Attendance> findByStudentAndAttendanceDateBetween(Student student, LocalDate startDate, LocalDate endDate);

    // Find all attendance records for a specific class on a specific date
    List<Attendance> findBySchoolClassAndAttendanceDate(SchoolClass schoolClass, LocalDate attendanceDate);

    // Find all attendance records for a specific class within a date range
    List<Attendance> findBySchoolClassAndAttendanceDateBetween(SchoolClass schoolClass, LocalDate startDate, LocalDate endDate);

    // Find attendance records by teacher (through class relationship)
    @Query("SELECT a FROM Attendance a WHERE a.schoolClass.teacher = :teacher")
    List<Attendance> findByTeacher(@Param("teacher") Teacher teacher);

    // Find attendance records by teacher within a date range
    @Query("SELECT a FROM Attendance a WHERE a.schoolClass.teacher = :teacher AND a.attendanceDate BETWEEN :startDate AND :endDate")
    List<Attendance> findByTeacherAndDateBetween(@Param("teacher") Teacher teacher,
                                                 @Param("startDate") LocalDate startDate,
                                                 @Param("endDate") LocalDate endDate);

    // Find attendance records for a specific term
    List<Attendance> findByTerm(Term term);

    // Find attendance records by status (present, absent, late, etc.)
    List<Attendance> findByStatus(String status);

    // Find attendance records by student and status
    List<Attendance> findByStudentAndStatus(Student student, String status);

    // Find attendance records by class and status
    List<Attendance> findBySchoolClassAndStatus(SchoolClass schoolClass, String status);

    // Count attendance records by student and status
    long countByStudentAndStatus(Student student, String status);

    // Count attendance records by class and status on a specific date
    long countBySchoolClassAndAttendanceDateAndStatus(SchoolClass schoolClass, LocalDate attendanceDate, String status);

    // Find attendance records for multiple students on a specific date
    List<Attendance> findByStudentInAndAttendanceDate(List<Student> students, LocalDate attendanceDate);

    // Custom query to get attendance statistics for a student in a term
    @Query("SELECT a.status, COUNT(a) FROM Attendance a WHERE a.student = :student AND a.term = :term GROUP BY a.status")
    List<Object[]> getAttendanceStatsByStudentAndTerm(@Param("student") Student student, @Param("term") Term term);

    // Custom query to get attendance statistics for a class in a term
    @Query("SELECT a.status, COUNT(a) FROM Attendance a WHERE a.schoolClass = :schoolClass AND a.term = :term GROUP BY a.status")
    List<Object[]> getAttendanceStatsByClassAndTerm(@Param("schoolClass") SchoolClass schoolClass, @Param("term") Term term);

    // Find all attendance records for students in a specific class
    @Query("SELECT a FROM Attendance a WHERE a.student IN (SELECT s FROM Student s WHERE s.schoolClass = :schoolClass)")
    List<Attendance> findByStudentsInClass(@Param("schoolClass") SchoolClass schoolClass);

    // Check if attendance exists for student on a specific date
    boolean existsByStudentAndAttendanceDate(Student student, LocalDate attendanceDate);

    // Find recent attendance records for a student (last N days)
    @Query("SELECT a FROM Attendance a WHERE a.student = :student AND a.attendanceDate >= :fromDate ORDER BY a.attendanceDate DESC")
    List<Attendance> findRecentAttendanceByStudent(@Param("student") Student student, @Param("fromDate") LocalDate fromDate);

    // Find attendance records that need follow-up (e.g., unexcused absences)
    @Query("SELECT a FROM Attendance a WHERE a.status = 'ABSENT' AND a.excused = false AND a.attendanceDate BETWEEN :startDate AND :endDate")
    List<Attendance> findUnexcusedAbsences(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
