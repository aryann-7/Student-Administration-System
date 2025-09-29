package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.models.Student;
import com.example.demo.services.StudentService;
import com.example.demo.services.TeacherService;

@Controller
public class MainController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    //main page
    @GetMapping("/")
    public String home() {
        return "index"; //return template
    }
    
    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin_dashboard"; //return admin dashboard template
    }

    @GetMapping("/admin/create-student")
    public String createStudent() {
        return "create_student"; //return crete student template
    }
    
    @GetMapping("/admin/create-teacher")
    public String createTeacher() {
        return "create-teacher"; //return create teacher template
    }
    
    @GetMapping("/admin/create-class")
    public String showCreateClassForm(Model model) {
        model.addAttribute("teachers", teacherService.getAllTeachers());
        model.addAttribute("students", studentService.getAllStudents());
        return "create_class"; // Path for create-class template
    }
    
    // Show Create Term Form
    @GetMapping("/admin/create-term")
    public String showCreateTermForm(Model model) {
        return "create_term"; //return create term template
    }
    
    @GetMapping("/admin/update-account")
    public String updateStudentTeacher() {
        return "updateStudentTeacher";
    }
    
    @GetMapping("auth/reset-password")
    public String showResetPasswordPage() {
        return "reset_password";
    }

    @GetMapping("/admin/students")
    public String listStudents(Model model) {
        List<Student> students = studentService.getAllStudents();//fetch all students
        model.addAttribute("students", students);
        return "student_list";
    }

    @GetMapping("/admin/students/{id}")
    public String viewStudentProfile(@PathVariable int id, Model model) {
        Optional<Student> optionalStudent = studentService.getStudentById(id);

        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            model.addAttribute("student", student);
            return "student_profile";//student profile template
        } else {
            // if student not found, redirect to student list
            return "redirect:/admin/students"; 
        }
    }
}