package services;

import models.book.Author;
import models.book.Book;
import models.book.Genre;

import java.util.List;

public interface SearchService {
    Book searchByIsbn(long isbn, List<Book> bookList);

    List<Book> searchByTitle(String title, List<Book> bookList);

    List<Book> searchByAuthor(Author author, List<Book> bookList);

    List<Book> searchByGenre(String genre, List<Book> bookList);

    Author getAuthor(String firstName, String lastName, List<Author> authorList);

    String getGenre(String genre, List<Genre> genreList);
}
