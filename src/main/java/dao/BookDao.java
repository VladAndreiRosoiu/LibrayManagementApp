package dao;

import models.book.Author;
import models.book.Book;

import java.util.List;

public interface BookDao extends EntityDao<Book> {

    Book findByIsbn(long isbn);

    List<Book> findByTitle(String name);

    List<Book> findByAuthor(Author author);

    List<Book> findByGenre(String genre);

    boolean removeByIsbn(long isbn);
}
