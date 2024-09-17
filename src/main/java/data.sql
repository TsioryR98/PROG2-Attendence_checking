    drop DATABASE IF EXISTS attendance_checking;
    CREATE DATABASE attendance_checking;
    \c attendance_checking;

    -- Table Student
CREATE TABLE Student (
studentId int PRIMARY KEY,
lastName VARCHAR(255) NOT NULL,
firstName VARCHAR(255) NOT NULL,
dateOfBirth TIMESTAMP NOT NULL,
studentEmail VARCHAR(255) NOT NULL UNIQUE,
phoneNumber VARCHAR(15),
enrollmentDate TIMESTAMP NOT NULL,
academicYear VARCHAR(10) CHECK (academicYear = 'L1' OR academicYear = 'L2' OR academicYear = 'L3') NOT NULL
);

-- Table Teacher
    CREATE TABLE Teacher (
                             teacherId int PRIMARY KEY,
                             lastName VARCHAR(255) NOT NULL,
                             firstName VARCHAR(255) NOT NULL,
                             email VARCHAR(255) NOT NULL UNIQUE,
                             phoneNumber VARCHAR(15)
    );

-- Table Course
    CREATE TABLE Course (
                            courseId int PRIMARY KEY,
                            courseName VARCHAR(255) NOT NULL,
                            teacherId INT NOT NULL,
                            FOREIGN KEY (teacherId) REFERENCES Teacher(teacherId) ON DELETE CASCADE
    );

-- Table Session
    CREATE TABLE Session (
                             sessionId int PRIMARY KEY,
                             sessionDate TIMESTAMP NOT NULL,
                             courseId INT NOT NULL,
                             FOREIGN KEY (courseId) REFERENCES Course(courseId) ON DELETE CASCADE
    );

-- Table Enrollment
    CREATE TABLE Enrollment (
                                idEnrollment int PRIMARY KEY,
                                studentId INT NOT NULL,
                                courseId INT NOT NULL,
                                FOREIGN KEY (studentId) REFERENCES Student(studentId) ON DELETE CASCADE,
                                FOREIGN KEY (courseId) REFERENCES Course(courseId) ON DELETE CASCADE
    );

-- Table Attendance
    CREATE TABLE Attendance (
                                attendenceId int PRIMARY KEY,
                                attendingStatus VARCHAR(10) CHECK (attendingStatus = 'MISSING' OR attendingStatus = 'ATTENDING') NOT NULL,
                                justifiedStatus VARCHAR(15) CHECK (justifiedStatus = 'JUSTIFIED' OR justifiedStatus = 'NOT_JUSTIFIED') NOT NULL,
                                proof TEXT,
                                sessionId INT NOT NULL,
                                studentId INT NOT NULL,
                                FOREIGN KEY (sessionId) REFERENCES Session(sessionId) ON DELETE CASCADE,
                                FOREIGN KEY (studentId) REFERENCES Student(studentId) ON DELETE CASCADE
    );


    INSERT INTO Student (studentId, lastName, firstName, dateOfBirth, studentEmail, phoneNumber, enrollmentDate, academicYear) VALUES
    (1, 'Dupont', 'Alice', '2000-05-15', 'alice.dupont@example.com', '0123456789', '2023-09-01', 'L1'),
    (2, 'Martin', 'Bob', '1999-04-20', 'bob.martin@example.com', '0987654321', '2023-09-01', 'L2'),
    (3, 'Bernard', 'Claire', '2001-03-10', 'claire.bernard@example.com', '0147852369', '2023-09-01', 'L1'),
    (4, 'Lefevre', 'David', '1998-02-25', 'david.lefevre@example.com', '0169875432', '2023-09-01', 'L3'),
    (5, 'Moreau', 'Emma', '2000-01-30', 'emma.moreau@example.com', '0176543210', '2023-09-01', 'L2'),
    (6, 'Girard', 'François', '1997-12-05', 'francois.girard@example.com', '0187654321', '2023-09-01', 'L3')
    ON CONFLICT (studentId) DO NOTHING;


