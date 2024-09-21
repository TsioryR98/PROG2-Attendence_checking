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
             ResultSet result = stm.executeQuery(query)) {

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
                "s.lastName,\n" +
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
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    LocalDateTime sessionDate = result.getObject("sessionDate", LocalDateTime.class);
                    String firstName = result.getString("firstName");
                    String lastName = result.getString("lastName");
                    String courseName = result.getString("courseName");

                    Student student = new Student();
                    student.setFirstName(firstName);
                    student.setLastName(lastName);

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
                } else {
                    throw new NotFoundException("Cannot find attendence with studentId " + studentId);
                }
            }
        } catch (SQLException e) {
            throw new ServerException("Error to retrieve attendence by session", e);
        }
        return attendanceByStudent;

    }

}