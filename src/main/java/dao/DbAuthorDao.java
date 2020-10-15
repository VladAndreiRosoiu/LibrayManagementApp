package dao;

import models.book.Author;

import java.time.LocalDate;
import java.util.List;

public class DbAuthorDao implements AuthorDao {
    @Override
    public Author findByBirthDate(LocalDate birthDate) {
        return null;
    }

    @Override
    public List<Author> findAll() {
        return null;
    }

    @Override
    public Author findById(int itemId) {
        return null;
    }

    @Override
    public boolean create(Author item) {
        return false;
    }

    @Override
    public boolean update(Author item) {
        return false;
    }

    @Override
    public boolean remove(Author item) {
        return false;
    }

    @Override
    public boolean remove(int itemId) {
        return false;
    }
}
