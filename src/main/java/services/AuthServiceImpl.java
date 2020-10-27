package services;

import database.GetConnection;
import models.book.BorrowedBook;
import models.user.Client;
import models.user.Librarian;
import models.user.User;
import models.user.UserType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthServiceImpl implements AuthService {

    Connection connection = new GetConnection().getConnection();

    @Override
    public int getUserId(String username) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT id FROM libraryDB.users WHERE username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
    public User getUser(int id, String password) {
        User user = null;
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT * FROM libraryDB.users WHERE id = ?");
            preparedStatement.setString(1, String.valueOf(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if (resultSet.getString("user_password").equals(getPassword(password))) {
                if (resultSet.getString("user_type").equals(UserType.CLIENT.toString())) {
                    List<BorrowedBook> borrowedBookList = new ArrayList<>();
                    user = new Client(
                            resultSet.getInt("id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("username"),
                            resultSet.getString("email"),
                            borrowedBookList,
                            null,
                            resultSet.getBoolean("is_active")
                    );
                } else if (resultSet.getString("user_type").equals(UserType.LIBRARIAN.toString())) {
                    user = new Librarian(
                            resultSet.getInt("id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("username"),
                            resultSet.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}

