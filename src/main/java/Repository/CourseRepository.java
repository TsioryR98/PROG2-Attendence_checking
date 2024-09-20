package Repository;

import Models.Course;
import Models.Teacher;
import Models.exception.NotFoundException;
import Models.exception.ServerException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CourseRepository implements GenericDAO<Course>{
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
            throw new ServerException("Error finding course with associated teacher",e);
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
            throw new ServerException("Error finding course with associated teacher",e);
        }

        return courseList;
    }

    @Override
    public void create(Course newCourse) {
        String query = "INSERT INTO course (courseId, courseName, teacherId) VALUES (?, ?, ?)";
        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, newCourse.getCourseId());
            statement.setString(2, newCourse.getCourseName());
            statement.setInt(3, newCourse.getTeacher().getTeacherId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ServerException("Error creating newCourse", e);
        }
    }

    @Override
    public List<Course> showAll() {
        List<Course> courseList = new ArrayList<>();

        String query = "SELECT * FROM course";
        try (Connection conn = dataBaseConnect.getConnection();
             Statement stm = conn.createStatement();
             ResultSet result = stm.executeQuery(query)) {

            while (result.next()) {
                int teacherId = result.getInt("teacherId");
                Teacher teacher = new Teacher();
                teacher.setTeacherId(teacherId);
                Course courseAdd = new Course(
                        result.getInt("courseId"),
                        result.getString("courseName"),
                        teacher
                );
                courseList.add(courseAdd);
            }
        } catch (SQLException e) {
            throw new ServerException("Error reading courses", e);
        }
        return courseList;
    }

    @Override
    public Course update(int courseId, Course courseUpdate) {
        String query = "UPDATE course SET courseName=?, teacherId=? WHERE courseId=?";
        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, courseUpdate.getCourseName());
            statement.setInt(2, courseUpdate.getTeacher().getTeacherId());
            statement.setInt(3, courseUpdate.getCourseId());

            statement.executeUpdate();
            return courseUpdate;
        } catch (SQLException e) {
            throw new ServerException("Error updating course", e);
        }
    }

    @Override
    public void delete(int courseId) {
        String query = "DELETE FROM course WHERE courseId=?";
        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, courseId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ServerException("Error deleting course", e);
        }
    }

    @Override
    public Course read(int courseId) {
        Course courseRead = null;
        String query = "SELECT * FROM course WHERE courseId=?;";

        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
             statement.setInt(1, courseId);
             ResultSet result = statement.executeQuery();
             if (result.next()) {
                int teacherId = result.getInt("teacherId");
                Teacher teacher = new Teacher();
                teacher.setTeacherId(teacherId);
                courseRead = new Course(
                        result.getInt("courseId"),
                        result.getString("courseName"),
                        teacher
                );
             }
             else{
                 throw new NotFoundException("cannot retrieve course by courseId "+courseId);
             }
        } catch (SQLException e) {
            throw new ServerException("Error reading course", e);
        }
        return courseRead;
    }
}

