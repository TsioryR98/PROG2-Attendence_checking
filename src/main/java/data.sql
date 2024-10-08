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
justifiedStatus VARCHAR(15) CHECK (justifiedStatus = 'JUSTIFIED' OR justifiedStatus = 'NOT_JUSTIFIED') NULL,
proof TEXT,
sessionId INT NOT NULL,
studentId INT NOT NULL,
FOREIGN KEY (sessionId) REFERENCES Session(sessionId) ON DELETE CASCADE,
FOREIGN KEY (studentId) REFERENCES Student(studentId) ON DELETE CASCADE
);

 CREATE TABLE convocation (
convocationId INT PRIMARY KEY,
teacherId INT NOT NULL,
studentId INT NOT NULL,
convocationDate TIMESTAMP NOT NULL,
reason TEXT,
FOREIGN KEY (teacherId) REFERENCES teacher(teacherId) ON DELETE CASCADE,
FOREIGN KEY (studentId) REFERENCES student(studentId) ON DELETE CASCADE
 );

---------------ALL INSERTS---------------------------
INSERT INTO convocation (convocationId, teacherId, studentId, convocationDate, reason)
VALUES (1, 5, 14, '2024-09-21', 'Frequent absence from class');

INSERT INTO convocation (convocationId, teacherId, studentId, convocationDate, reason)
VALUES (2, 3, 8, '2024-09-22', 'Misbehavior in class');

INSERT INTO convocation (convocationId, teacherId, studentId, convocationDate, reason)
VALUES (3, 7, 3, '2024-09-23', 'Violation of school rules');

INSERT INTO convocation (convocationId, teacherId, studentId, convocationDate, reason)
VALUES (4, 1, 19, '2024-09-24', 'Consistent tardiness to classes');

INSERT INTO convocation (convocationId, teacherId, studentId, convocationDate, reason)
VALUES (5, 6, 5, '2024-09-25', 'Failure to submit assignments on time');

INSERT INTO Student (studentId, lastName, firstName, dateOfBirth, studentEmail, phoneNumber, enrollmentDate, academicYear) VALUES
(1, 'Dupont', 'Alice', '2000-05-15', 'alice.dupont@example.com', '0123456789', '2023-09-01', 'L1'),
(2, 'Martin', 'Bob', '1999-04-20', 'bob.martin@example.com', '0987654321', '2023-09-01', 'L2'),
(3, 'Bernard', 'Claire', '2001-03-10', 'claire.bernard@example.com', '0147852369', '2023-09-01', 'L1'),
(4, 'Lefevre', 'David', '1998-02-25', 'david.lefevre@example.com', '0169875432', '2023-09-01', 'L3'),
(5, 'Moreau', 'Emma', '2000-01-30', 'emma.moreau@example.com', '0176543210', '2023-09-01', 'L2'),
(6, 'Girard', 'François', '1997-12-05', 'francois.girard@example.com', '0187654321', '2023-09-01', 'L3'),
(7, 'Wilson', 'Lucas', '1999-07-05', 'lucaswilson@example.com', '5556667777', '2019-09-01', 'L1'),
(8, 'Moore', 'Olivia', '1998-08-15', 'oliviamoore@example.com', '6667778888', '2020-09-01', 'L2'),
(9, 'Taylor', 'Emma', '2001-09-09', 'emmataylor@example.com', '7778889999', '2021-09-01', 'L3'),
(10, 'Anderson', 'James', '2000-10-01', 'jamesanderson@example.com', '8889990000', '2019-09-01', 'L1')
ON CONFLICT (studentId) DO NOTHING;

