package com.example.demo.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "attendance")
public class Attendance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private SchoolClass schoolClass;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "term_id", nullable = false)
    private Term term;
    
    @Column(name = "attendance_date", nullable = false)
    private LocalDate attendanceDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AttendanceStatus status;
    
    @Column(name = "marked_by")
    private String markedBy; // Teacher who marked the attendance
    
    @Column(name = "remarks")
    private String remarks; // Optional remarks (e.g., "Late", "Medical excuse")
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Enum for attendance status
    public enum AttendanceStatus {
        PRESENT,
        ABSENT,
        LATE,
        EXCUSED
    }
    
    // Constructors
    public Attendance() {
        this.createdAt = LocalDateTime.now();
    }
    
    public Attendance(Student student, SchoolClass schoolClass, Term term, 
                     LocalDate attendanceDate, AttendanceStatus status) {
        this();
        this.student = student;
        this.schoolClass = schoolClass;
        this.term = term;
        this.attendanceDate = attendanceDate;
        this.status = status;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public SchoolClass getSchoolClass() {
        return schoolClass;
    }
    
    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }
    
    public Term getTerm() {
        return term;
    }
    
    public void setTerm(Term term) {
        this.term = term;
    }
    
    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }
    
    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }
    
    public AttendanceStatus getStatus() {
        return status;
    }
    
    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }
    
    public String getMarkedBy() {
        return markedBy;
    }
    
    public void setMarkedBy(String markedBy) {
        this.markedBy = markedBy;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // JPA lifecycle methods
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    // Helper methods
    public boolean isPresent() {
        return status == AttendanceStatus.PRESENT;
    }
    
    public boolean isAbsent() {
        return status == AttendanceStatus.ABSENT;
    }
    
    public boolean isLate() {
        return status == AttendanceStatus.LATE;
    }
    
    public boolean isExcused() {
        return status == AttendanceStatus.EXCUSED;
    }
    
    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", student=" + (student != null ? student.getId() : null) +
                ", schoolClass=" + (schoolClass != null ? schoolClass.getId() : null) +
                ", attendanceDate=" + attendanceDate +
                ", status=" + status +
                ", markedBy='" + markedBy + '\'' +
                '}';
    }
}