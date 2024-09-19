package Repository;

import Models.Course;
import Models.Teacher;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CourseRepository {
    /* List all courses  with their teachers */
    DataBaseConnect dataBaseConnect = new DataBaseConnect();

    public List<Course> courseWithTeacher() {
        String query = "SELECT c.courseId, c.courseName, t.firstName,t.lastName,t.email\n" +
                "FROM Course c\n" +
                "JOIN Teacher t ON c.teacherId = t.teacherId;";
        List<Course> courseList = new ArrayList<>();

        try(Connection conn = dataBaseConnect.getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet result = statement.executeQuery()
        ){
            while (result.next()) {
                String teacherFirstName = result.getString("firstName");
                String teacherLastName = result.getString("lastName");
                String teacherEmail = result.getString("email");

                // teacher object
                Teacher teacher = new Teacher();
                teacher.setFirstName(teacherFirstName);
                teacher.setLastName(teacherLastName);
                teacher.setEmail(teacherEmail);

                // course object
                Course course = new Course(result.getInt("courseId"),result.getString("courseName"),teacher);
                courseList.add(course);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return courseList;
    }

    public List<Course> courseWithTeacherById(int teacherId) {
        String query = "SELECT c.courseId, c.courseName, t.firstName,t.lastName,t.email\n" +
                "FROM Course c\n" +
                "JOIN Teacher t ON c.teacherId = t.teacherId\n" +
                "WHERE t.teacherId = ?";
        List<Course> courseList = new ArrayList<>();
        try(Connection conn = dataBaseConnect.getConnection();
            PreparedStatement statement = conn.prepareStatement(query)){

            statement.setInt(1, teacherId);

            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    String teacherFirstName = result.getString("firstName");
                    String teacherLastName = result.getString("lastName");
                    String teacherEmail = result.getString("email");

                    // teacher object
                    Teacher teacher = new Teacher();
                    teacher.setFirstName(teacherFirstName);
                    teacher.setLastName(teacherLastName);
                    teacher.setEmail(teacherEmail);

                    // course object
                    Course course = new Course(result.getInt("courseId"), result.getString("courseName"), teacher);
                    courseList.add(course);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return courseList;
    }
}