INSERT INTO Student (studentId, lastName, firstName, dateOfBirth, studentEmail, phoneNumber, enrollmentDate, academicYear)
VALUES
(11, 'Garcia', 'Isabella', '2001-11-05', 'isabellagarcia@example.com', '1112223334', '2022-09-01', 'L1'),
(12, 'Martinez', 'Michael', '2000-12-10', 'michaelmartinez@example.com', '2223334445', '2021-09-01', 'L2'),
(13, 'Rodriguez', 'Sophia', '1999-10-22', 'sophiarodriguez@example.com', '3334445556', '2020-09-01', 'L3'),
(14, 'Hernandez', 'Daniel', '1998-09-15', 'danielhernandez@example.com', '4445556667', '2019-09-01', 'L1'),
(15, 'Lopez', 'Mia', '2002-08-18', 'mialopez@example.com', '5556667778', '2022-09-01', 'L1'),
(16, 'Gonzalez', 'Benjamin', '2001-07-25', 'benjamingonzalez@example.com', '6667778889', '2021-09-01', 'L2'),
(17, 'Perez', 'Emily', '2000-06-30', 'emilyperez@example.com', '7778889990', '2020-09-01', 'L3'),
(18, 'Wilson', 'Lucas', '1999-05-12', 'luca@example.com', '8889990001', '2019-09-01', 'L1'),
(19, 'Sanchez', 'Emma', '2002-04-19', 'emmasanchez@example.com', '9990001112', '2022-09-01', 'L1'),
(20, 'Ramirez', 'Jacob', '2001-03-27', 'jacobramirez@example.com', '0001112223', '2021-09-01', 'L2');


INSERT INTO Teacher (teacherId, lastName, firstName, email, phoneNumber) VALUES
(1, 'Leblanc', 'Pierre', 'pierre.leblanc@example.com', '0600000001'),
(2, 'Garnier', 'Lucie', 'lucie.garnier@example.com', '0600000002'),
(3, 'Rousseau', 'Clara', 'clara.rousseau@example.com', '0600000003'),
(4, 'Dufresne', 'Thomas', 'thomas.dufresne@example.com', '0600000004'),
(5, 'Dupuis', 'Isabelle', 'isabelle.dupuis@example.com', '0600000005'),
(6, 'Barre', 'François', 'francois.barre@example.com', '0600000006'),
(7, 'Lemoine', 'Elise', 'elise.lemoine@example.com', '0600000007'),
(8, 'Vidal', 'Jacques', 'jacques.vidal@example.com', '0600000008');


INSERT INTO Course (courseId, courseName, teacherId)
VALUES
(1, 'Introduction to Computer Science', 5),
(2, 'Data Structures', 1),
(3, 'Algorithms', 2),
(4, 'Operating Systems', 3),
(5, 'Database Systems', 4),
(6, 'Computer Networks', 6),
(7, 'Software Engineering', 7),
(8, 'Artificial Intelligence', 8);


INSERT INTO Session (sessionId, sessionDate, courseId)
VALUES
(1, '2024-01-10 08:00:00', 1),
(2, '2024-01-11 09:00:00', 2),
(3, '2024-01-12 10:00:00', 3),
(4, '2024-01-13 11:00:00', 4),
(5, '2024-01-14 12:00:00', 5),
(6, '2024-01-15 13:00:00', 6),
(7, '2024-01-16 14:00:00', 7),
(8, '2024-01-17 15:00:00', 8),
(9, '2024-01-18 16:00:00', 1),
(10, '2024-01-19 17:00:00', 2),
(11, '2024-01-20 08:00:00', 3),
(12, '2024-01-21 09:00:00', 4),
(13, '2024-01-22 10:00:00', 5),
(14, '2024-01-23 11:00:00', 6),
(15, '2024-01-23 11:00:00', 6),
(16, '2024-01-24 12:00:00', 7);


INSERT INTO Attendance (attendenceId, attendingStatus, justifiedStatus, proof, sessionId, studentId)
VALUES
(1, 'ATTENDING', 'NOT_JUSTIFIED', NULL, 1, 11),
(2, 'MISSING', 'JUSTIFIED', 'Medical certificate', 2, 12),
(3, 'ATTENDING', 'NOT_JUSTIFIED', NULL, 3, 13),
(4, 'MISSING', 'JUSTIFIED', 'Doctor’s note', 4, 14),
(5, 'ATTENDING', 'NOT_JUSTIFIED', NULL, 5, 15),
(6, 'MISSING', 'NOT_JUSTIFIED', NULL, 6, 16),
(7, 'ATTENDING', 'NOT_JUSTIFIED', NULL, 7, 17),
(8, 'MISSING', 'NOT_JUSTIFIED', NULL, 8, 18),
(9, 'ATTENDING', 'JUSTIFIED', 'Work excuse', 9, 19),
(10, 'MISSING', 'JUSTIFIED', 'Family emergency', 10, 20);

