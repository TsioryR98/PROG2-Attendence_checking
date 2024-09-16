package Repository;

import Models.AcademicYear;
import Models.Student;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Repository
public class StudentRepository implements StudentDAO{
    DataBaseConnect dataBaseConnect = new DataBaseConnect();

    @Override
    public void create(Student newStudent) {
        String query = "INSERT INTO student (id_student, last_name, first_name, date_of_birth, email, phone_number, enrollment_date, academic_year ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
                        result.getInt("id"),
                        result.getString("last_name"),
                        result.getString("first_name"),
                        result.getObject("date_of_birth", LocalDateTime.class),
                        result.getString("email"),
                        result.getString("phone_number"),
                        result.getObject("enrollment_date", LocalDateTime.class),
                        AcademicYear.valueOf(result.getString("academic_year"))
                );
                studentList.add(studentAdd);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error reading one student", e);
        }
        return studentList;
    }

    @Override
    public Student updateById(int id, Student studentUpdate) {
        String query =  "UPDATE student SET  email=?, phone_number=?, enrollment_date=?, academic_year=? WHERE id_student=?";
        try(Connection conn = dataBaseConnect.getConnection();
            PreparedStatement statement = conn.prepareStatement(query)){
            statement.setString(1, studentUpdate.getStudentEmail());
            statement.setString(2, studentUpdate.getPhoneNumber());
            statement.setObject(3, Timestamp.valueOf(studentUpdate.getEnrollmentDate()));
            statement.setString(4, String.valueOf(studentUpdate.getAcademicYear())) ;
            statement.setInt(5, studentUpdate.getStudentId());

            statement.executeUpdate();
            return studentUpdate;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating student", e);
        }
    }

    @Override
    public void deleteById(int idStudent) {
        String query = "DELETE FROM student WHERE id=?";
        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, idStudent);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting student", e);
        }

    }
}
