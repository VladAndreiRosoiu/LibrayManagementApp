package services;

import models.user.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface AuthService {

    int getUserId(Connection connection, String username) throws SQLException;

    String getPassword(String password);

    String getUserDetails(Connection connection, int id, String password) throws SQLException;
}
