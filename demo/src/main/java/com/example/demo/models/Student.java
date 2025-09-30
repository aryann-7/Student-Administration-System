package com.example.demo.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate dateOfBirth;
    private String address;
    private String email;

    @ManyToOne //1 student - 1 class
    @JoinColumn(name = "Class_ID")
    private SchoolClass schoolClass;


    //refer to 1st parent
    @ManyToOne
    @JoinColumn(name = "parent1_id")
    private Parent parent1;

    //refer to 2nd parent
    @ManyToOne
    @JoinColumn(name = "parent2_id")
    private Parent parent2;

    //setters and getters
    public Long getId() { 
        return id; }
    public void setId(Long id) { 
        this.id = id; }

    public String getFirstName() { 
        return firstName; }
    public void setFirstName(String firstName) { 
        this.firstName = firstName; }

    public String getLastName() { 
        return lastName; }
    public void setLastName(String lastName) { 
        this.lastName = lastName; }

    public String getGender() { 
        return gender; }
    public void setGender(String gender) { 
        this.gender = gender; }

    public LocalDate getDateOfBirth() { 
        return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { 
        this.dateOfBirth = dateOfBirth; }

    public String getAddress() { 
        return address; }
    public void setAddress(String address) { 
        this.address = address; }

    public SchoolClass getSchoolClass() { 
        return schoolClass; }
    
    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
        if (schoolClass != null && !schoolClass.getStudents().contains(this)) {
            schoolClass.getStudents().add(this);
        }
    }

    public Parent getParent1() { 
        return parent1; }
    public void setParent1(Parent parent1) { 
        this.parent1 = parent1; }

    public Parent getParent2() { 
        return parent2; }
    public void setParent2(Parent parent2) { 
        this.parent2 = parent2; }

    public String getEmail() { 
        return email; }
    public void setEmail(String email) { 
        this.email = email; }
}