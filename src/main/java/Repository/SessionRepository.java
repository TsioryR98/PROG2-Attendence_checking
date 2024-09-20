package Repository;

import Models.Course;
import Models.Session;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SessionRepository implements GenericDAO<Session>{
    DataBaseConnect dataBaseConnect = new DataBaseConnect();

    @Override
    public void create(Session newSession) {
        String query = "INSERT INTO session (sessionId, sessionDate, courseId) VALUES (?, ?, ?)";
        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, newSession.getSessionId());
            statement.setObject(2, newSession.getSessionDate());
            statement.setInt(3, newSession.getCourse().getCourseId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating newSession", e);
        }
    }

    @Override
    public List<Session> showAll() {
        List<Session> sessionList = new ArrayList<>();
        String query = "SELECT * FROM session";

        try (Connection conn = dataBaseConnect.getConnection();
             Statement stm = conn.createStatement();
             ResultSet result = stm.executeQuery(query)) {

            while (result.next()) {
                int courseId = result.getInt("courseId");
                // course object
                Course course = new Course();
                course.setCourseId(courseId);
                Session sessionAdd = new Session(
                        result.getInt("sessionId"),
                        result.getObject("sessionDate", LocalDateTime.class),
                        course
                );
                sessionList.add(sessionAdd);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error reading sessions", e);
        }
        return sessionList;
    }

    @Override
    public Session update(int sessionId, Session sessionUpdate) {
        String query = "UPDATE session SET sessionDate=?, courseId=? WHERE sessionId=?";
        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setObject(1, (sessionUpdate.getSessionDate()));
            statement.setInt(2, sessionUpdate.getCourse().getCourseId());
            statement.setInt(3, sessionUpdate.getSessionId());

            statement.executeUpdate();
            return sessionUpdate;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating session", e);
        }
    }

    @Override
    public void delete(int sessionId) {
        String query = "DELETE FROM session WHERE sessionId=?";
        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, sessionId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting session", e);
        }
    }

    @Override
    public Session read(int sessionId) {
        Session sessionRead = null;
        String query = "SELECT * FROM session WHERE sessionId=?";

        try (Connection conn = dataBaseConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, sessionId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                int courseId = result.getInt("courseId");
                // course object
                Course course = new Course();
                course.setCourseId(courseId);
                sessionRead = new Session(
                        result.getInt("sessionId"),
                        result.getObject("sessionDate", LocalDateTime.class),
                        course
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error reading session", e);
        }
        return sessionRead;
    }
}
