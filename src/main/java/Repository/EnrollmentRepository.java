package Repository;

import Models.Course;
import Models.Enrollment;
import Models.Student;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class EnrollmentRepository {
    /* List all students with their enrolled courses */
    DataBaseConnect dataBaseConnect = new DataBaseConnect();

    public List<Enrollment> studentWithCourse() {
        String query = "SELECT e.idEnrollment,s.studentId, s.lastName, s.firstName, s.studentEmail, c.courseId, c.courseName " +
                "FROM Enrollment e " +
                "JOIN Student s ON e.studentId = s.studentId " +
                "JOIN Course c ON e.courseId = c.courseId";
        List<Enrollment> enrollmentList = new ArrayList<>();

        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                int studentId = result.getInt("studentId");
                String studentLastName = result.getString("lastName");
                String studentFirstName = result.getString("firstName");
                String studentEmail = result.getString("studentEmail");
                int courseId = result.getInt("courseId");
                String enrolledCourseName = result.getString("courseName");
                // Student object
                Student student = new Student();
                student.setStudentId(studentId);
                student.setLastName(studentLastName);
                student.setFirstName(studentFirstName);
                student.setStudentEmail(studentEmail);
                // Course object
                Course course = new Course();
                course.setCourseId(courseId);
                course.setCourseName(enrolledCourseName);
                // Enrollment object
                Enrollment enrollment = new Enrollment(result.getInt("idEnrollment"),student,course);
                enrollmentList.add(enrollment);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error reading students with courses", e);
        }
        return enrollmentList;
    }

    /* List all students with their enrolled courses by courseId */
    public List<Enrollment> studentWithCourseById(int courseId) {
        String query = "SELECT e.idEnrollment, s.studentId, s.lastName, s.firstName, s.studentEmail, c.courseId, c.courseName " +
                "FROM Enrollment e " +
                "JOIN Student s ON e.studentId = s.studentId " +
                "JOIN Course c ON e.courseId = c.courseId " +
                "WHERE e.courseId = ?";

        List<Enrollment> enrollmentList = new ArrayList<>();
        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, courseId);

            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    int studentId = result.getInt("studentId");
                    String studentLastName = result.getString("lastName");
                    String studentFirstName = result.getString("firstName");
                    String studentEmail = result.getString("studentEmail");
                    int enrolledCourseId = result.getInt("courseId");
                    String enrolledCourseName = result.getString("courseName");
                    // Student object
                    Student student = new Student();
                    student.setStudentId(studentId);
                    student.setLastName(studentLastName);
                    student.setFirstName(studentFirstName);
                    student.setStudentEmail(studentEmail);
                    // Course object
                    Course course = new Course();
                    course.setCourseId(enrolledCourseId);
                    course.setCourseName(enrolledCourseName);

                    Enrollment enrollment = new Enrollment(result.getInt("idEnrollment"), student, course);
                    enrollmentList.add(enrollment);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error reading students with courses", e);
        }
        return enrollmentList;
    }
}
