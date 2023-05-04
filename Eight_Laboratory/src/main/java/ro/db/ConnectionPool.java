package ro.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * A Singleton class that manages
 * a Hikari database connection pool.
 *
 * @author Alex Neagu
 */
@Log4j2
public class ConnectionPool {
    private static final String URL = "jdbc:postgresql://localhost:5432/java_lab8";
    private static final String USER = "postgres";
    private static final String PASSWORD = "student";
    private static ConnectionPool instance = null;
    private final HikariDataSource ds;

    private ConnectionPool() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(20);
        config.setAutoCommit(false);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        ds = new HikariDataSource(config);
    }

    /**
     * @return A database connection extracted from the connection pool
     */
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public void closePool() {
        this.ds.close();
    }

    /**
     * Restricted to a single instantiation of the <tt>ConnectionPool</tt> class
     */
    public static ConnectionPool getInstance() {
        if (instance == null || instance.ds.isClosed()) {
            instance = new ConnectionPool();
        }
        return instance;
    }
}
