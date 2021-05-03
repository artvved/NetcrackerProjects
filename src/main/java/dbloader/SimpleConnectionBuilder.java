package dbloader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Simple connection pool
 */
public class SimpleConnectionBuilder implements ConnectionBuilder {
    @Override
    public Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/netcrackerprojects";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","admin");
        return DriverManager.getConnection(url, props);
    }
}
