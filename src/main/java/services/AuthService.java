package services;

import models.user.User;

public interface AuthService {

    int getUserId(String username);

    String getPassword(String password);

    User getUser(int id, String password);

}
