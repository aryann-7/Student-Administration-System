package com.example.demo.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "notifications")
public class Notification {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", nullable = false)
    private UserEntity recipient; // The user who will receive this notification
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private UserEntity sender; // The user who sent this notification (can be null for system notifications)
    
    @Column(name = "title", nullable = false, length = 200)
    private String title;
    
    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private NotificationType type;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private NotificationPriority priority;
    
    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;
    
    @Column(name = "read_at")
    private LocalDateTime readAt;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "scheduled_for")
    private LocalDateTime scheduledFor; // For notifications to be sent later
    
    @Column(name = "sent_at")
    private LocalDateTime sentAt;
    
    // Additional data for specific notification types (stored as JSON string)
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;
    
    // Related entity references (optional - for linking to specific records)
    @Column(name = "related_entity_type")
    private String relatedEntityType; // e.g., "ATTENDANCE", "GRADE", "STUDENT"
    
    @Column(name = "related_entity_id")
    private Long relatedEntityId;
    
    // Enum for notification types
    public enum NotificationType {
        ATTENDANCE_ALERT,    // Low attendance warning
        GRADE_UPDATE,        // New grade posted
        SYSTEM_ANNOUNCEMENT, // School-wide announcements
        PARENT_MESSAGE,      // Message from parent
        TEACHER_MESSAGE,     // Message from teacher
        PAYMENT_DUE,         // Fee payment reminders
        EVENT_REMINDER,      // School events
        DISCIPLINARY_ACTION, // Behavioral issues
        ACADEMIC_ACHIEVEMENT, // Awards, honors
        GENERAL_INFO         // General information
    }
    
    // Enum for notification priority
    public enum NotificationPriority {
        LOW,
        MEDIUM,
        HIGH,
        URGENT
    }
    
    // Constructors
    public Notification() {
        this.createdAt = LocalDateTime.now();
        this.priority = NotificationPriority.MEDIUM;
    }
    
    public Notification(UserEntity recipient, String title, String message, NotificationType type) {
        this();
        this.recipient = recipient;
        this.title = title;
        this.message = message;
        this.type = type;
    }
    
    public Notification(UserEntity recipient, UserEntity sender, String title, 
                       String message, NotificationType type, NotificationPriority priority) {
        this();
        this.recipient = recipient;
        this.sender = sender;
        this.title = title;
        this.message = message;
        this.type = type;
        this.priority = priority;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public UserEntity getRecipient() {
        return recipient;
    }
    
    public void setRecipient(UserEntity recipient) {
        this.recipient = recipient;
    }
    
    public UserEntity getSender() {
        return sender;
    }
    
    public void setSender(UserEntity sender) {
        this.sender = sender;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public NotificationType getType() {
        return type;
    }
    
    public void setType(NotificationType type) {
        this.type = type;
    }
    
    public NotificationPriority getPriority() {
        return priority;
    }
    
    public void setPriority(NotificationPriority priority) {
        this.priority = priority;
    }
    
    public boolean isRead() {
        return isRead;
    }
    
    public void setRead(boolean read) {
        isRead = read;
        if (read && readAt == null) {
            this.readAt = LocalDateTime.now();
        }
    }
    
    public LocalDateTime getReadAt() {
        return readAt;
    }
    
    public void setReadAt(LocalDateTime readAt) {
        this.readAt = readAt;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getScheduledFor() {
        return scheduledFor;
    }
    
    public void setScheduledFor(LocalDateTime scheduledFor) {
        this.scheduledFor = scheduledFor;
    }
    
    public LocalDateTime getSentAt() {
        return sentAt;
    }
    
    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
    
    public String getMetadata() {
        return metadata;
    }
    
    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
    
    public String getRelatedEntityType() {
        return relatedEntityType;
    }
    
    public void setRelatedEntityType(String relatedEntityType) {
        this.relatedEntityType = relatedEntityType;
    }
    
    public Long getRelatedEntityId() {
        return relatedEntityId;
    }
    
    public void setRelatedEntityId(Long relatedEntityId) {
        this.relatedEntityId = relatedEntityId;
    }
    
    // Helper methods
    public void markAsRead() {
        this.isRead = true;
        this.readAt = LocalDateTime.now();
    }
    
    public void markAsUnread() {
        this.isRead = false;
        this.readAt = null;
    }
    
    public boolean isUrgent() {
        return priority == NotificationPriority.URGENT;
    }
    
    public boolean isHigh() {
        return priority == NotificationPriority.HIGH;
    }
    
    public boolean isSystemNotification() {
        return sender == null;
    }
    
    public boolean isScheduled() {
        return scheduledFor != null && scheduledFor.isAfter(LocalDateTime.now());
    }
    
    public boolean isSent() {
        return sentAt != null;
    }
    
    // Method to set related entity
    public void setRelatedEntity(String entityType, Long entityId) {
        this.relatedEntityType = entityType;
        this.relatedEntityId = entityId;
    }
    
    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", recipient=" + (recipient != null ? recipient.getUsername() : null) +
                ", sender=" + (sender != null ? sender.getUsername() : "SYSTEM") +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", priority=" + priority +
                ", isRead=" + isRead +
                ", createdAt=" + createdAt +
                '}';
    }
}