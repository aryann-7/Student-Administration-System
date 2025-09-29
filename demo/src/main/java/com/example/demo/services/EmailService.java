package com.example.demo.services;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public void sendNotification(String email, String message) {

        // Mock logic
        System.out.println("Sending email to " + email + ": " + message);
    }
}