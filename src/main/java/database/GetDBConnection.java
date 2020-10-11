package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GetDBConnection {
    public static Connection getConnection(String serverUrl, String serverPort, String db, String username, String password)
            throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://" + serverUrl + ":" + serverPort + "/" + db + "?serverTimezone=UTC", username, password);
    }
}
