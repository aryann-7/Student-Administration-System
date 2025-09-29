package com.example.demo.controllers;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.demo.models.Parent;
import com.example.demo.models.Student;
import com.example.demo.models.Teacher;
import com.example.demo.repositories.ParentRepository;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.services.TeacherService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;//import service to fetch teacher details

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final TeacherService teacherService;
    private final ParentRepository parentRepository;
    private final StudentRepository studentRepository;

    //inject contructor & initialize all used repositories
    public CustomLoginSuccessHandler(TeacherService teacherService, ParentRepository parentRepository,StudentRepository studentRepository) {
        this.teacherService = teacherService;
        this.parentRepository = parentRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,Authentication authentication) throws IOException {
        String redirectUrl = determineTargetUrl(authentication);
        response.sendRedirect(redirectUrl);
    }

    private String determineTargetUrl(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Check the role and determine the target URL
        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            return switch (role) {
                case "ADMIN" -> "/admin/dashboard";
                case "TEACHER" -> redirectTeacherDashboard(authentication.getName());
                case "PARENT" -> redirectParentDashboard(authentication.getName());
                case "STUDENT" -> redirectStudentDashboard(authentication.getName());
                default -> "/"; // Fallback URL if mismatched role
            };
        }
        //redirect to home if no role found
        return "/";
    }

    private String redirectParentDashboard(String email) {
        //fetch email parent to get parent ID
        Parent parent = parentRepository.findByEmail(email);
        if (parent != null) {
            //redirect to parent dashboard with parent ID as query parameter
            return "/parent/dashboard?parentId=" + parent.getId();
        }
        //fallback if parent not found
        return "/";
    }

    private String redirectTeacherDashboard(String email) {
        //fetch teacher email to get teacher ID
        Teacher teacher = teacherService.findByEmail(email);
        if (teacher != null) {
            //redirect to teacher dashboard with teacher ID as path variable
            return "/teacher/dashboard/" + teacher.getId();
        }
        //fallback if teacher not found
        return "/";
    }
    
    private String redirectStudentDashboard(String email) {
        //fetch student email to get student ID
        Student student = studentRepository.findByEmail(email);
        if (student != null) {
            //redirect to student dashboard with student ID as path variable
            return "/student/dashboard/" + student.getId();
        }
        //fallback if student not found
        return "/";
    }
}