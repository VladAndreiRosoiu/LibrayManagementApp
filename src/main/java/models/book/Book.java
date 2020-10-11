package models.book;

import java.time.LocalDate;

public class Book {
    private int id;
    private String book;
    private Author author;
    private long isbn;
    private LocalDate releaseDate;
    private boolean isBooKBorrowed;

    public Book(int id, String book, Author author, long isbn, LocalDate releaseDate, boolean isBooKBorrowed) {
        this.id = id;
        this.book = book;
        this.author = author;
        this.isbn = isbn;
        this.releaseDate = releaseDate;
        this.isBooKBorrowed = isBooKBorrowed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
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
