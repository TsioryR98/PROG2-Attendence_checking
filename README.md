# Attendance Checking Application

This is a **Spring Boot** application designed to manage student attendance, courses, enrollment, sessions, and other related features such as teachers and convocations.

# Summary of Endpoints

## Student Management
- **GET** `/student`: Retrieve all students.
- **GET** `/student/{studentId}`: Retrieve a student by ID.
- **POST** `/newStudent`: Create a new student.
- **PUT** `/student/{studentId}`: Update a student.
- **DELETE** `/student/{studentId}`: Delete a student.
- **GET** `/student/{studentId}/absence`: Retrieve absences for a student.
- **GET** `/student/{studentId}/courses/{courseId}/absence`: Retrieve absences for a student in a specific course.
- **GET** `/student/{studentId}/absences`: Retrieve absences for a student within a date interval.
- **GET** `/student/absences:`: count justified or not_justified absences  within a date interval.

---

## Teacher Management
- **GET** `/teacher`: Retrieve all teachers.
- **GET** `/teacher/{teacherId}`: Retrieve a teacher by ID.
- **POST** `/newTeacher`: Create a new teacher.
- **PUT** `/teacher/{teacherId}`: Update a teacher.
- **DELETE** `/teacher/{teacherId}`: Delete a teacher.

---

## Attendance Management
- **GET** `/attendances`: Retrieve all attendances.
- **GET** `/attendances/{attendanceId}`: Retrieve attendance by ID.
- **POST** `/attendances`: Create a new attendance record.
- **PUT** `/attendances/{attendanceId}`: Update an attendance record.
- **DELETE** `/attendances/{attendanceId}`: Delete attendance by ID.
- **GET** `/attendances/session/{sessionId}`: Retrieve attendance by session ID.
- **GET** `/attendances/absence/proof`: Retrieve absences with proof.

---

## Course Management
- **GET** `/courses`: Retrieve all courses.
- **GET** `/courses/{courseId}`: Retrieve a course by ID.
- **POST** `/courses`: Create a new course.
- **PUT** `/courses/{courseId}`: Update a course.
- **DELETE** `/courses/{courseId}`: Delete a course.
- **GET** `/courses/teacher`: Retrieve courses with their teachers.
- **GET** `/courses/teacher/{teacherId}`: Retrieve courses taught by a specific teacher.

---

## Enrollment Management
- **GET** `/enrollments`: Retrieve all enrollments.
- **GET** `/enrollments/{enrollmentId}`: Retrieve an enrollment by ID.
- **POST** `/enrollments`: Create a new enrollment.
- **PUT** `/enrollments/{enrollmentId}`: Update an enrollment.
- **DELETE** `/enrollments/{enrollmentId}`: Delete an enrollment.
- **GET** `/enrollments/course`: Retrieve all students with their courses.
- **GET** `/enrollments/course/{courseId}`: Retrieve students enrolled in a specific course.

---

## Session Management
- **GET** `/sessions`: Retrieve all sessions.
- **GET** `/sessions/{sessionId}`: Retrieve a session by ID.
- **POST** `/sessions`: Create a new session.
- **PUT** `/sessions/{sessionId}`: Update a session.
- **DELETE** `/sessions/{sessionId}`: Delete a session.
- **GET** `/sessions/course/{courseId}`: Retrieve sessions for a specific course.

---

## Convocation Management
- **GET** `/convocations`: Retrieve all convocations.
- **GET** `/convocations/{convocationId}`: Retrieve a convocation by ID.
- **POST** `/convocations`: Create a new convocation.
- **PUT** `/convocations/{convocationId}`: Update a convocation.
- **DELETE** `/convocations/{convocationId}`: Delete a convocation.

## How to Launch the Application

### Prerequisites

Before running the application, make sure you have the following installed:

- **Java 17+**
- **Maven** (for building the project)
- **PostgreSQL**
- **Spring Boot**

### Package Structure

- **com.attendence_checking**: This is the main package where the application is initiated.
- **Controller**: Contains all the REST API controllers that handle HTTP requests.
- **Models**: Defines the data models and entities used in the application.
- **Repository**: Handles interactions with the database.
- **Service**: Contains the business logic and service layer for the application.
- 
### OpenAPI spec for endpoints tests in 
```src/main/java/Attendence.yaml```

---

### Steps to Run

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/TsioryR98/PROG2-Attendence_checking
   cd attendance-checking
    ```   
2. **Set Up database**:
   ```bash
   CREATE DATABASE attendance_checking
   ```
3. **Run the Application**: To run the application via Maven, use the following command:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
 If you are using an IDE like **IntelliJ IDEA** , you can run by clicking the "Run" button provided by your IDE.

4. **Navigator access** :

Once the application is running, you can access it by navigating to the following URL in your browser:
   ```http://localhost:8080```
