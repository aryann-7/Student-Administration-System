package com.example.demo.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String studentId; // e.g., "STU2024001"
    
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    
    @ManyToOne
    @JoinColumn(name = "school_class_id")
    private SchoolClass schoolClass;
    
    @Column(name = "grade_level")
    private String gradeLevel; // e.g., "9th Grade", "10th Grade"
    
    @Column(name = "enrollment_date")
    private LocalDate enrollmentDate;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Column(name = "guardian_name")
    private String guardianName;
    
    @Column(name = "guardian_phone")
    private String guardianPhone;
    
    @Column(name = "guardian_email")
    private String guardianEmail;
    
    @Column(name = "emergency_contact_name")
    private String emergencyContactName;
    
    @Column(name = "emergency_contact_phone")
    private String emergencyContactPhone;
    
    @Column(name = "address", length = 500)
    private String address;
    
    @Column(name = "blood_group")
    private String bloodGroup;
    
    @Column(name = "medical_conditions", length = 1000)
    private String medicalConditions;
    
    @Column(name = "is_active")
    private boolean isActive = true;
    
    @Column(name = "graduation_date")
    private LocalDate graduationDate;
    
    @Column(name = "gpa")
    private Double gpa;
    
    // Relationships with other entities
    @ManyToMany
    @JoinTable(
        name = "student_parents",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "parent_id")
    )
    private List<Parent> parents;
    
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Attendance> attendances;
    
    // Constructors
    public Student() {}
    
    public Student(String studentId, UserEntity user, SchoolClass schoolClass, 
                  String gradeLevel, LocalDate enrollmentDate) {
        this.studentId = studentId;
        this.user = user;
        this.schoolClass = schoolClass;
        this.gradeLevel = gradeLevel;
        this.enrollmentDate = enrollmentDate;
        this.isActive = true;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getStudentId() {
        return studentId;
    }
    
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    
    public UserEntity getUser() {
        return user;
    }
    
    public void setUser(UserEntity user) {
        this.user = user;
    }
    
    public SchoolClass getSchoolClass() {
        return schoolClass;
    }
    
    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }
    
    public String getGradeLevel() {
        return gradeLevel;
    }
    
    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }
    
    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }
    
    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }
    
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getGuardianName() {
        return guardianName;
    }
    
    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }
    
    public String getGuardianPhone() {
        return guardianPhone;
    }
    
    public void setGuardianPhone(String guardianPhone) {
        this.guardianPhone = guardianPhone;
    }
    
    public String getGuardianEmail() {
        return guardianEmail;
    }
    
    public void setGuardianEmail(String guardianEmail) {
        this.guardianEmail = guardianEmail;
    }
    
    public String getEmergencyContactName() {
        return emergencyContactName;
    }
    
    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }
    
    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }
    
    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getBloodGroup() {
        return bloodGroup;
    }
    
    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
    
    public String getMedicalConditions() {
        return medicalConditions;
    }
    
    public void setMedicalConditions(String medicalConditions) {
        this.medicalConditions = medicalConditions;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    public LocalDate getGraduationDate() {
        return graduationDate;
    }
    
    public void setGraduationDate(LocalDate graduationDate) {
        this.graduationDate = graduationDate;
    }
    
    public Double getGpa() {
        return gpa;
    }
    
    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }
    
    public List<Parent> getParents() {
        return parents;
    }
    
    public void setParents(List<Parent> parents) {
        this.parents = parents;
    }
    
    public List<Attendance> getAttendances() {
        return attendances;
    }
    
    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }
    
    // Utility methods
    public String getFullName() {
        return user != null ? user.getFirstName() + " " + user.getLastName() : "";
    }
    
    public int getAge() {
        if (dateOfBirth == null) return 0;
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }
    
    public boolean isGraduated() {
        return graduationDate != null && graduationDate.isBefore(LocalDate.now());
    }
    
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", studentId='" + studentId + '\'' +
                ", gradeLevel='" + gradeLevel + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}