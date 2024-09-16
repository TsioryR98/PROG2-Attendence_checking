package Repository;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DataBaseConnect {
    @Bean
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/todolist", "postgres", "tsiory98");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}

