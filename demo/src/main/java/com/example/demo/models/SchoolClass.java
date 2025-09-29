package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Classes")
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @OneToMany(mappedBy = "SchoolClass", cascade = CascadeType.ALL)
    private List<Student> students = new ArrayList<>();

    @OneToOne(mappedBy = "AssignedClass", cascade = CascadeType.ALL)
    private Teacher teacher;

    //setters and getters
    public int getId() { 
        return id; }
    public void setId(int id) { 
        this.id = id; }

    public String getName() { 
        return name; }
    public void setName(String name) { 
        this.name = name; }

    public List<Student> getStudents() { 
        return students; }
    public void setStudents(List<Student> students) { 
        this.students = students; }

    public Teacher getTeacher() { 
        return teacher; }
    public void setTeacher(Teacher teacher) { 
        this.teacher = teacher; }
    
    public void addStudent(Student student) {
        students.add(student);
        student.setSchoolClass(this);
    }
}