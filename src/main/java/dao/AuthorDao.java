package dao;

import models.book.Author;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface AuthorDao extends EntityDao<Author> {
    Author findByBirthDate(Connection connection, LocalDate birthDate) throws SQLException;

    public List<Author> findByBookId(Connection connection, int itemId) throws SQLException;
}
