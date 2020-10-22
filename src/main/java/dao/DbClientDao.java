package dao;

import models.book.BorrowedBook;
import models.user.Client;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbClientDao implements ClientDao{
    @Override
    public List<Client> findByFirstName(Connection connection, String firstName) throws SQLException {
        return null;
    }

    @Override
    public List<Client> findByLastName(Connection connection, String lastName) throws SQLException {
        return null;
    }

    @Override
    public Client findByUsername(Connection connection, String username) throws SQLException {
        return null;
    }

    @Override
    public Client findByEmail(Connection connection, String email) throws SQLException {
        return null;
    }

    @Override
    public boolean removeByUsername(Connection connection, String username) throws SQLException {
        return false;
    }

    @Override
    public List<Client> findAll(Connection connection) throws SQLException {
        List<Client> clientList = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM libraryDB.users WHERE user_type = 'CLIENT'");
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String username = resultSet.getString("username");
            String email = resultSet.getString("email");
            List<BorrowedBook> borrowedBooks = new ArrayList<>();
            boolean isActive = resultSet.getBoolean("is_active");
            clientList.add(new Client(id, firstName, lastName, username, email, borrowedBooks, null, isActive));
        }
        return clientList;
    }

    @Override
    public Client findById(Connection connection, int itemId) throws SQLException {
        return null;
    }

    @Override
    public boolean create(Connection connection, Client item) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Connection connection, Client item) throws SQLException {
        return false;
    }

    @Override
    public boolean remove(Connection connection, Client item) throws SQLException {
        return false;
    }

    @Override
    public boolean remove(Connection connection, int itemId) throws SQLException {
        return false;
    }
}
