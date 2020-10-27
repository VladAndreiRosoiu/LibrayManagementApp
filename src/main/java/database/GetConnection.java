package database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class GetConnection {

    private final File PROP_FILE = new File(Objects.requireNonNull(GetConnection.class.getClassLoader().getResource("database.properties")).getFile());

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" +
                            GetProperties.getDBProperties(PROP_FILE).getProperty("serverURL") + ":" +
                            GetProperties.getDBProperties(PROP_FILE).getProperty("serverPort") + "/" +
                            GetProperties.getDBProperties(PROP_FILE).getProperty("database") + "?serverTimezone=UTC",
                    GetProperties.getDBProperties(PROP_FILE).getProperty("user"),
                    GetProperties.getDBProperties(PROP_FILE).getProperty("password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
