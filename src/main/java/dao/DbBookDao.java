package dao;

import models.book.Author;
import models.book.Book;
import models.book.Genre;

import java.util.List;

public class DbBookDao implements BookDao {
    @Override
    public Book findByIsbn(long isbn) {
        return null;
    }

    @Override
    public Book findByName(String name) {
        return null;
    }

    @Override
    public List<Book> findByAuthor(Author author) {
        return null;
    }

    @Override
    public List<Book> findByGenre(Genre genre) {
        return null;
    }

    @Override
    public boolean removeByIsbn(long isbn) {
        return false;
    }

    @Override
    public List<Book> findAll() {
        return null;
    }

    @Override
    public Book findById(int itemId) {
        return null;
    }

    @Override
    public boolean create(Book item) {
        return false;
    }

    @Override
    public boolean update(Book item) {
        return false;
    }

    @Override
    public boolean remove(Book item) {
        return false;
    }

    @Override
    public boolean remove(int itemId) {
        return false;
    }
}
