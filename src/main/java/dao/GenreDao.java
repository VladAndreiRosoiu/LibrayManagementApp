package dao;

import models.book.Genre;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GenreDao extends EntityDao<Genre> {
    List<Genre> findGenreByBookId(Connection connection, int bookId) throws SQLException;
}
