package database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class GetDBConnection {

    private static final File CONN_FILE = new File(Objects.requireNonNull(GetDBConnection.class.getClassLoader().getResource("database.properties")).getFile());

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://" +
                        GetProperties.getDBProperties(CONN_FILE).getProperty("serverURL") + ":" +
                        GetProperties.getDBProperties(CONN_FILE).getProperty("serverPort") + "/" +
                        GetProperties.getDBProperties(CONN_FILE).getProperty("database") + "?serverTimezone=UTC",
                GetProperties.getDBProperties(CONN_FILE).getProperty("user"),
                GetProperties.getDBProperties(CONN_FILE).getProperty("password"));
    }
}
