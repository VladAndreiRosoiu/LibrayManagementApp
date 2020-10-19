import database.GetDBConnection;
import models.Library;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            Connection connection = GetDBConnection.getConnection();
            Library library = new Library(connection);
            library.initLibrary();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
