package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Student;
import com.example.demo.repositories.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    //update or save student
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    //find student by ID
    public Optional<Student> getStudentById(int id) {
        return studentRepository.findById(id);
    }

    public List<Student> getAllStudents() {
        //return student list
        List<Student> students = studentRepository.findAll();
        System.out.println("Students: " + students);
        return students;
    }

    //delete a student by ID
    public void deleteStudent(int id) {
        studentRepository.deleteById(id);
    }
    
    public List<Student> getStudentsByIds(List<Integer> studentIds) {
        return studentRepository.findAllById(studentIds);
    }
}