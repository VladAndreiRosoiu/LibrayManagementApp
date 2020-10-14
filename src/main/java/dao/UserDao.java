package dao;

import models.user.User;

public interface UserDao extends EntityDao <User>{
    User findByFirstName (String firstName);
    User findByLastName (String lastName);
    User findByUsername (String username);
    User findByEmail (String email);
    boolean removeByUsername(String username);
}
