package dao;

import models.book.Author;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface AuthorDao extends EntityDao<Author> {

    public List<Author> findByBookId(Connection connection, int bookId) throws SQLException;
}
