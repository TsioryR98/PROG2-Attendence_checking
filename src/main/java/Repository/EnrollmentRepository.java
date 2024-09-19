package Repository;

import Models.Course;
import Models.Enrollment;
import Models.Session;
import Models.Student;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class EnrollmentRepository implements GenericDAO<Enrollment>{
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

    @Override
    public void create(Enrollment newEnrollment) {
        String query = "INSERT INTO enrollment (idEnrollment, studentId, courseId) VALUES (?, ?, ?)";
        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, newEnrollment.getIdEnrollment());
            statement.setInt(2, newEnrollment.getStudent().getStudentId());
            statement.setInt(3, newEnrollment.getCourse().getCourseId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating new enrollment", e);
        }
    }

    @Override
    public List<Enrollment> showAll() {
        List<Enrollment> enrollmentList = new ArrayList<>();
        String query = "SELECT * FROM enrollment";

        try (Connection conn = dataBaseConnect.getConnection();
             Statement stm = conn.createStatement();
             ResultSet result = stm.executeQuery(query)) {

            while (result.next()) {
                int studentId = result.getInt("studentId");
                int courseId = result.getInt("courseId");
                Student student = new Student();
                Course course = new Course();
                student.setStudentId(studentId);
                course.setCourseId(courseId);
                Enrollment enrollmentAdd = new Enrollment(
                        result.getInt("idEnrollment"),
                        student,
                        course
                );
                enrollmentList.add(enrollmentAdd);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error reading enrollments", e);
        }
        return enrollmentList;
    }

    @Override
    public Enrollment update(int idEnrollment, Enrollment enrollmentUpdate) {
        String query = "UPDATE enrollment SET studentId=?, courseId=? WHERE idEnrollment=?";
        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, enrollmentUpdate.getStudent().getStudentId());
            statement.setInt(2, enrollmentUpdate.getCourse().getCourseId());
            statement.setInt(3, enrollmentUpdate.getIdEnrollment());

            statement.executeUpdate();
            return enrollmentUpdate;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating enrollment", e);
        }
    }

    @Override
    public void delete(int idEnrollment) {
        String query = "DELETE FROM enrollment WHERE idEnrollment=?";
        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, idEnrollment);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting enrollment", e);
        }
    }

    @Override
    public Enrollment read(int idEnrollment) {
        Enrollment enrollmentRead = null;
        String query = "SELECT * FROM enrollment WHERE idEnrollment=?";

        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, idEnrollment);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                int studentId = result.getInt("studentId");
                int courseId = result.getInt("courseId");
                Student student = new Student();
                Course course = new Course();
                student.setStudentId(studentId);
                course.setCourseId(courseId);
                enrollmentRead = new Enrollment(
                        result.getInt("idEnrollment"),
                        student,
                        course
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error reading enrollment", e);
        }
        return enrollmentRead;
    }
}
