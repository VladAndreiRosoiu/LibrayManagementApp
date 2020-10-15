package services;

import models.user.User;

import java.sql.Connection;

public class AuthServiceImpl implements AuthService {

    @Override
    public int getUserId(Connection connection, String username) {
        return 0;
    }

    @Override
    public String getPassword(String password) {
        return null;
    }

    @Override
    public User getUser(Connection connection, int id, String password) {
        return null;
    }
}

