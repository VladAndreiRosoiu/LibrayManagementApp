package dao;

import models.book.Author;
import models.book.Book;
import models.book.Genre;

import java.util.List;

public interface BookDao {
    Book findByIsbn (long isbn);
    Book findByName (String name);
    List<Book> findByAuthor (Author author);
    List<Book> findByGenre (Genre genre);
    boolean removeByIsbn (long isbn);
}
