package services;

import models.user.User;

import java.sql.Connection;

public interface AuthService {

    int getUserId(Connection connection, String username);

    String getPassword(String password);

    User getUser(Connection connection, int id, String password);
}
