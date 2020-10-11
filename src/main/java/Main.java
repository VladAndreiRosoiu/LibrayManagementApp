import database.GetCredentials;
import database.GetDBConnection;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class Main {

    private static final File CONN_FILE = new File(Objects.requireNonNull(Main.class.getClassLoader().getResource("db.json")).getFile());


    public static void main(String[] args) {

        try {

            Connection connection = GetDBConnection.getConnection(GetCredentials.readCredentials(CONN_FILE).get("serverURL"),
                    GetCredentials.readCredentials(CONN_FILE).get("serverPort"),
                    GetCredentials.readCredentials(CONN_FILE).get("database"),
                    GetCredentials.readCredentials(CONN_FILE).get("user"),
                    GetCredentials.readCredentials(CONN_FILE).get("password"));
        } catch (IOException | ParseException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
