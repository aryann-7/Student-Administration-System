# Student Administration System

**CS241 Assignment Two - Group 8**

A comprehensive Spring Boot application for managing student administration at Compton Public School, featuring grade management, attendance tracking, and role-based access control.

## 👥 Team Members

- **Aryan Singh** (S11230987) - Project Leader & Manager
- **Krishnil Prasad** (S11022399) - UI & Frontend Designer
- **Naweed Ali** (S11229584) - Applications Analyst
- **Isaia Mocenacagi** (S11232294) - Systems Analyst

## 📋 Project Overview

This is a software system that aims to simplify and automate school academic processes, with functionality such as registering new students, teachers as well as managing student academic grades and their attendance.

## 🎯 Implementation Scope

The system has successfully implemented the following core features:

1. **User Account Management**: Created accounts for System Admin, Teachers, and Students
2. **Role-Based Access Control**: Implemented secure authentication and authorization for different user types
3. **Grade Management**: Teachers can manage student grades (add, edit, or delete marks)
4. **Attendance Tracking**: Teachers can take attendances of students and keep track of records

## ✨ Key Features

### Core Functionality

#### Attendance Tracking
- **FR1**: Teacher takes student attendance and marks as either absent, present, or late
- **FR2**: Teacher can edit student attendance (make changes)
- **FR3**: Student can view their weekly attendance

#### Grade Management
- **FR4**: Teachers can enter student marks, using which the grades are calculated
- **FR5**: Teachers can edit student marks, which would also change grade
- **FR6**: Students can view their marks and grades

### System Features

- **System Administration**: Configuring system administrators and their default accounts
- **Role-Based Access Control**: Implemented login for all user types with secure authentication
- **Class Management**: Created classes for students and school year levels
- **Student-Teacher Assignment**: Assigning students and teachers to classes
- **Subject Assignment**: Assign subjects to teachers for teaching at particular levels
- **Grade Management System**: Teachers can add, update, and delete student marks which automatically affects student grades
- **Attendance Tracking System**: Teachers can take student attendance and maintain comprehensive records

### Incomplete Features

- **Student Attendance Report Generation**: Partially implemented, not working
- **Librarian Module**: Completed on librarian side (frontend + backend) but not implemented in the student and admin dashboards (Partial Implementation)

## 🛠 Technology Stack

### Architecture
The system follows the **MVC (Model-View-Controller)** architecture pattern:

- **View**: HTML pages (Thymeleaf templates) rendering the system's user interfaces
- **Controller**: Handles interaction between the model and view components
- **Model**: Entity classes used to create database tables (Student, Teacher, Grade, Attendance, etc.)
- **DTO (Data Transfer Object)**: Methods used to interact with the database
- **Database**: MySQL database storing all data and system information

### Technologies Used

- **Backend Framework**: Java Spring Boot
- **Security**: Spring Security with JWT authentication
- **Database**: MySQL
- **Template Engine**: Thymeleaf
- **Build Tool**: Maven
- **ORM**: Spring Data JPA
- **Password Encryption**: BCrypt

## 📁 Project Structure

```
src/main/java/com/example/demo/
├── config/                          # Configuration classes
│   ├── CustomAuthenticationSuccessHandler.java
│   ├── DataLoader.java             # Initial data seeding
│   ├── PasswordEncoderConfig.java
│   ├── SecurityConfig.java
│   └── WebConfig.java
├── controller/                      # Controllers for all modules
│   ├── Admin*.java                 # Admin controllers
│   ├── Teacher*.java               # Teacher controllers
│   ├── Student*.java               # Student controllers
│   ├── Librarian*.java             # Library controllers
│   ├── AuthController.java
│   └── RootController.java
├── dto/                            # Data Transfer Objects
├── model/                          # Entity classes
│   ├── Attendance.java
│   ├── Book.java
│   ├── BorrowRecord.java
│   ├── Classroom.java
│   ├── Exam.java
│   ├── Grade.java
│   ├── Level.java
│   ├── Notification.java
│   ├── Role.java
│   ├── Subject.java
│   └── User.java
├── repository/                     # Data access layer
├── service/                        # Business logic layer
└── util/                          # Utility classes

src/main/resources/
├── templates/                      # Thymeleaf templates
│   ├── admin/                     # Admin views
│   ├── teacher/                   # Teacher views
│   ├── student/                   # Student views
│   └── library/                   # Library views
├── static/                        # Static resources
├── db/                           # Database migration scripts
│   ├── V1__create_tables.sql
│   ├── V2__add_indexes.sql
│   └── V3__modify_tables.sql
└── application.properties
```

