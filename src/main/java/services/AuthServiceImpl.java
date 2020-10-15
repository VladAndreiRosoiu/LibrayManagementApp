package services;

import models.user.User;
import models.user.UserType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthServiceImpl implements AuthService {

    @Override
    public int getUserId(Connection connection, String username) throws SQLException {
        String query = "SELECT id FROM libraryDB.users WHERE username = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
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
    public String getUserDetails(Connection connection, int id, String password) throws SQLException {
        String query = "SELECT * FROM libraryDB.users WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, String.valueOf(id));
        ResultSet rs = ps.executeQuery();
        rs.next();
        if (rs.getString("user_password").equals(password)){
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String username = rs.getString("username");
            String email = rs.getString("email");
            String userType = rs.getString("user_type");
                return id +","+firstName+","+lastName+","+username+","+email+","+userType;
        }
        return null;
    }
}

