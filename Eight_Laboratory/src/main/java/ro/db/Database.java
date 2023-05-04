package ro.db;

import lombok.extern.log4j.Log4j2;
import ro.exceptions.ConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A Singleton class that manages
 * a connection to the database.
 *
 * @author Alex Neagu
 */
@Log4j2
public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/java_lab8";
    private static final String USER = "postgres";
    private static final String PASSWORD = "student";
    private static Database instance = null;
    private Connection connection = null;

    private Database() {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            this.connection.setAutoCommit(false);
        } catch (SQLException | ClassNotFoundException e) {
            log.error(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() throws SQLException {
        this.connection.close();
    }

    /**
     * Restricted to a single instantiation of the <tt>Database</tt> class
     */
    public static Database getInstance() throws ConnectionException {
        try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new Database();
            }
            return instance;
        } catch (SQLException e) {
            throw new ConnectionException("Error when getting database connection");
        }
    }
}