## 🚀 Getting Started

### Prerequisites

- Java 11 or higher
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse, or VS Code recommended)

### Installation Steps

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd demo
   ```

2. **Configure the database**
   
   Create a MySQL database:
   ```sql
   CREATE DATABASE student_admin_db;
   ```
   
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/student_admin_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the application**
   
   Open your browser and navigate to: `http://localhost:8080`

## 💾 Database Setup

The application uses versioned SQL migration scripts: (Not FLYWAY)

- **V1__create_tables.sql**: Creates initial database schema with all tables
- **V2__add_indexes.sql**: Adds performance optimization indexes
- **V3__modify_tables.sql**: Contains schema modifications and updates

The database will be automatically initialized on first run through the `DataLoader` configuration class.


## 👥 User Modules

### User Registration & Authentication
- User login and logout for students, teachers, and administrators
- Role-based access control with secure authentication
- Unique username and password authentication

### Admin Panel
- System admins have oversight of all student data
- Manage user roles and permissions
- System-wide configuration and management

### Teacher Module
- Input attendance for students (Present, Absent, Late)
- Edit and update attendance records
- Enter student exam marks
- Calculate and manage grades
- Dashboard with attendance and grade information

### Student Module
- View personal grades and marks
- Access attendance information
- View weekly attendance summary
- Track academic progress

## 🧪 Testing & Integration

### Testing Methodology
**Java File with Unit Testing**
CS241Asg2ApplicationTests.java comes with 3 valid and invalid test cases for system logic and components.

**Black-Box Testing**
- Functions and controllers were tested with various inputs
- Evaluated whether outputs matched expected results
- Verified functional requirements against specifications

**Top-Down Integration Testing**
- Implemented logic paths between MVC components
- Tested on localhost to verify connections between components
- Validated dashboard artifacts and navigation flows

## 🔧 Development Practices

### Version Control
- Code maintained with backup copies for fallback scenarios
- Regular commits to prevent loss of work
- Branching strategy for feature development

### Project Collaboration
- Project hosted on GitHub for team collaboration
- All members worked collaboratively on the same repository
- Remote access enabled for distributed development

### Task Planning
- Development work equally distributed using a Kanban Board
- Implementation plan from Assignment 1 closely followed
- Regular task tracking and progress monitoring

### Continuous Integration
- All previously implemented modules re-tested when adding new features
- Ensured no conflicts with existing functionality
- Maintained system stability throughout development

## 📚 API Endpoints

### Authentication
- `POST /login` - User login
- `POST /logout` - User logout

### Admin Endpoints
- `/admin/dashboard` - Admin dashboard
- `/admin/students` - Manage students
- `/admin/teachers` - Manage teachers
- `/admin/subjects` - Manage subjects
- `/admin/classrooms` - Manage classrooms
- `/admin/levels` - Manage academic levels
- `/admin/grades` - Manage grades

### Teacher Endpoints
- `/teacher/dashboard` - Teacher dashboard
- `/teacher/attendance` - Attendance management
- `/teacher/grades` - Grade management
- `/teacher/exams` - Exam management

### Student Endpoints
- `/student/dashboard` - Student dashboard
- `/student/attendance` - View attendance
- `/student/grades` - View grades
- `/student/courses` - View enrolled courses

## 🤝 Contributing

This is an academic project for CS241. For contribution guidelines:

1. Follow the established code structure
2. Maintain consistent naming conventions
3. Test all changes before committing
4. Document new features and modifications
5. Use the Kanban board for task management

## 📝 Assignment Requirements

This project fulfills the following requirements for **CS241 Assignment Two**:

✅ Manage Student Grade functionality
✅ Manage Student Attendance functionality
✅ Role-based access control
✅ MVC architecture implementation
✅ Database integration with MySQL
✅ Thymeleaf templating for views
✅ Spring Security for authentication

**Note**: Remember to change default passwords and update security configurations before any production deployment.