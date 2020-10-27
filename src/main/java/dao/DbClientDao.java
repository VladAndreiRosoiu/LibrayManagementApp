package dao;

import database.GetConnection;
import models.book.BorrowedBook;
import models.user.Client;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbClientDao implements ClientDao {

    Connection connection = new GetConnection().getConnection();

    @Override
    public List<Client> findByFirstName(String firstName) {
        return null;
    }

    @Override
    public List<Client> findByLastName(String lastName) {
        return null;
    }

    @Override
    public Client findByUsername(String username) {
        return null;
    }

    @Override
    public Client findByEmail(String email) {
        return null;
    }

    @Override
    public boolean removeByUsername(String username) {
        return false;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clientList = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM libraryDB.users WHERE user_type = 'CLIENT'");
            while (resultSet.next()) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientList;
    }

    @Override
    public Client findById(int itemId) {
        return null;
    }

    @Override
    public boolean create(Client item) {
        return false;
    }

    @Override
    public boolean update(Client item) {
        return false;
    }

    @Override
    public boolean remove(Client item) {
        return false;
    }

    @Override
    public boolean remove(int itemId) {
        return false;
    }
}
