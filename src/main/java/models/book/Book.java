package models.book;

import java.time.LocalDate;
import java.util.List;

public class Book {
    private int id;
    private String title;
    private List<Author> authors;
    private List<String> genres;
    private long isbn;
    private LocalDate releaseDate;
    private int stock;


    public Book(int id, String title, List<Author> authors, List<String> genres, long isbn, LocalDate releaseDate, int stock) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.genres = genres;
        this.isbn = isbn;
        this.releaseDate = releaseDate;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + title + '\'' +
                ", authors=" + authors +
                ", genres=" + genres +
                ", isbn=" + isbn +
                ", releaseDate=" + releaseDate +
                ", stock=" + stock +
                '}';
    }
}
