# Assignment Portal

A web-based Assignment Portal developed using Java Spring Boot that enables administrators to create and manage assignments while allowing students to securely view their assigned work. The application provides role-based authentication, a user-friendly interface, and efficient assignment management.

---

## Table of Contents

1. Project Overview
2. Features
3. Technologies Used
4. System Architecture
5. Modules
6. Database Design
7. Project Structure
8. Installation
9. Configuration
10. Running the Application
11. Application Workflow
12. Security
13. Future Enhancements
14. Screenshots
15. Author

---

# Project Overview

The Assignment Portal is a web application designed to simplify assignment management in educational institutions.

The system provides two types of users:

- Administrator
- Student

The Administrator manages students and assignments, while students can securely log in and view their assignments.

The application follows the MVC (Model-View-Controller) architecture using Spring Boot and Thymeleaf.

---

# Features

## Administrator

- Secure Login
- Dashboard
- Add Students
- Update Student Details
- Delete Students
- View Student List
- Create Assignments
- Update Assignments
- Delete Assignments
- View All Assignments
- Search Assignments
- Assign work to students

---

## Student

- Secure Login
- Dashboard
- View Assigned Assignments
- View Assignment Details
- Update Profile
- Change Password

---

## Common Features

- Authentication
- Authorization
- Session Management
- Responsive User Interface
- Form Validation
- Exception Handling

---

# Technologies Used

## Backend

- Java 17/21
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate

## Frontend

- HTML5
- CSS3
- Bootstrap
- Thymeleaf

## Database

- MySQL

## Build Tool

- Maven

## IDE

- IntelliJ IDEA / Eclipse

---

# System Architecture

```
          User
             |
             |
        Thymeleaf UI
             |
             |
    Spring MVC Controller
             |
             |
       Service Layer
             |
             |
      Repository Layer
             |
             |
          MySQL
```

---

# Modules

## Admin Module

Responsibilities:

- Login
- Manage Students
- Manage Assignments
- View Dashboard
- Update Profile

---

## Student Module

Responsibilities:

- Login
- View Assignments
- View Dashboard
- Update Profile

---

## Authentication Module

Responsibilities:

- Login Validation
- Session Creation
- Logout
- Role-Based Access

---

# Project Structure

```
AssignmentPortal
│
├── controller
│     ├── AdminController
│     ├── StudentController
│     └── LoginController
│
├── service
│     ├── AdminService
│     ├── StudentService
│     └── AssignmentService
│
├── repository
│     ├── StudentRepository
│     ├── AssignmentRepository
│     └── AdminRepository
│
├── entity
│     ├── Student
│     ├── Assignment
│     └── Admin
│
├── templates
│
├── static
│
└── application.properties
```

---

# Database Design

## Admin Table

| Column |
|---------|
| id |
| name |
| email |
| password |

---

## Student Table

| Column |
|---------|
| id |
| name |
| email |
| password |
| department |
| semester |

---

## Assignment Table

| Column |
|---------|
| id |
| title |
| description |
| dueDate |
| subject |
| createdDate |
| studentId |

---

# Entity Relationship

```
Admin
   |
   | creates
   |
Assignment
   |
   | assigned to
   |
Student
```

---

# Installation

Clone the repository

```
git clone https://github.com/yourusername/assignment-portal.git
```

Open the project in IntelliJ IDEA or Eclipse.

---

# Configure Database

Create a MySQL database.

```
CREATE DATABASE assignment_portal;
```

Update `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/assignment_portal

spring.datasource.username=root

spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true
```

---

# Run the Project

Using Maven

```
mvn spring-boot:run
```

Or run the main Spring Boot application.

The application starts on:

```
http://localhost:8080
```

---

# Application Workflow

### Administrator

1. Login
2. Open Dashboard
3. Add Students
4. Create Assignment
5. Assign to Student
6. Save Assignment
7. Student can view assignment

---

### Student

1. Login
2. Dashboard
3. View Assignments
4. Open Assignment
5. Read Details

---

# Validation

The application validates:

- Required Fields
- Email Format
- Duplicate Email
- Invalid Login
- Empty Fields

---

# Exception Handling

- Invalid Login
- Student Not Found
- Assignment Not Found
- Database Exceptions

---

# Security

- Role-Based Authentication
- Password Protection
- Session Management
- Unauthorized Access Restriction

---

# Future Enhancements

- Email Notification
- Marks Management
- Faculty Module
- Student Attendance
- Dashboard Analytics
- Spring Security Integration
- JWT Authentication
- REST APIs
- Docker Deployment
- Cloud Deployment

---

# Advantages

- Easy Assignment Management
- Reduced Paperwork
- Faster Communication
- Secure Authentication
- User-Friendly Interface
- Centralized Data Management

---

# Screenshots

Add screenshots of:

- Login Page
- Admin Dashboard
- Student Dashboard
- Create Assignment Page
- Student Assignment List
- Assignment Details

---

# Author

**Shreya M Prabhu**

MCA Graduate

Java Full Stack Developer

Technologies:
- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate
- Thymeleaf
- MySQL
