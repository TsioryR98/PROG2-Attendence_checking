package Repository;

import Models.Teacher;
import Exception.BadRequestException;
import Exception.NotFoundException;
import Exception.ServerException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TeacherRepository implements GenericDAO<Teacher> {

   DataBaseConnect dataBaseConnect = new DataBaseConnect();
    @Override
    public void create(Teacher newTeacher) {
        if (newTeacher == null) {
            throw new BadRequestException("New teacher cannot be null for this request");
        }
        String query = "INSERT INTO Teacher (teacherId, lastName, firstName, email, phoneNumber) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dataBaseConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, newTeacher.getTeacherId());
            statement.setString(2, newTeacher.getLastName());
            statement.setString(3, newTeacher.getFirstName());
            statement.setString(4, newTeacher.getEmail());
            statement.setString(5, newTeacher.getPhoneNumber());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ServerException("error occurred during creating Teacher");
        }
    }

    @Override
    public List<Teacher> showAll() {
        String query = "SELECT * FROM Teacher";
        List<Teacher> teachers = new ArrayList<>();
        try (Connection connection = dataBaseConnect.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Teacher teacher = new Teacher(
                        resultSet.getInt("teacherId"),
                        resultSet.getString("lastName"),
                        resultSet.getString("firstName"),
                        resultSet.getString("email"),
                        resultSet.getString("phoneNumber")
                );
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            throw new ServerException("error occurred during showing all Teachers");
        }
        return teachers;
    }

    @Override
    public Teacher update(int id, Teacher updatedTeacher) {
        String query = "UPDATE Teacher SET lastName = ?, firstName = ?, email = ?, phoneNumber = ? WHERE teacherId = ?";
        try (Connection connection = dataBaseConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, updatedTeacher.getLastName());
            statement.setString(2, updatedTeacher.getFirstName());
            statement.setString(3, updatedTeacher.getEmail());
            statement.setString(4, updatedTeacher.getPhoneNumber());
            statement.setInt(5, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ServerException("error occurred during updating Teacher");
        }
        return updatedTeacher;
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM Teacher WHERE teacherId = ?";
        try (Connection connection = dataBaseConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ServerException("error occurred during deleting Teacher");
        }
    }

    @Override
    public Teacher read(int id) {
        String query = "SELECT * FROM Teacher WHERE teacherId = ?";
        Teacher teacher = null;
        try (Connection connection = dataBaseConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    teacher = new Teacher(
                            resultSet.getInt("teacherId"),
                            resultSet.getString("lastName"),
                            resultSet.getString("firstName"),
                            resultSet.getString("email"),
                            resultSet.getString("phoneNumber")
                    );
                }
                else {
                    throw new NotFoundException("Not found teacher with id "+ id);
                }
            }
        } catch (SQLException e) {
            throw new ServerException("error occurred during reading Teacher");
        }
        return teacher;
    }
}
