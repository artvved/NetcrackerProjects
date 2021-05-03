package dbloader;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * interface to get connection
 *
 */
public interface ConnectionBuilder {
    /**
     * Returns db connection
     * @return db connection
     * @throws SQLException when connection not received
     */
    Connection getConnection() throws SQLException;
}
