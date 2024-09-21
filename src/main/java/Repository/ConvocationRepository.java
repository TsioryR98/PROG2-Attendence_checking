package Repository;

import Models.Convocation;
import Models.Student;
import Models.Teacher;
import Exception.BadRequestException;
import Exception.NotFoundException;
import Exception.ServerException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ConvocationRepository implements GenericDAO<Convocation>{
    DataBaseConnect dataBaseConnect = new DataBaseConnect();
    @Override
    public void create(Convocation newConvocation) {
        if (newConvocation == null) {
            throw new BadRequestException("newConvocation cannot be null");
        }
        String query = "INSERT INTO convocation (convocationId, teacherId, studentId, convocationDate, reason) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, newConvocation.getConvocationId());
            statement.setInt(2, newConvocation.getTeacher().getTeacherId());
            statement.setInt(3, newConvocation.getStudent().getStudentId());
            statement.setObject(4,(newConvocation.getConvocationDate()));
            statement.setString(5, newConvocation.getReason());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ServerException("Error creating new convocation", e);
        }
    }
    @Override
    public List<Convocation> showAll() {
        List<Convocation> convocationList = new ArrayList<>();
        String query = "SELECT * FROM convocation";

        try (Connection conn = dataBaseConnect.getConnection();
             Statement stm = conn.createStatement();
             ResultSet result = stm.executeQuery(query)) {

            while (result.next()) {
                Teacher teacher = new Teacher();
                Student student = new Student();
                int teacherId = result.getInt("teacherId");
                int studentId = result.getInt("studentId");
                student.setStudentId(studentId);
                teacher.setTeacherId(teacherId);
                Convocation convocationAdd = new Convocation(
                        result.getInt("convocationId"),
                        teacher,
                        student,
                        result.getObject("convocationDate", LocalDateTime.class),
                        result.getString("reason")
                );
                convocationList.add(convocationAdd);
            }
        } catch (SQLException e) {
            throw new ServerException("Error reading convocations", e);
        }
        return convocationList;
    }

    @Override
    public Convocation update(int convocationId, Convocation convocationUpdate) {
        String query = "UPDATE convocation SET teacherId=?, studentId=?, convocationDate=?, reason=? WHERE convocationId=?";
        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, convocationUpdate.getTeacher().getTeacherId());
            statement.setInt(2, convocationUpdate.getStudent().getStudentId());
            statement.setObject(3,(convocationUpdate.getConvocationDate()));
            statement.setString(4, convocationUpdate.getReason());
            statement.setInt(5, convocationId);

            statement.executeUpdate();
            return convocationUpdate;
        } catch (SQLException e) {
            throw new ServerException("Error updating convocation", e);
        }    }

    @Override
    public void delete(int convocationId) {
        String query = "DELETE FROM convocation WHERE convocationId=?";
        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, convocationId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ServerException("Error deleting convocation", e);
        }
    }

    @Override
    public Convocation read(int convocationId) {
        Convocation convocationRead = null;
        String query = "SELECT * FROM convocation WHERE convocationId=?";

        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, convocationId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Teacher teacher = new Teacher();
                Student student = new Student();
                int teacherId = result.getInt("teacherId");
                int studentId = result.getInt("studentId");
                student.setStudentId(studentId);
                teacher.setTeacherId(teacherId);
                convocationRead = new Convocation(
                        result.getInt("convocationId"),
                        teacher,
                        student,
                        result.getObject("convocationDate", LocalDateTime.class),
                        result.getString("reason")
                );
            }
            else{
                throw new NotFoundException("cannot retrieve convocation by convocationId "+convocationId);
            }

        } catch (SQLException e) {
            throw new ServerException("Error reading convocation", e);
        }
        return convocationRead;    }
}
