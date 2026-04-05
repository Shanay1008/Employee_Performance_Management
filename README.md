Employee Performance Management System
A Spring Boot backend application designed to track employee performance by managing tasks, collecting manager reviews, and generating mathematical performance summaries.

Features
Employee Management: Create and view employees with assigned roles and departments.

Task Tracking: Assign tasks to employees and update their status (Pending/Completed).

Performance Reviews: Managers can submit 1-5 star ratings and feedback for employees.

Automated Summaries: Calculates dynamic performance metrics (Task Completion Rate & Average Rating).

Database Integration: In-memory H2 Database for seamless testing.

Data Security: Strict input validation using Jakarta annotations (@NotBlank, @Email, @Min, @Max).

Clean Architecture: Strict separation of Controller, Service, and Repository layers.

Tech Stack

Java 21

Spring Boot 3.3.5

Spring Data JPA

H2 Database

Maven

Postman (API Testing)

Project Structure
com.pict.controller - Exposes REST APIs to the web

com.pict.service - Handles all mathematical and business logic

com.pict.repository - Manages automated database SQL queries

com.pict.entity - Database table models (Employee, Task, PerformanceReview)

How to Run

Step 1: Clone the repository

Bash
git clone https://github.com/Shanay1008/Employee_Performance_Management

Step 2: Start the Server

Locate src/main/java/com/pict/CapstoneApplication.java.

Run the project.

Check the console to ensure Tomcat started on port 8080.

Step 3: Use Postman for Testing

1. Create Employee

Method: POST

URL: http://localhost:8080/api/performance/employees

Body (raw / JSON):

JSON
{
"name": "Rahul Sharma",
"email": "rahul.s@company.com",
"department": "Engineering",
"role": "EMPLOYEE"
}
👉 Click Send (Rahul will be assigned ID 1)

2. Assign Task to Employee

Method: POST

URL: http://localhost:8080/api/performance/tasks/1?taskTitle=Fix Login Bug
👉 Click Send (Creates a task for Rahul with status PENDING)

3. Complete a Task

Method: PUT

URL: http://localhost:8080/api/performance/tasks/1/complete
👉 Click Send (Changes Task 1 status to COMPLETED)

4. Add Manager Review

Method: POST

URL: http://localhost:8080/api/performance/reviews

Body (raw / JSON):

JSON
{
"rating": 4,
"comments": "Great work on the login bug, Rahul!",
"reviewPeriod": "Q1-2026",
"managerId": 3,
"employee": {
"id": 1
}
}
👉 Click Send (Assigns a 4-star rating to Rahul)

5. Get Final Performance Summary

Method: GET

URL: http://localhost:8080/api/performance/summary/1
👉 Click Send

Expected Output:

JSON
{
"totalTasks": 1,
"reviewCount": 1,
"employeeName": "Rahul Sharma",
"completionRate": "100.00%",
"department": "Engineering",
"averageRating": "4.00",
"completedTasks": 1
}
