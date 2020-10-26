package dao;

import models.book.Author;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DbAuthorDao implements AuthorDao {

    @Override
    public List<Author> findByBookId(Connection connection, int bookId) throws SQLException {
        List<Author> authorList = new ArrayList<>();
        PreparedStatement preparedStatement = connection.
                prepareStatement("SELECT id_author FROM libraryDB.book_author WHERE id_book = ?");
        preparedStatement.setString(1, String.valueOf(bookId));
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id_author");
            PreparedStatement preparedStmtAuthor = connection.
                    prepareStatement("SELECT * FROM libraryDB.authors WHERE id = ?");
            preparedStmtAuthor.setString(1, String.valueOf(id));
            ResultSet resultSetAuthor = preparedStmtAuthor.executeQuery();
            while (resultSetAuthor.next()) {
                int idAut = resultSetAuthor.getInt("id");
                String firstName = resultSetAuthor.getString("first_name");
                String lastName = resultSetAuthor.getString("last_name");
                String description = resultSetAuthor.getString("additional_info");
                LocalDate birthDate = resultSetAuthor.getDate("birth_date").toLocalDate();
                authorList.add(new Author(idAut, firstName, lastName, description, birthDate));
            }
        }
        return authorList;
    }

    @Override
    public List<Author> findAll(Connection connection) throws SQLException {
        List<Author> authorList = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM libraryDB.authors");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String description = resultSet.getString("additional_info");
            LocalDate birthDate = resultSet.getDate("birth_date").toLocalDate();
            authorList.add(new Author(id, firstName, lastName, description, birthDate));
        }
        return authorList;
    }

    @Override
    public Author findById(Connection connection, int authorId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM libraryDB.authors WHERE id = ?");
        preparedStatement.setString(1, String.valueOf(authorId));
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            int id = resultSet.getInt("id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String description = resultSet.getString("additional_info");
            LocalDate birthDate = resultSet.getDate("birth_date").toLocalDate();
            return new Author(id, firstName, lastName, description, birthDate);
        }
        return null;
    }

    @Override
    public boolean create(Connection connection, Author author) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO libraryDB.authors(first_name, last_name, additional_info, birth_date) VALUES (?,?,?,?)");
        preparedStatement.setString(1, author.getFirstName());
        preparedStatement.setString(2, author.getLastName());
        preparedStatement.setString(3, author.getDescription());
        preparedStatement.setString(4, String.valueOf(author.getBirthDate()));
        preparedStatement.executeUpdate();
        return false;
    }

    @Override
    public boolean update(Connection connection, Author author) throws SQLException {
        return false;
    }

    @Override
    public boolean remove(Connection connection, Author author) throws SQLException {
        return false;
    }

    @Override
    public boolean remove(Connection connection, int authorId) throws SQLException {
        return false;
    }
}
