import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides JDBC connections to the MySQL database.
 * Credentials can be overridden with environment variables
 * DB_URL, DB_USER and DB_PASSWORD.
 */
public class DBConnection {

    private static final String URL = System.getenv("DB_URL") != null
            ? System.getenv("DB_URL")
            : "jdbc:mysql://localhost:3306/student_db";
    private static final String USER = System.getenv("DB_USER") != null
            ? System.getenv("DB_USER")
            : "root";
    private static final String PASSWORD = System.getenv("DB_PASSWORD") != null
            ? System.getenv("DB_PASSWORD")
            : "root";

    private DBConnection() {
        // utility class
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
