package dao;

import database.GetConnection;
import models.book.Author;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DbAuthorDao implements AuthorDao {

    Connection connection = new GetConnection().getConnection();

    @Override
    public List<Author> findByBookId(int bookId) {
        List<Author> authorList = new ArrayList<>();
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authorList;
    }


    @Override
    public List<Author> findAll() {
        List<Author> authorList = new ArrayList<>();
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authorList;
    }

    @Override
    public Author findById(int authorId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM libraryDB.authors WHERE id = ?");
            preparedStatement.setString(1, String.valueOf(authorId));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String description = resultSet.getString("additional_info");
                LocalDate birthDate = resultSet.getDate("birth_date").toLocalDate();
                return new Author(id, firstName, lastName, description, birthDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean create(Author author) {
        try {
            List<Integer> dbId = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM libraryDB.authors WHERE first_name LIKE ? AND last_name LIKE ?");
            statement.setString(1, author.getFirstName());
            statement.setString(2, author.getLastName());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                dbId.add(resultSet.getInt("id"));
            } else {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO libraryDB.authors(first_name, last_name, additional_info, birth_date) VALUES (?,?,?,?)");
                preparedStatement.setString(1, author.getFirstName());
                preparedStatement.setString(2, author.getLastName());
                preparedStatement.setString(3, author.getDescription());
                preparedStatement.setString(4, String.valueOf(author.getBirthDate()));
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Author author) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE libraryDB.authors SET first_name = ?, last_name = ?, additional_info = ?, birth_date = ? WHERE id = ?");
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            preparedStatement.setString(3, author.getDescription());
            preparedStatement.setString(4, String.valueOf(author.getBirthDate()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(Author author) {
        return false;
    }

    @Override
    public boolean remove(int authorId) {
        return false;
    }

    @Override
    public List<Integer> getInsertedAuthorsIds(List<Author> authorList) {
        List<Integer> authorIds = new ArrayList<>();
        try {
            for (Author author : authorList) {
                PreparedStatement pStmtCheckAuthor = connection.prepareStatement(
                        "SELECT * FROM libraryDB.authors WHERE first_name = ? AND last_name = ? AND birth_date = ?");
                pStmtCheckAuthor.setString(1, author.getFirstName());
                pStmtCheckAuthor.setString(2, author.getLastName());
                pStmtCheckAuthor.setString(3, author.getBirthDate().toString());
                ResultSet rSetCheckAuthor = pStmtCheckAuthor.executeQuery();
                if (rSetCheckAuthor.next()) {
                    authorIds.add(rSetCheckAuthor.getInt("id"));
                } else {
                    PreparedStatement pStmtInsertAuthor = connection.prepareStatement(
                            "INSERT INTO libraryDB.authors(first_name, last_name, additional_info, birth_date ) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                    pStmtInsertAuthor.setString(1, author.getFirstName());
                    pStmtInsertAuthor.setString(2, author.getLastName());
                    pStmtInsertAuthor.setString(3, author.getDescription());
                    pStmtInsertAuthor.setString(4, author.getBirthDate().toString());
                    pStmtInsertAuthor.executeUpdate();
                    try (ResultSet generatedKeys = pStmtInsertAuthor.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            authorIds.add(generatedKeys.getInt(1));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authorIds;
    }

}
