package com.example.demo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.models.Teacher;
import com.example.demo.models.Term;
import com.example.demo.repositories.TermRepository;
import com.example.demo.services.AttendanceService;
import com.example.demo.services.StudentService;
import com.example.demo.services.TeacherService;
import com.example.demo.services.TermService;

@Controller // Keep this as @Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TermService termService;
    
    @Autowired
    private TeacherService teacherService;
    
    @Autowired
    private TermRepository termRepository;
    
    @GetMapping("/dashboard/{teacherId}") //catch teacher ID from path
    public String teacherDashboard(Model model, @PathVariable int teacherId) {
        // Fetch teacher by ID
        Teacher teacher = teacherService.getTeacherById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        // Pass teacher and assigned class to the model
        model.addAttribute("teacher", teacher);
        model.addAttribute("classAssigned", teacher.getClassAssigned());

        //return teacher dashboard view
        return "teacher_dashboard";
    }

    @GetMapping("/attendance/{teacherId}")
    public String showAttendanceForm(@PathVariable int teacherId, Model model) {
        //fetch teacher by ID
        Teacher teacher = teacherService.getTeacherById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        //add teacher data to model
        model.addAttribute("teacher", teacher);
        model.addAttribute("terms", termService.getAllTerms());
        model.addAttribute("students", studentService.getAllStudents());
        
        //return attendance form view
        return "teacher_attendance";
    }


    @PostMapping("/take-attendance")
    public ResponseEntity<String> takeAttendance(
            @RequestParam("termId") Long termId, @RequestParam("weekNumber") Integer weekNumber,
            @RequestParam("day") String day, @RequestParam Map<String, String> attendanceStatus) {

        System.out.println("Term ID: " + termId);
        System.out.println("Week Number: " + weekNumber);
        System.out.println("Day: " + day);
        System.out.println("Attendance Status: " + attendanceStatus);

        //get term by ID
        Term term = termRepository.findById(termId)
                .orElse(null);

        if (term == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Term ID!");
        }

        //process each student attendance entry
        attendanceStatus.forEach((key, status) -> {
            if (key.startsWith("attendanceStatus[")) {
                String studentIdString = key.replace("attendanceStatus[", "").replace("]", "");
                try {
                    int studentId = Integer.parseInt(studentIdString);
                    System.out.println("Student ID: " + studentId + ", Status: " + status);
                    attendanceService.saveAttendance(studentId, term, weekNumber, day, status);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid student ID format for key: " + key);
                }
            }
        });

        //validation message
        return ResponseEntity.status(HttpStatus.OK).body("Attendance recorded successfully!");
    }
}