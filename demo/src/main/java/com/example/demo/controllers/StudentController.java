package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.models.Attendance;
import com.example.demo.models.Student;
import com.example.demo.repositories.AttendanceRepository;
import com.example.demo.repositories.StudentRepository;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @GetMapping("/student/dashboard/{studentId}") //catch student ID from path
    public String showStudentDashboard(@PathVariable("studentId") int studentId, Model model) {
        //debug student ID received
        System.out.println("Received studentId: " + studentId);

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        //add student to model
        model.addAttribute("student", student);
        
         //return student dashboard view
        return "student_dashboard";
    }

    //show attendance
    @GetMapping("/student/attendance")
    public String showStudentAttendance(@RequestParam("studentId") int studentId, Model model) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        //get student attendance records
        List<Attendance> attendanceRecords = attendanceRepository.findByStudent(student);

        //add records & student to model
        model.addAttribute("student", student);
        model.addAttribute("attendanceRecords", attendanceRecords);

         //return student attendance view
        return "student_attendance";
    }
}