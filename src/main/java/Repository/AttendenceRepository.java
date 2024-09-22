package Repository;

import Models.*;
import Exception.BadRequestException;
import Exception.NotFoundException;
import Exception.ServerException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AttendenceRepository implements GenericDAO<Attendance> {
    /* List all students with their attendence courses with session details by sessionID*/
    DataBaseConnect dataBaseConnect = new DataBaseConnect();

    /*ADD GROUP BY ACADEMIC YEAR to separate*/
    public List<Attendance> attendancesBySesId(int sessionId) {
        String query = "SELECT a.attendenceId,ses.sessionDate,s.studentId,\n" +
                "s.firstName,s.lastName,s.academicYear,a.attendingStatus,\n" +
                "a.justifiedStatus\n" +
                "FROM Attendance a\n" +
                "JOIN Student s ON a.studentId = s.studentId\n" +
                "JOIN Session ses ON a.sessionId = ses.sessionId\n" +
                "WHERE a.sessionId =? \n" +
                "GROUP BY s.academicYear, a.attendenceId, ses.sessionDate, s.studentId,\n" +
                "s.firstName, s.lastName, a.attendingStatus, a.justifiedStatus\n" +
                "ORDER BY s.academicYear;";

        List<Attendance> attendanceList = new ArrayList<>();
        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, sessionId);

            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    LocalDateTime sessionDate = result.getObject("sessionDate", LocalDateTime.class);
                    int studentId = result.getInt("studentId");
                    String studentFirstName = result.getString("firstName");
                    String studentLastName = result.getString("lastName");
                    String studentLevel = String.valueOf(AcademicYear.valueOf(result.getString("academicyear")));
                    //Student object
                    Student student = new Student();
                    student.setStudentId(studentId);
                    student.setAcademicYear(AcademicYear.valueOf(studentLevel));
                    student.setFirstName(studentFirstName);
                    student.setLastName(studentLastName);
                    //session object
                    Session session = new Session();
                    session.setSessionDate(sessionDate);

                    Attendance attendence = new Attendance();
                    attendence.setStudent(student);
                    attendence.setSession(session);
                    attendence.setAttendenceId(result.getInt("attendenceId"));
                    attendence.setAttendingStatus(AttendingStatus.valueOf(result.getString("attendingStatus")));
                    attendence.setJustifiedStatus(JustifiedStatus.valueOf(result.getString("justifiedStatus")));

                    attendanceList.add(attendence);
                }
            }
        } catch (SQLException e) {
            throw new ServerException("Error reading attendence by session", e);
        }

        return attendanceList;
    }

    @Override
    public void create(Attendance newAttendance) {
        if (newAttendance == null) {
            throw new BadRequestException("newAttendance cannot be null");
        }
        String query = "INSERT INTO attendance (attendenceId, attendingStatus, justifiedStatus, proof, sessionId, studentId) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, newAttendance.getAttendenceId());
            statement.setString(2, newAttendance.getAttendingStatus().name());
            statement.setString(3, newAttendance.getJustifiedStatus().name());
            statement.setString(4, newAttendance.getProof());
            statement.setInt(5, newAttendance.getSession().getSessionId());
            statement.setInt(6, newAttendance.getStudent().getStudentId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ServerException("Error creating newAttendance", e);
        }
    }

    @Override
    public List<Attendance> showAll() {
        List<Attendance> attendanceList = new ArrayList<>();
        String query = "SELECT * FROM attendance";

        try (Connection conn = dataBaseConnect.getConnection();
             Statement stm = conn.createStatement();
             ) {
            ResultSet result = stm.executeQuery(query);
            while (result.next()) {
                int sessionId = result.getInt("sessionId");
                int studentId = result.getInt("studentId");
                Session session = new Session();
                Student student = new Student();
                session.setSessionId(sessionId);
                student.setStudentId(studentId);
                Attendance attendanceAdd = new Attendance(
                        result.getInt("attendenceId"),
                        AttendingStatus.valueOf(result.getString("attendingStatus")),
                        JustifiedStatus.valueOf(result.getString("justifiedStatus")),
                        result.getString("proof"),
                        session,
                        student
                );
                attendanceList.add(attendanceAdd);
            }
        } catch (SQLException e) {
            throw new ServerException("Error reading attendance", e);
        }
        return attendanceList;
    }

    @Override
    public Attendance update(int attendenceId, Attendance attendanceUpdate) {
        String query = "UPDATE attendance SET attendingStatus=?, justifiedStatus=?, proof=?, sessionId=?, studentId=? WHERE attendenceId=?";
        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, attendanceUpdate.getAttendingStatus().name());
            statement.setString(2, attendanceUpdate.getJustifiedStatus().name());
            statement.setString(3, attendanceUpdate.getProof());
            statement.setInt(4, attendanceUpdate.getSession().getSessionId());
            statement.setInt(5, attendanceUpdate.getStudent().getStudentId());
            statement.setInt(6, attendanceUpdate.getAttendenceId());

            statement.executeUpdate();
            return attendanceUpdate;
        } catch (SQLException e) {
            throw new ServerException("Error updating attendance", e);
        }
    }

    @Override
    public void delete(int attendenceId) {
        String query = "DELETE FROM attendance WHERE attendenceId=?";
        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, attendenceId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ServerException("Error deleting attendance", e);
        }
    }

    @Override
    public Attendance read(int attendenceId) {
        Attendance attendanceRead = null;
        String query = "SELECT * FROM attendance WHERE attendenceId=?";

        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, attendenceId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                int sessionId = result.getInt("sessionId");
                int studentId = result.getInt("studentId");
                Session session = new Session();
                Student student = new Student();
                session.setSessionId(sessionId);
                student.setStudentId(studentId);
                attendanceRead = new Attendance(
                        result.getInt("attendenceId"),
                        AttendingStatus.valueOf(result.getString("attendingStatus")),
                        JustifiedStatus.valueOf(result.getString("justifiedStatus")),
                        result.getString("proof"),
                        session,
                        student
                );
            } else {
                throw new NotFoundException("cannot reading attendence by attendenceId " + attendenceId);
            }

        } catch (SQLException e) {
            throw new ServerException("Error reading attendance", e);
        }
        return attendanceRead;
    }

    public List<Attendance> getAttendanceByStudent(int studentId) {
        List<Attendance> attendanceByStudent = new ArrayList<>();

        String query = "SELECT s.firstName,\n" +
                "s.lastName,s.academicYear,\n" +
                "ses.sessionDate,\n" +
                "c.courseName,\n" +
                "a.attendingStatus,\n" +
                "a.justifiedStatus\n" +
                "FROM Attendance a\n" +
                "JOIN Student s ON a.studentId = s.studentId\n" +
                "JOIN Session ses ON a.sessionId = ses.sessionId\n" +
                "JOIN Course c ON ses.courseId = c.courseId\n" +
                "WHERE a.attendingStatus = 'MISSING' AND s.studentId =?;";

        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, studentId);
            ResultSet result = statement.executeQuery();
                if (result.next()) {
                    LocalDateTime sessionDate = result.getObject("sessionDate", LocalDateTime.class);
                    String firstName = result.getString("firstName");
                    String lastName = result.getString("lastName");
                    String courseName = result.getString("courseName");
                    String studentLevel = String.valueOf(AcademicYear.valueOf(result.getString("academicyear")));

                    Student student = new Student();
                    student.setFirstName(firstName);
                    student.setLastName(lastName);
                    student.setAcademicYear(AcademicYear.valueOf(studentLevel));

                    Course course = new Course();
                    course.setCourseName(courseName);

                    Session session = new Session();
                    session.setSessionDate(sessionDate);
                    session.setCourse(course);

                    Attendance attendence = new Attendance();
                    attendence.setStudent(student);
                    attendence.setSession(session);
                    attendence.setAttendingStatus(AttendingStatus.valueOf(result.getString("attendingStatus")));
                    attendence.setJustifiedStatus(JustifiedStatus.valueOf(result.getString("justifiedStatus")));

                    attendanceByStudent.add(attendence);
                }
        } catch (SQLException e) {
            throw new ServerException("Error to retrieve attendence by session", e);
        }
        return attendanceByStudent;

    }

    public List<Attendance> attendanceByStudentCourse(int studentId, int courseId) {
        List<Attendance> attendanceList = new ArrayList<>();

        String query = "SELECT s.firstName,\n" +
                "s.lastName,\n" +
                "ses.sessionDate,\n" +
                "c.courseName,\n" +
                "a.attendingStatus,\n" +
                "a.justifiedStatus\n" +
                "FROM Attendance a\n" +
                "JOIN Student s ON a.studentId = s.studentId\n" +
                "JOIN Session ses ON a.sessionId = ses.sessionId\n" +
                "JOIN Course c ON ses.courseId = c.courseId\n" +
                "WHERE a.attendingStatus = 'MISSING' AND s.studentId =? AND c.courseId=?;";

        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            ResultSet result = statement.executeQuery();
                while (result.next()) {
                    LocalDateTime dateSession = result.getObject("sessionDate", LocalDateTime.class);
                    String firstName = result.getString("firstName");
                    String lastName = result.getString("lastName");
                    String courseName = result.getString("courseName");

                    Student student = new Student();
                    student.setFirstName(firstName);
                    student.setLastName(lastName);

                    Course course = new Course();
                    course.setCourseName(courseName);

                    Session session = new Session();
                    session.setSessionDate(dateSession);
                    session.setCourse(course);

                    Attendance attendence = new Attendance();
                    attendence.setStudent(student);
                    attendence.setSession(session);
                    attendence.setAttendingStatus(AttendingStatus.valueOf(result.getString("attendingStatus")));
                    attendence.setJustifiedStatus(JustifiedStatus.valueOf(result.getString("justifiedStatus")));

                    attendanceList.add(attendence);
                }
        } catch (SQLException e) {
            throw new ServerException("Error to retrieve attendence by session and student ID", e);
        }
        return attendanceList;
    }
    public List<Attendance> absenceProof(){
        List<Attendance> absenceProofList = new ArrayList<>();
        String query ="SELECT a.attendenceId,s.studentId,\n" +
                "s.lastName,\n" +
                "s.firstName,\n" +
                "s.academicYear,\n" +
                "a.justifiedStatus,\n" +
                "a.proof,\n" +
                "se.sessionDate\n" +
                "FROM Attendance a\n" +
                "INNER JOIN Student s ON a.studentId = s.studentId\n" +
                "INNER JOIN Session se ON a.sessionId = se.sessionId\n" +
                "WHERE a.attendingStatus = 'MISSING' ORDER BY a.attendenceId ASC";

        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            ResultSet result = statement.executeQuery();
                while (result.next()){
                    LocalDateTime sessionDate = result.getObject("sessionDate", LocalDateTime.class);
                    int studentId = result.getInt("studentId");
                    String studentFirstName = result.getString("firstName");
                    String studentLastName = result.getString("lastName");
                    String studentLevel = String.valueOf(AcademicYear.valueOf(result.getString("academicyear")));
                    //Student object
                    Student student = new Student();
                    student.setStudentId(studentId);
                    student.setAcademicYear(AcademicYear.valueOf(studentLevel));
                    student.setFirstName(studentFirstName);
                    student.setLastName(studentLastName);
                    //session object
                    Session session = new Session();
                    session.setSessionDate(sessionDate);

                    Attendance attendence = new Attendance();
                    attendence.setStudent(student);
                    attendence.setSession(session);
                    attendence.setAttendenceId(result.getInt("attendenceId"));
                    attendence.setJustifiedStatus(JustifiedStatus.valueOf(result.getString("justifiedStatus")));

                    absenceProofList.add(attendence);
                }
        } catch (SQLException e) {
            throw new ServerException("Error to retrieve absenceProof for missing student", e);
        }
        return absenceProofList;
    }

    public List<Attendance> getAbsencesBetween(LocalDateTime startDateTime, LocalDateTime endDateTime){
        String query = "SELECT a.attendenceId,a.justifiedStatus,\n" +
                "s.sessionId, s.sessionDate, c.courseId, c.courseName,\n" +
                " st.studentId, st.lastName,st.firstName\n" +
                " FROM attendance a\n" +
                " JOIN session s ON a.sessionId = s.sessionId\n" +
                " JOIN course c ON s.courseId = c.courseId\n" +
                " JOIN student st ON a.studentId = st.studentId\n" +
                " WHERE s.sessionDate BETWEEN ? AND ?\n" +
                " AND a.attendingStatus = 'MISSING';";

        List<Attendance> absencesBetweenDates = new ArrayList<>();
        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setTimestamp(1, Timestamp.valueOf(startDateTime));
            statement.setTimestamp(2, Timestamp.valueOf(endDateTime));

            try ( ResultSet result = statement.executeQuery();
            ) {
                while (result.next()) {
                    Session session = new Session();
                    session.setSessionId(result.getInt("sessionId"));
                    session.setSessionDate(result.getTimestamp("sessionDate").toLocalDateTime());

                    Course course = new Course();
                    course.setCourseId(result.getInt("courseId"));
                    course.setCourseName(result.getString("courseName"));
                    session.setCourse(course);

                    Student student = new Student();
                    student.setStudentId(result.getInt("studentId"));
                    student.setLastName(result.getString("lastName"));
                    student.setFirstName(result.getString("firstName"));

                    Attendance attendance = new Attendance();
                    attendance.setAttendenceId(result.getInt("attendenceId"));
                    attendance.setJustifiedStatus(JustifiedStatus.valueOf(result.getString("justifiedStatus")));
                    attendance.setSession(session);
                    attendance.setStudent(student);
                    absencesBetweenDates.add(attendance);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return absencesBetweenDates;
    }
}