package dao;

import models.book.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DbAuthorDao implements AuthorDao {

    @Override
    public Author findByBirthDate(Connection connection, LocalDate birthDate) throws SQLException {
        return null;
    }

    @Override
    public List<Author> findAll(Connection connection) throws SQLException {
        return null;
    }

    @Override
    public Author findById(Connection connection, int itemId) throws SQLException {
        String query = "SELECT id_author FROM libraryDB.book_author WHERE id_book = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, String.valueOf(itemId));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id_author");
            String query2 = "SELECT * FROM libraryDB.authors WHERE id = ?";
            PreparedStatement ps2 = connection.prepareStatement(query2);
            ps2.setString(1, String.valueOf(id));
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                int idAut = rs2.getInt("id");
                String firstName = rs2.getString("first_name");
                String lastName = rs2.getString("last_name");
                String description = rs2.getString("aditional_info");
                LocalDate birthDate = rs2.getDate("birth_date").toLocalDate();
                //LocalDate deathDate = rs2.getDate("death_date").toLocalDate();
                return new Author(idAut, firstName, lastName, description, birthDate, LocalDate.now());
            }
        }
        return null;
    }

    @Override
    public boolean create(Connection connection, Author item) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Connection connection, Author item) throws SQLException {
        return false;
    }

    @Override
    public boolean remove(Connection connection, Author item) throws SQLException {
        return false;
    }

    @Override
    public boolean remove(Connection connection, int itemId) throws SQLException {
        return false;
    }
}
