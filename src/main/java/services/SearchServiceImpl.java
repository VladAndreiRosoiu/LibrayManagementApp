package services;

import models.book.Author;
import models.book.Book;
import models.book.Genre;

import java.util.List;
import java.util.stream.Collectors;

public class SearchServiceImpl implements SearchService {

    @Override
    public Book searchByIsbn(long isbn, List<Book> bookList) {
        return bookList.stream().filter(book -> book.getIsbn() == isbn).findAny().orElse(null);
    }

    @Override
    public List<Book> searchByTitle(String title, List<Book> bookList) {
        return bookList.stream().
                filter(book -> book.getBookName()
                        .toLowerCase()
                        .contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> searchByAuthor(Author author, List<Book> bookList) {
        return bookList.stream().
                filter(book -> book.getAuthors().contains(author))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> searchByGenre(Genre genre, List<Book> bookList) {
        return bookList.stream()
                .filter(book -> book.getGenres().contains(genre))
                .collect(Collectors.toList());
    }

    @Override
    public Author getAuthor(String firstName, String lastName, List<Author> authorList) {
        return authorList.stream()
                .filter(author -> author.getFirstName().equalsIgnoreCase(firstName) &&
                        author.getLastName().equalsIgnoreCase(lastName))
                .findAny()
                .orElse(null);
    }

    @Override
    public Genre getGenre(String genre, List<Genre> genreList) {
        return genreList.stream()
                .filter(genreObj -> genreObj.equals(Genre.valueOf(genre)))
                .findAny()
                .orElse(null);
    }
}
