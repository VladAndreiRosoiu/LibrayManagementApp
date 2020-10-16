package services;

import models.user.Client;
import models.user.Librarian;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface AuthService {

    int getUserId(Connection connection, String username) throws SQLException;

    String getPassword(String password);

    ResultSet getUser(Connection connection, int id, String password) throws SQLException;

    Client getClient(Connection connection, ResultSet resultSet) throws SQLException;

    Librarian getLibrarian(Connection connection, ResultSet resultSet) throws SQLException;
}
