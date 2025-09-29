package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Teacher;
import com.example.demo.repositories.TeacherRepository;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    //update or save teacher
    public Teacher saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    //get teacher by ID
    public Optional<Teacher> getTeacherById(int id) {
        return teacherRepository.findById(id);
    }

    //teacher list
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    //delete teacher
    public void deleteTeacher(int id) {
        teacherRepository.deleteById(id);
    }

    //fetch teacher by email
    public Teacher findByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }
}