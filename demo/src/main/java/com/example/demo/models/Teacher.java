package com.example.demo.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "teachers")
public class Teacher {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String teacherId; // e.g., "TCH2024001"
    
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    
    @Column(name = "employee_id", unique = true)
    private String employeeId;
    
    @Column(name = "department")
    private String department; // e.g., "Mathematics", "Science", "English"
    
    @Column(name = "subject_specialization")
    private String subjectSpecialization; // Primary subject they teach
    
    @Column(name = "qualification", length = 500)
    private String qualification; // Educational qualifications
    
    @Column(name = "experience_years")
    private Integer experienceYears;
    
    @Column(name = "hire_date")
    private LocalDate hireDate;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Column(name = "emergency_contact_name")
    private String emergencyContactName;
    
    @Column(name = "emergency_contact_phone")
    private String emergencyContactPhone;
    
    @Column(name = "address", length = 500)
    private String address;
    
    @Column(name = "salary", precision = 10, scale = 2)
    private BigDecimal salary;
    
    @Column(name = "is_active")
    private boolean isActive = true;
    
    @Column(name = "is_head_of_department")
    private boolean isHeadOfDepartment = false;
    
    @Column(name = "office_room")
    private String officeRoom; // Room number or location
    
    @Column(name = "office_hours")
    private String officeHours; // e.g., "Mon-Fri 2:00-4:00 PM"
    
    @Column(name = "bio", length = 1000)
    private String bio; // Teacher biography/description
    
    // Relationships
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SchoolClass> classes;
    
    // Constructors
    public Teacher() {}
    
    public Teacher(String teacherId, UserEntity user, String department, String subjectSpecialization, LocalDate hireDate) {
        this.teacherId = teacherId;
        this.user = user;
        this.department = department;
        this.subjectSpecialization = subjectSpecialization;
        this.hireDate = hireDate;
        this.isActive = true;
        this.isHeadOfDepartment = false;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTeacherId() {
        return teacherId;
    }
    
    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
    
    public UserEntity getUser() {
        return user;
    }
    
    public void setUser(UserEntity user) {
        this.user = user;
    }
    
    public String getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public String getSubjectSpecialization() {
        return subjectSpecialization;
    }
    
    public void setSubjectSpecialization(String subjectSpecialization) {
        this.subjectSpecialization = subjectSpecialization;
    }
    
    public String getQualification() {
        return qualification;
    }
    
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
    
    public Integer getExperienceYears() {
        return experienceYears;
    }
    
    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }
    
    public LocalDate getHireDate() {
        return hireDate;
    }
    
    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
    
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
    
    public BigDecimal getSalary() {
        return salary;
    }
    
    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    public boolean isHeadOfDepartment() {
        return isHeadOfDepartment;
    }
    
    public void setHeadOfDepartment(boolean headOfDepartment) {
        isHeadOfDepartment = headOfDepartment;
    }
    
    public String getOfficeRoom() {
        return officeRoom;
    }
    
    public void setOfficeRoom(String officeRoom) {
        this.officeRoom = officeRoom;
    }
    
    public String getOfficeHours() {
        return officeHours;
    }
    
    public void setOfficeHours(String officeHours) {
        this.officeHours = officeHours;
    }
    
    public String getBio() {
        return bio;
    }
    
    public void setBio(String bio) {
        this.bio = bio;
    }
    
    public List<SchoolClass> getClasses() {
        return classes;
    }
    
    public void setClasses(List<SchoolClass> classes) {
        this.classes = classes;
    }
    
    // Utility methods
    public String getFullName() {
        return user != null ? user.getFirstName() + " " + user.getLastName() : "";
    }
    
    public int getAge() {
        if (dateOfBirth == null) return 0;
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }
    
    public int getYearsOfService() {
        if (hireDate == null) return 0;
        return LocalDate.now().getYear() - hireDate.getYear();
    }
    
    public int getClassCount() {
        return classes != null ? classes.size() : 0;
    }
    
    public boolean hasClasses() {
        return classes != null && !classes.isEmpty();
    }
    
    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", teacherId='" + teacherId + '\'' +
                ", department='" + department + '\'' +
                ", subjectSpecialization='" + subjectSpecialization + '\'' +
                ", isActive=" + isActive +
                ", isHeadOfDepartment=" + isHeadOfDepartment +
                '}';
    }
}