INSERT INTO Attendance (attendenceId, attendingStatus, justifiedStatus, proof, sessionId, studentId)
VALUES
(11, 'ATTENDING', 'NOT_JUSTIFIED', NULL, 16, 1),
(12, 'MISSING', 'JUSTIFIED', 'Medical certificate', 2, 2),
(13, 'ATTENDING', 'NOT_JUSTIFIED', NULL, 13, 3),
(14, 'MISSING', 'JUSTIFIED', 'Doctor’s note', 4, 4),
(15, 'ATTENDING', 'NOT_JUSTIFIED', NULL, 5, 5),
(16, 'MISSING', 'NOT_JUSTIFIED', NULL, 8, 6),
(17, 'ATTENDING', 'NOT_JUSTIFIED', NULL, 15, 7),
(18, 'MISSING', 'JUSTIFIED', 'Work excuse', 9, 8),
(19, 'ATTENDING', 'NOT_JUSTIFIED', NULL, 10, 9),
(20, 'MISSING', 'JUSTIFIED', 'Family emergency', 11, 10);


INSERT INTO Enrollment (idEnrollment, studentId, courseId)
VALUES
(1, 1, 1),
(2, 2, 2),
(3, 3, 3),
(4, 4, 4),
(5, 5, 5),
(6, 6, 6),
(7, 7, 7),
(8, 8, 8),
(9, 9, 1),
(10, 10, 2),
(11, 11, 3),
(12, 12, 4),
(13, 13, 5),
(14, 14, 6),
(15, 15, 7),
(16, 16, 8),
(17, 17, 1),
(18, 18, 2),
(19, 19, 3),
(20, 20, 4),
(21, 1, 5),
(22, 2, 6),
(23, 3, 7),
(24, 4, 8),
(25, 5, 1),
(26, 6, 2),
(27, 7, 3),
(28, 8, 4),
(29, 9, 5),
(30, 10, 6);


-------------------ALL QUERIES----------------

 SELECT studentId, lastName, firstName, dateOfBirth, studentEmail, phoneNumber, enrollmentDate,academicYear FROM Student GROUP BY academicYear, studentId, lastName, firstName, dateOfBirth, studentEmail, phoneNumber, enrollmentDate;

--OK--1 .Requête pour obtenir tous les étudiants inscrits à un cours spécifique ou tous

 SELECT e.idEnrollment,s.studentId, s.lastName, s.firstName, s.studentEmail,c.courseName
 FROM Enrollment e
 JOIN student s ON e.studentId=s.studentId
 JOIN Course c ON e.courseId=c.courseId;

 SELECT e.idEnrollment,s.studentId, s.lastName, s.firstName, s.studentEmail,c.courseName
 FROM Enrollment e
 JOIN student s ON e.studentId=s.studentId
 JOIN Course c ON e.courseId=c.courseId
 WHERE e.courseId=1;

--OK--2. Requête pour obtenir les cours enseignés par un professeur spécifique ou tous les profs

 SELECT c.courseId, c.courseName, t.firstName,t.lastName,t.email
 FROM Course c
 JOIN Teacher t ON c.teacherId = t.teacherId;

 SELECT c.courseId, c.courseName, t.firstName,t.lastName,t.email
 FROM Course c
 JOIN Teacher t ON c.teacherId = t.teacherId
 WHERE t.teacherId =2;

---OK 3. Requête pour obtenir l'assiduité des étudiants pour une session spécifique

