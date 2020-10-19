package dao;

import models.book.Author;
import models.book.Book;
import models.book.Genre;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DbBookDao implements BookDao {

    @Override
    public Book findByIsbn(Connection connection, long isbn) throws SQLException {
        return null;
    }

    @Override
    public Book findByName(Connection connection, String name) throws SQLException {
        return null;
    }

    @Override
    public List<Book> findByAuthor(Connection connection, Author author) throws SQLException {
        return null;
    }

    @Override
    public List<Book> findByGenre(Connection connection, Genre genre) throws SQLException {
        return null;
    }

    @Override
    public boolean removeByIsbn(Connection connection, long isbn) throws SQLException {
        return false;
    }

    @Override
    public List<Book> findAll(Connection connection) throws SQLException {
        List<Book> bookList = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * from libraryDB.books");
        while (rs.next()) {
            int id = rs.getInt("id");
            String bookName = rs.getString("book_name");
            Long isbn = rs.getLong("isbn");
            LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
            int stock = rs.getInt("stock");
            List<Author> authorList = new ArrayList<>();
            List<Genre> genreList = new ArrayList<>();
            bookList.add(new Book(id, bookName, authorList, genreList, isbn, releaseDate, stock));
        }
        return bookList;
    }

    @Override
    public Book findById(Connection connection, int itemId) throws SQLException {
        return null;
    }

    @Override
    public boolean create(Connection connection, Book item) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Connection connection, Book item) throws SQLException {
        return false;
    }

    @Override
    public boolean remove(Connection connection, Book item) throws SQLException {
        return false;
    }

    @Override
    public boolean remove(Connection connection, int itemId) throws SQLException {
        return false;
    }
}
