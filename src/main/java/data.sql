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
        comment TEXT
);

-- Summon table
CREATE TABLE Summon (
        id_summon INT PRIMARY KEY,
        id_convocation INT,
        id_student INT,
        summon_date DATE NOT NULL,
        FOREIGN KEY (id_convocation) REFERENCES Convocation(id_convocation),
        FOREIGN KEY (id_student) REFERENCES Student(id_student)
);

-- Manage table
CREATE TABLE Manage (
  id_manage INT PRIMARY KEY,
  id_convocation INT,
  id_teacher INT,
  FOREIGN KEY (id_convocation) REFERENCES Convocation(id_convocation),
  FOREIGN KEY (id_teacher) REFERENCES Teacher(id_teacher)
);
