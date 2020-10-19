package dao;

import models.user.Client;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public interface ClientDao extends EntityDao<Client> {
    List<Client> findByFirstName(Connection connection, String firstName) throws SQLException;

    List<Client> findByLastName(Connection connection, String lastName) throws SQLException;

    Client findByUsername(Connection connection, String username) throws SQLException;

    Client findByEmail(Connection connection, String email) throws SQLException;

    boolean removeByUsername(Connection connection, String username) throws SQLException;
}
