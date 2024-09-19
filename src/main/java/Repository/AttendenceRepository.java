package Repository;

import Models.*;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AttendenceRepository {
    /* List all students with their attendence courses with session details by sessionID*/
    DataBaseConnect dataBaseConnect = new DataBaseConnect();

    /*ADD GROUP BY ACADEMIC YEAR to separate*/
    public List<Attendance> attendancesBySesId(int sessionId) {
        String query ="SELECT a.attendenceId,ses.sessionDate,s.studentId,\n" +
                "s.firstName,s.lastName,s.academicYear,a.attendingStatus,\n" +
                "a.justifiedStatus\n" +
                "FROM Attendance a\n" +
                "JOIN Student s ON a.studentId = s.studentId\n" +
                "JOIN Session ses ON a.sessionId = ses.sessionId\n" +
                "WHERE a.sessionId =? \n" +
                "GROUP BY s.academicYear, a.attendenceId, ses.sessionDate, s.studentId,\n" +
                "s.firstName, s.lastName, a.attendingStatus, a.justifiedStatus\n" +
                "ORDER BY s.studentId ASC;";

        List<Attendance> attendanceList = new ArrayList<>();
        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setInt(1, sessionId);

            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    LocalDateTime sessionDate = result.getObject("sessionDate",LocalDateTime.class);
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
            throw new RuntimeException("Error reading attendence by session", e);
        }

        return attendanceList;
    }
}
