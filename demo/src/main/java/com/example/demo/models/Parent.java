package com.example.demo.models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "parents")
public class Parent {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String parentId; // e.g., "PAR2024001"
    
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Column(name = "alternate_phone")
    private String alternatePhone;
    
    @Column(name = "address", length = 500)
    private String address;
    
    @Column(name = "occupation")
    private String occupation;
    
    @Column(name = "workplace")
    private String workplace;
    
    @Column(name = "work_phone")
    private String workPhone;
    
    @Column(name = "relationship_to_student")
    private String relationshipToStudent; // "Father", "Mother", "Guardian", "Stepparent", etc.
    
    @Column(name = "is_primary_contact")
    private boolean isPrimaryContact = true;
    
    @Column(name = "is_emergency_contact")
    private boolean isEmergencyContact = true;
    
    @Column(name = "is_authorized_pickup")
    private boolean isAuthorizedPickup = true;
    
    @Column(name = "is_active")
    private boolean isActive = true;
    
    @Column(name = "preferred_contact_method")
    private String preferredContactMethod; // "EMAIL", "PHONE", "SMS"
    
    @Column(name = "notes", length = 1000)
    private String notes; // Special notes about the parent
    
    // Relationships
    @ManyToMany(mappedBy = "parents")
    private List<Student> children;
    
    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Notification> notifications;
    
    // Constructors
    public Parent() {}
    
    public Parent(String parentId, UserEntity user, String phoneNumber, 
                  String relationshipToStudent) {
        this.parentId = parentId;
        this.user = user;
        this.phoneNumber = phoneNumber;
        this.relationshipToStudent = relationshipToStudent;
        this.isPrimaryContact = true;
        this.isEmergencyContact = true;
        this.isAuthorizedPickup = true;
        this.isActive = true;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getParentId() {
        return parentId;
    }
    
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    
    public UserEntity getUser() {
        return user;
    }
    
    public void setUser(UserEntity user) {
        this.user = user;
    }
    
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getAlternatePhone() {
        return alternatePhone;
    }
    
    public void setAlternatePhone(String alternatePhone) {
        this.alternatePhone = alternatePhone;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getOccupation() {
        return occupation;
    }
    
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
    
    public String getWorkplace() {
        return workplace;
    }
    
    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }
    
    public String getWorkPhone() {
        return workPhone;
    }
    
    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }
    
    public String getRelationshipToStudent() {
        return relationshipToStudent;
    }
    
    public void setRelationshipToStudent(String relationshipToStudent) {
        this.relationshipToStudent = relationshipToStudent;
    }
    
    public boolean isPrimaryContact() {
        return isPrimaryContact;
    }
    
    public void setPrimaryContact(boolean primaryContact) {
        isPrimaryContact = primaryContact;
    }
    
    public boolean isEmergencyContact() {
        return isEmergencyContact;
    }
    
    public void setEmergencyContact(boolean emergencyContact) {
        isEmergencyContact = emergencyContact;
    }
    
    public boolean isAuthorizedPickup() {
        return isAuthorizedPickup;
    }
    
    public void setAuthorizedPickup(boolean authorizedPickup) {
        isAuthorizedPickup = authorizedPickup;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    public String getPreferredContactMethod() {
        return preferredContactMethod;
    }
    
    public void setPreferredContactMethod(String preferredContactMethod) {
        this.preferredContactMethod = preferredContactMethod;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public List<Student> getChildren() {
        return children;
    }
    
    public void setChildren(List<Student> children) {
        this.children = children;
    }
    
    public List<Notification> getNotifications() {
        return notifications;
    }
    
    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
    
    // Utility methods
    public String getFullName() {
        return user != null ? user.getFirstName() + " " + user.getLastName() : "";
    }
    
    public int getAge() {
        if (dateOfBirth == null) return 0;
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }
    
    public int getChildrenCount() {
        return children != null ? children.size() : 0;
    }
    
    public boolean hasChildren() {
        return children != null && !children.isEmpty();
    }
    
    public String getPrimaryContactInfo() {
        if (preferredContactMethod == null) return phoneNumber;
        
        return switch (preferredContactMethod.toLowerCase()) {
            case "email" -> user != null ? user.getEmail() : "";
            case "phone", "sms" -> phoneNumber;
            default -> phoneNumber;
        };
    }
    
    @Override
    public String toString() {
        return "Parent{" +
                "id=" + id +
                ", parentId='" + parentId + '\'' +
                ", relationshipToStudent='" + relationshipToStudent + '\'' +
                ", isPrimaryContact=" + isPrimaryContact +
                ", isActive=" + isActive +
                '}';
    }
}