SELECT a.attendenceId,c.courseName,s.studentId,
s.firstName,s.lastName,s.academicYear,ses.sessionDate,a.attendingStatus,
a.justifiedStatus
FROM Attendance a
JOIN Student s ON a.studentId = s.studentId
JOIN Session ses ON a.sessionId = ses.sessionId
JOIN Course c ON ses.courseId = c.courseId
WHERE a.sessionId = 5
GROUP BY s.academicYear, a.attendenceId, c.courseName, ses.sessionDate, s.studentId,
s.firstName, s.lastName, a.attendingStatus, a.justifiedStatus
ORDER BY s.academicYear;


---OK 4. Requête pour obtenir toutes les sessions d'un cours spécifique session/{sessionId}/course
SELECT c.courseId,
c.courseName,
ses.sessionId,
ses.sessionDate
FROM Session ses
 JOIN Course c ON ses.courseId = c.courseId
WHERE c.courseId = 1;


--OK 10. Requête pour obtenir toutes les absences pour un etudiant specifique et course specifique

SELECT s.firstName,
s.lastName,
ses.sessionDate,
c.courseName,
a.attendingStatus,
a.justifiedStatus
FROM Attendance a
JOIN Student s ON a.studentId = s.studentId
JOIN Session ses ON a.sessionId = ses.sessionId
JOIN Course c ON ses.courseId = c.courseId
WHERE a.attendingStatus = 'MISSING' AND s.studentId =1 AND c.courseId=5;


--KO 11. tous les cours avec les enseignants responsables et les étudiants inscrits

SELECT
c.courseName,
t.firstName AS teacherFirstName,
t.lastName AS teacherLastName,
s.firstName AS studentFirstName,
s.lastName AS studentLastName,
a.attendingStatus AS attendanceStatus,
a.justifiedStatus AS justifiedStatus,
a.proof
FROM
Course c
INNER JOIN
Teacher t ON c.teacherId = t.teacherId
INNER JOIN
Enrollment e ON c.courseId = e.courseId
INNER JOIN
Student s ON e.studentId = s.studentId
INNER JOIN
Attendance a ON s.studentId = a.attendenceId
ORDER BY c.courseName;

--OK 13.Liste des absences, avec le statut de justification et les pieces justificatives
SELECT a.attendenceId,
s.studentId,
s.lastName,
s.firstName,
s.academicYear,
a.justifiedStatus,
a.proof,
a.sessionId,
se.sessionDate
FROM Attendance a
INNER JOIN Student s ON a.studentId = s.studentId
INNER JOIN Session se ON a.sessionId = se.sessionId
WHERE a.attendingStatus = 'MISSING' ORDER BY a.attendenceId ASC;

--14. OK Nombre d'absences justifiées et non justifiées pour chaque étudiant

SELECT
st.studentId,
st.lastName,
st.firstName,
a.justifiedStatus,
COUNT(CASE WHEN a.justifiedStatus = 'JUSTIFIED' THEN 1 END) AS justified,
COUNT(CASE WHEN a.justifiedStatus = 'NOT_JUSTIFIED' THEN 1 END) AS not_Justified
FROM attendance a
JOIN session s ON a.sessionId = s.sessionId
JOIN course c ON s.courseId = c.courseId
JOIN student st ON a.studentId = st.studentId
WHERE
s.sessionDate BETWEEN '2024-01-01 11:00:00' AND '2024-01-24 11:00:00'
AND a.attendingStatus = 'MISSING'
GROUP BY st.studentId,st.lastName,st.firstName,a.justifiedStatus;

--OK 15. ABS entre 2 dates
 SELECT
a.attendenceId,c.courseName,
st.studentId, st.lastName, st.firstName
FROM attendance a
JOIN session s ON a.sessionId = s.sessionId
JOIN course c ON s.courseId = c.courseId
JOIN student st ON a.studentId = st.studentId
WHERE s.sessionDate BETWEEN '2024-01-01 11:00:00' AND '2024-01-24 11:00:00'
AND a.attendingStatus = 'MISSING'
AND st.studentId = 1;


--'2024-01-01 11:00:00' '2024-01-24 11:00:00'


