import database.GetDBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            Connection connection = GetDBConnection.getConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
