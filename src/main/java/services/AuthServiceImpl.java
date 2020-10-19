package services;

import models.book.Book;
import models.user.Client;
import models.user.Librarian;
import models.user.UserType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthServiceImpl implements AuthService {

    @Override
    public int getUserId(Connection connection, String username) throws SQLException {
        String query = "SELECT id FROM libraryDB.users WHERE username = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            System.out.println(rs.getInt("id"));
            return rs.getInt("id");
        }
        return 0;
    }

    @Override
    public String getPassword(String password) {
        StringBuilder hashPass = new StringBuilder();
        char[] charArrPass = password.toCharArray();
        for (int i = 0; i < charArrPass.length; i++) {
            hashPass.append(Character.hashCode(charArrPass[i]));
            if (i % 2 == 0) {
                hashPass.append(Character.hashCode(charArrPass[i])).append("&").append(charArrPass[i]).append("^");
            } else {
                hashPass.append(Character.hashCode(charArrPass[i])).append("@").append(charArrPass[i]).append("(");
            }
            if (i % 2 == 1) {
                hashPass.append(Character.hashCode(charArrPass[i])).append("%").append(charArrPass[i]).append(")");
            } else {
                hashPass.append(Character.hashCode(charArrPass[i])).append("$").append(charArrPass[i]).append(";");
            }
        }
        return hashPass.toString();
    }

    @Override
    public ResultSet getUser(Connection connection, int id, String password) throws SQLException {
        String query = "SELECT * FROM libraryDB.users WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, String.valueOf(id));
        ResultSet rs = ps.executeQuery();
        rs.next();
        System.out.println(rs.getString("first_name"));
        System.out.println(rs.getString("last_name"));
        if (rs.getString("user_password").equals(getPassword(password))) {
            return rs;
        }
        return null;
    }

    @Override
    public Client getClient(Connection connection, ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            if (resultSet.getString("user_type").equals(UserType.CLIENT.toString())) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                List<Book> borrowedBooks = new ArrayList<>();
                boolean isActive = resultSet.getBoolean("is_active");
                return new Client(id, firstName, lastName, username, email, borrowedBooks, null, isActive);
            }
        }
        return null;
    }

    @Override
    public Librarian getLibrarian(Connection connection, ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            if (resultSet.getString("user_type").equals(UserType.LIBRARIAN.toString())) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                return new Librarian(id, firstName, lastName, username, email);
            }
        }
        return null;
    }
}

