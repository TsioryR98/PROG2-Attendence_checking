package Repository;

import Models.AcademicYear;
import Models.Student;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository

public class StudentRepository implements GenericDAO <Student> {
    DataBaseConnect dataBaseConnect = new DataBaseConnect();

    @Override
    public void create(Student newStudent) {
        String query = "INSERT INTO student (studentId, lastName, firstName, dateOfBirth, studentEmail, phoneNumber, enrollmentDate, academicYear) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, newStudent.getStudentId());
            statement.setString(2, newStudent.getLastName());
            statement.setString(3, newStudent.getFirstName());
            statement.setObject(4, newStudent.getDateOfBirth());
            statement.setString(5, newStudent.getStudentEmail());
            statement.setString(6, newStudent.getPhoneNumber());
            statement.setObject(7, newStudent.getEnrollmentDate());
            statement.setString(8, newStudent.getAcademicYear().name());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating newStudent", e);
        }
    }

    @Override
    public List<Student> showAll() {
        List<Student> studentList = new ArrayList<>();

        try (Statement stm = dataBaseConnect.getConnection().createStatement()) {
            String query = "SELECT * FROM student";
            ResultSet result = stm.executeQuery(query);

            while (result.next()) {
                Student studentAdd = new Student(
                        result.getInt("studentId"),
                        result.getString("lastName"),
                        result.getString("firstName"),
                        result.getObject("dateOfBirth", LocalDateTime.class),
                        result.getString("studentEmail"),
                        result.getString("phoneNumber"),
                        result.getObject("enrollmentDate", LocalDateTime.class),
                        AcademicYear.valueOf(result.getString("academicYear"))
                );
                studentList.add(studentAdd);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error reading student", e);
        }
        return studentList;
    }


    @Override
    public Student update(int studentId, Student studentUpdate) {
        String query = "UPDATE student SET studentEmail=?, phoneNumber=?, enrollmentDate=?, academicYear=? WHERE studentId=?";
        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, studentUpdate.getStudentEmail());
            statement.setString(2, studentUpdate.getPhoneNumber());
            statement.setObject(3, Timestamp.valueOf(studentUpdate.getEnrollmentDate()));
            statement.setString(4, studentUpdate.getAcademicYear().name());
            statement.setInt(5, studentId);

            statement.executeUpdate();
            return studentUpdate;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating student", e);
        }
    }

    @Override
    public void delete(int studentId) {
        String query = "DELETE FROM student WHERE studentId=?";
        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, studentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting student", e);
        }
    }

    @Override
    public Student read(int studentId) {
        Student studentRead = null;
        String query = "SELECT * FROM student WHERE studentId=?";

        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, studentId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                studentRead = new Student(
                        result.getInt("studentId"),
                        result.getString("lastName"),
                        result.getString("firstName"),
                        result.getObject("dateOfBirth", LocalDateTime.class),
                        result.getString("studentEmail"),
                        result.getString("phoneNumber"),
                        result.getObject("enrollmentDate", LocalDateTime.class),
                        AcademicYear.valueOf(result.getString("academicYear"))
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error reading Student", e);
        }
        return studentRead;
    }
}
