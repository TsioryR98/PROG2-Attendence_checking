    drop DATABASE IF EXISTS attendance_checking;
    CREATE DATABASE attendance_checking;
    \c attendance_checking;

    -- Teacher table
    CREATE TABLE Teacher (
        id_teacher INT PRIMARY KEY,
        last_name VARCHAR(255) NOT NULL,
        first_name VARCHAR(255) NOT NULL,
        contact VARCHAR(255),
        email_address VARCHAR(255) UNIQUE NOT NULL
    );

    -- Course table
    CREATE TABLE Course (
        id_course INT PRIMARY KEY,
        course_name VARCHAR(255) NOT NULL,
        id_teacher INT,
        FOREIGN KEY (id_teacher) REFERENCES Teacher(id_teacher)
    );

    -- Student table
    CREATE TABLE Student (
        id_student INT PRIMARY KEY,
        last_name VARCHAR(255) NOT NULL,
        first_name VARCHAR(255) NOT NULL,
        date_of_birth DATE NOT NULL,
        email VARCHAR(255) UNIQUE NOT NULL,
        phone_number VARCHAR(15),
        enrollment_date DATE NOT NULL,
        academic_year VARCHAR(10) NOT NULL
    );

    -- Enrollment table
    CREATE TABLE Enrollment (
        id_enrollment INT PRIMARY KEY,
        id_student INT,
        id_course INT,
        enrollment_date DATE NOT NULL,
        FOREIGN KEY (id_student) REFERENCES Student(id_student),
        FOREIGN KEY (id_course) REFERENCES Course(id_course)
    );

    -- Session table
    CREATE TABLE Session (
        id_session INT PRIMARY KEY,
        session_date DATE NOT NULL,
        id_course INT,
        FOREIGN KEY (id_course) REFERENCES Course(id_course)
    );

    -- Attendance table
    CREATE TABLE Attendance (
           id_attendance INT PRIMARY KEY,
           id_session INT,
           id_student INT,
           attending_course BOOL NOT NULL,
           justified_status BOOL,
           proof TEXT,
           FOREIGN KEY (id_session) REFERENCES Session(id_session),
           FOREIGN KEY (id_student) REFERENCES Student(id_student)
    );

    -- Convocation table
    CREATE TABLE Convocation (
            id_convocation INT PRIMARY KEY,
            convocation_date DATE NOT NULL,
            id_student INT,
            comment TEXT,
            id_teacher INT,
            FOREIGN KEY (id_student) REFERENCES Student(id_student),
            FOREIGN KEY (id_teacher) REFERENCES Teacher(id_teacher)
    );



    INSERT INTO Student (id_student, last_name, first_name, date_of_birth, email, phone_number, enrollment_date, academic_year) VALUES
    (1, 'Dupont', 'Alice', '2000-05-15', 'alice.dupont@example.com', '0123456789', '2023-09-01', 'L1'),
    (2, 'Martin', 'Bob', '1999-04-20', 'bob.martin@example.com', '0987654321', '2023-09-01', 'L2'),
    (3, 'Bernard', 'Claire', '2001-03-10', 'claire.bernard@example.com', '0147852369', '2023-09-01', 'L1'),
    (4, 'Lefevre', 'David', '1998-02-25', 'david.lefevre@example.com', '0169875432', '2023-09-01', 'L3'),
    (5, 'Moreau', 'Emma', '2000-01-30', 'emma.moreau@example.com', '0176543210', '2023-09-01', 'L2'),
    (6, 'Girard', 'François', '1997-12-05', 'francois.girard@example.com', '0187654321', '2023-09-01', 'L3');

