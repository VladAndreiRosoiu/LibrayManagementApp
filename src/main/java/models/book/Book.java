package models.book;

import java.time.LocalDate;
import java.util.List;

public class Book {
    private int id;
    private String bookName;
    private List<Author> authors;
    private List<Genre> genres;
    private long isbn;
    private LocalDate releaseDate;
    private boolean isBooKBorrowed;

    public Book(int id, String bookName, List<Author> authors, List<Genre> genres, long isbn, LocalDate releaseDate, boolean isBookBorrowed) {
        this.id = id;
        this.bookName = bookName;
        this.authors = authors;
        this.genres=genres;
        this.isbn = isbn;
        this.releaseDate = releaseDate;
        this.isBooKBorrowed = isBookBorrowed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isBooKBorrowed() {
        return isBooKBorrowed;
    }

    public void setBooKBorrowed(boolean booKBorrowed) {
        isBooKBorrowed = booKBorrowed;
    }
}
