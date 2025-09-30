package com.example.demo.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    private LocalDateTime date;

    @Column(name = "Is_Read", nullable = false)
    private boolean isRead;

    @ManyToOne
    @JoinColumn(name = "Parent_ID", nullable = false)
    private Parent parent;

    //setters, getters & constructors
    public Notification() {}

    public Notification(String message, LocalDateTime date) {
        this.message = message;
        this.date = date;
    }

    public Long getId() { 
        return id; }
    public void setId(Long id) { 
        this.id = id; }

    public String getMessage() { 
        return message; }
    public void setMessage(String message) { 
        this.message = message; }

    public LocalDateTime getDate() { 
        return date; }
    public void setDate(LocalDateTime date) { 
        this.date = date; }

    public boolean isRead() { 
        return isRead; }
    public void setRead(boolean isRead) { 
        this.isRead = isRead; }

    public Parent getParent() { 
        return parent; }
    public void setParent(Parent parent) { 
        this.parent = parent; }
}
