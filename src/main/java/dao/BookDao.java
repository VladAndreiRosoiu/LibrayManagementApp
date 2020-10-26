package dao;

import models.book.Author;
import models.book.Book;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BookDao extends EntityDao<Book> {

    Book findByIsbn(Connection connection, long isbn) throws SQLException;

    List<Book> findByTitle(Connection connection, String name) throws SQLException;

    List<Book> findByAuthor(Connection connection, Author author) throws SQLException;

    List<Book> findByGenre(Connection connection, String genre) throws SQLException;

    boolean removeByIsbn(Connection connection, long isbn) throws SQLException;
}
