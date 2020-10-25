package dao;

import models.book.Author;
import models.book.Book;
import models.book.Genre;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DbBookDao implements BookDao {

    @Override
    public Book findByIsbn(Connection connection, long isbn) throws SQLException {
        PreparedStatement pStmtGetBook = connection.prepareStatement(
                "SELECT * FROM libraryDB.books WHERE isbn = ?");
        pStmtGetBook.setString(1, String.valueOf(isbn));
        ResultSet rSetGetBook = pStmtGetBook.executeQuery();
        if (rSetGetBook.next()) {
            List<Author> authorList = new ArrayList<>();
            List<Genre> genreList = new ArrayList<>();
            int bookId = rSetGetBook.getInt("id");
            PreparedStatement pStmtGetAuthorId = connection.prepareStatement(
                    "SELECT * FROM libraryDB.book_author WHERE id_book = ?");
            pStmtGetAuthorId.setString(1, String.valueOf(bookId));
            ResultSet rSetGetAuthorId = pStmtGetAuthorId.executeQuery();
            while (rSetGetAuthorId.next()) {
                int authorId = rSetGetAuthorId.getInt("id_author");
                PreparedStatement pStmtGetAuthor = connection.prepareStatement(
                        "SELECT * FROM libraryDB.authors WHERE id = ?");
                pStmtGetAuthor.setString(1, String.valueOf(authorId));
                ResultSet rSetGetAuthor = pStmtGetAuthor.executeQuery();
                while (rSetGetAuthor.next()) {
                    authorList.add(new Author(
                            rSetGetAuthor.getInt("id"),
                            rSetGetAuthor.getString("first_name"),
                            rSetGetAuthor.getString("last_name"),
                            rSetGetAuthor.getString("additional_info"),
                            rSetGetAuthor.getDate("birth_date").toLocalDate(),
                            LocalDate.now()
                    ));
                }
            }
            PreparedStatement pStmtGetGenreId = connection.prepareStatement(
                    "SELECT * FROM libraryDB.book_genre WHERE id_book = ?");
            pStmtGetGenreId.setString(1, String.valueOf(bookId));
            ResultSet rSetGetGenreId = pStmtGetGenreId.executeQuery();
            while (rSetGetGenreId.next()) {
                int genreId = rSetGetGenreId.getInt("id_genre");
                PreparedStatement pStmtGetGenre = connection.prepareStatement(
                        "SELECT * FROM libraryDB.genre WHERE id = ?");
                pStmtGetGenre.setString(1, String.valueOf(genreId));
                ResultSet rSetGetGenre = pStmtGetGenre.executeQuery();
                while (rSetGetGenre.next()) {
                    String genre = rSetGetGenre.getString("genre_type").replace(" ", "_").toUpperCase();
                    genreList.add(Genre.valueOf(genre));
                }
            }
            return new Book(
                    bookId,
                    rSetGetBook.getString("book_name"),
                    authorList,
                    genreList,
                    rSetGetBook.getLong("isbn"),
                    rSetGetBook.getDate("release_date").toLocalDate(),
                    rSetGetBook.getInt("stock")
            );
        }
        return null;
    }

    @Override
    public List<Book> findByTitle(Connection connection, String name) throws SQLException {
        List<Book> bookList = new ArrayList<>();
        PreparedStatement pStmtGetBook = connection.prepareStatement(
                "SELECT * FROM libraryDB.books WHERE book_name LIKE ?");
        pStmtGetBook.setString(1, "%" + name + "%");
        ResultSet rSetGetBook = pStmtGetBook.executeQuery();
        while (rSetGetBook.next()) {
            List<Author> authorList = new ArrayList<>();
            List<Genre> genreList = new ArrayList<>();
            int bookId = rSetGetBook.getInt("id");
            PreparedStatement pStmtGetAuthorId = connection.prepareStatement(
                    "SELECT * FROM libraryDB.book_author WHERE id_book = ?");
            pStmtGetAuthorId.setString(1, String.valueOf(bookId));
            ResultSet rSetGetAuthorId = pStmtGetAuthorId.executeQuery();
            while (rSetGetAuthorId.next()) {
                int authorId = rSetGetAuthorId.getInt("id_author");
                PreparedStatement pStmtGetAuthor = connection.prepareStatement(
                        "SELECT * FROM libraryDB.authors WHERE id = ?");
                pStmtGetAuthor.setString(1, String.valueOf(authorId));
                ResultSet rSetGetAuthor = pStmtGetAuthor.executeQuery();
                while (rSetGetAuthor.next()) {
                    authorList.add(new Author(
                            rSetGetAuthor.getInt("id"),
                            rSetGetAuthor.getString("first_name"),
                            rSetGetAuthor.getString("last_name"),
                            rSetGetAuthor.getString("additional_info"),
                            rSetGetAuthor.getDate("birth_date").toLocalDate(),
                            LocalDate.now()
                    ));
                }
            }
            PreparedStatement pStmtGetGenreId = connection.prepareStatement(
                    "SELECT * FROM libraryDB.book_genre WHERE id_book = ?");
            pStmtGetGenreId.setString(1, String.valueOf(bookId));
            ResultSet rSetGetGenreId = pStmtGetGenreId.executeQuery();
            while (rSetGetGenreId.next()) {
                int genreId = rSetGetGenreId.getInt("id_genre");
                PreparedStatement pStmtGetGenre = connection.prepareStatement(
                        "SELECT * FROM libraryDB.genre WHERE id = ?");
                pStmtGetGenre.setString(1, String.valueOf(genreId));
                ResultSet rSetGetGenre = pStmtGetGenre.executeQuery();
                while (rSetGetGenre.next()) {
                    String genre = rSetGetGenre.getString("genre_type").replace(" ", "_").toUpperCase();
                    genreList.add(Genre.valueOf(genre));
                }
            }
            bookList.add(new Book(
                    bookId,
                    rSetGetBook.getString("book_name"),
                    authorList,
                    genreList,
                    rSetGetBook.getLong("isbn"),
                    rSetGetBook.getDate("release_date").toLocalDate(),
                    rSetGetBook.getInt("stock")
            ));
        }
        return bookList;
    }

    @Override
    public List<Book> findByAuthor(Connection connection, Author author) throws SQLException {
        List<Book> bookList = new ArrayList<>();
        Statement stmtGetBook = connection.createStatement();
        ResultSet rSetGetBook = stmtGetBook.executeQuery("SELECT * FROM libraryDB.books");
        while (rSetGetBook.next()) {
            List<Author> authorList = new ArrayList<>();
            List<Genre> genreList = new ArrayList<>();
            int bookId = rSetGetBook.getInt("id");
            PreparedStatement pStmtGetAuthorId = connection.prepareStatement(
                    "SELECT * FROM libraryDB.book_author WHERE id_book = ?");
            pStmtGetAuthorId.setString(1, String.valueOf(bookId));
            ResultSet rSetGetAuthorId = pStmtGetAuthorId.executeQuery();
            while (rSetGetAuthorId.next()) {
                int authorId = rSetGetAuthorId.getInt("id_author");
                PreparedStatement pStmtGetAuthor = connection.prepareStatement(
                        "SELECT * FROM libraryDB.authors WHERE id = ?");
                pStmtGetAuthor.setString(1, String.valueOf(authorId));
                ResultSet rSetGetAuthor = pStmtGetAuthor.executeQuery();
                while (rSetGetAuthor.next()) {
                    authorList.add(new Author(
                            rSetGetAuthor.getInt("id"),
                            rSetGetAuthor.getString("first_name"),
                            rSetGetAuthor.getString("last_name"),
                            rSetGetAuthor.getString("additional_info"),
                            rSetGetAuthor.getDate("birth_date").toLocalDate(),
                            LocalDate.now()
                    ));
                }
            }
            PreparedStatement pStmtGetGenreId = connection.prepareStatement(
                    "SELECT * FROM libraryDB.book_genre WHERE id_book = ?");
            pStmtGetGenreId.setString(1, String.valueOf(bookId));
            ResultSet rSetGetGenreId = pStmtGetGenreId.executeQuery();
            while (rSetGetGenreId.next()) {
                int genreId = rSetGetGenreId.getInt("id_genre");
                PreparedStatement pStmtGetGenre = connection.prepareStatement(
                        "SELECT * FROM libraryDB.genre WHERE id = ?");
                pStmtGetGenre.setString(1, String.valueOf(genreId));
                ResultSet rSetGetGenre = pStmtGetGenre.executeQuery();
                while (rSetGetGenre.next()) {
                    String genre = rSetGetGenre.getString("genre_type").replace(" ", "_").toUpperCase();
                    genreList.add(Genre.valueOf(genre));
                }
            }
            bookList.add(new Book(
                    bookId,
                    rSetGetBook.getString("book_name"),
                    authorList,
                    genreList,
                    rSetGetBook.getLong("isbn"),
                    rSetGetBook.getDate("release_date").toLocalDate(),
                    rSetGetBook.getInt("stock")
            ));
        }
        return bookList.stream().filter(book -> book.getAuthors().contains(author)).collect(Collectors.toList());
    }

    @Override
    public List<Book> findByGenre(Connection connection, Genre genre) throws SQLException {
        List<Book> bookList = new ArrayList<>();
        Statement stmtGetBook = connection.createStatement();
        ResultSet rSetGetBook = stmtGetBook.executeQuery("SELECT * FROM libraryDB.books");
        while (rSetGetBook.next()) {
            List<Author> authorList = new ArrayList<>();
            List<Genre> genreList = new ArrayList<>();
            int bookId = rSetGetBook.getInt("id");
            PreparedStatement pStmtGetAuthorId = connection.prepareStatement(
                    "SELECT * FROM libraryDB.book_author WHERE id_book = ?");
            pStmtGetAuthorId.setString(1, String.valueOf(bookId));
            ResultSet rSetGetAuthorId = pStmtGetAuthorId.executeQuery();
            while (rSetGetAuthorId.next()) {
                int authorId = rSetGetAuthorId.getInt("id_author");
                PreparedStatement pStmtGetAuthor = connection.prepareStatement(
                        "SELECT * FROM libraryDB.authors WHERE id = ?");
                pStmtGetAuthor.setString(1, String.valueOf(authorId));
                ResultSet rSetGetAuthor = pStmtGetAuthor.executeQuery();
                while (rSetGetAuthor.next()) {
                    authorList.add(new Author(
                            rSetGetAuthor.getInt("id"),
                            rSetGetAuthor.getString("first_name"),
                            rSetGetAuthor.getString("last_name"),
                            rSetGetAuthor.getString("additional_info"),
                            rSetGetAuthor.getDate("birth_date").toLocalDate(),
                            LocalDate.now()
                    ));
                }
            }
            PreparedStatement pStmtGetGenreId = connection.prepareStatement(
                    "SELECT * FROM libraryDB.book_genre WHERE id_book = ?");
            pStmtGetGenreId.setString(1, String.valueOf(bookId));
            ResultSet rSetGetGenreId = pStmtGetGenreId.executeQuery();
            while (rSetGetGenreId.next()) {
                int genreId = rSetGetGenreId.getInt("id_genre");
                PreparedStatement pStmtGetGenre = connection.prepareStatement(
                        "SELECT * FROM libraryDB.genre WHERE id = ?");
                pStmtGetGenre.setString(1, String.valueOf(genreId));
                ResultSet rSetGetGenre = pStmtGetGenre.executeQuery();
                while (rSetGetGenre.next()) {
                    String genreString = rSetGetGenre.getString("genre_type").replace(" ", "_").toUpperCase();
                    genreList.add(Genre.valueOf(genreString));
                }
            }
            bookList.add(new Book(
                    bookId,
                    rSetGetBook.getString("book_name"),
                    authorList,
                    genreList,
                    rSetGetBook.getLong("isbn"),
                    rSetGetBook.getDate("release_date").toLocalDate(),
                    rSetGetBook.getInt("stock")
            ));
        }
        return bookList.stream().filter(book -> book.getGenres().contains(genre)).collect(Collectors.toList());
    }

    @Override
    public boolean removeByIsbn(Connection connection, long isbn) throws SQLException {
        return false;
    }

    @Override
    public List<Book> findAll(Connection connection) throws SQLException {
        List<Book> bookList = new ArrayList<>();
        Statement stmtGetBook = connection.createStatement();
        ResultSet rSetGetBook = stmtGetBook.executeQuery("SELECT * FROM libraryDB.books");
        while (rSetGetBook.next()) {
            List<Author> authorList = new ArrayList<>();
            List<Genre> genreList = new ArrayList<>();
            int bookId = rSetGetBook.getInt("id");
            PreparedStatement pStmtGetAuthorId = connection.prepareStatement(
                    "SELECT * FROM libraryDB.book_author WHERE id_book = ?");
            pStmtGetAuthorId.setString(1, String.valueOf(bookId));
            ResultSet rSetGetAuthorId = pStmtGetAuthorId.executeQuery();
            while (rSetGetAuthorId.next()) {
                int authorId = rSetGetAuthorId.getInt("id_author");
                PreparedStatement pStmtGetAuthor = connection.prepareStatement(
                        "SELECT * FROM libraryDB.authors WHERE id = ?");
                pStmtGetAuthor.setString(1, String.valueOf(authorId));
                ResultSet rSetGetAuthor = pStmtGetAuthor.executeQuery();
                while (rSetGetAuthor.next()) {
                    authorList.add(new Author(
                            rSetGetAuthor.getInt("id"),
                            rSetGetAuthor.getString("first_name"),
                            rSetGetAuthor.getString("last_name"),
                            rSetGetAuthor.getString("additional_info"),
                            rSetGetAuthor.getDate("birth_date").toLocalDate(),
                            LocalDate.now()
                    ));
                }
            }
            PreparedStatement pStmtGetGenreId = connection.prepareStatement(
                    "SELECT * FROM libraryDB.book_genre WHERE id_book = ?");
            pStmtGetGenreId.setString(1, String.valueOf(bookId));
            ResultSet rSetGetGenreId = pStmtGetGenreId.executeQuery();
            while (rSetGetGenreId.next()) {
                int genreId = rSetGetGenreId.getInt("id_genre");
                PreparedStatement pStmtGetGenre = connection.prepareStatement(
                        "SELECT * FROM libraryDB.genre WHERE id = ?");
                pStmtGetGenre.setString(1, String.valueOf(genreId));
                ResultSet rSetGetGenre = pStmtGetGenre.executeQuery();
                while (rSetGetGenre.next()) {
                    String genre = rSetGetGenre.getString("genre_type").replace(" ", "_").toUpperCase();
                    genreList.add(Genre.valueOf(genre));
                }
            }
            bookList.add(new Book(
                    bookId,
                    rSetGetBook.getString("book_name"),
                    authorList,
                    genreList,
                    rSetGetBook.getLong("isbn"),
                    rSetGetBook.getDate("release_date").toLocalDate(),
                    rSetGetBook.getInt("stock")
            ));
        }
        return bookList;
    }

    @Override
    public Book findById(Connection connection, int id) throws SQLException {
        PreparedStatement pStmtGetBook = connection.prepareStatement(
                "SELECT * FROM libraryDB.books WHERE id = ?");
        pStmtGetBook.setString(1, String.valueOf(id));
        ResultSet rSetGetBook = pStmtGetBook.executeQuery();
        if (rSetGetBook.next()) {
            List<Author> authorList = new ArrayList<>();
            List<Genre> genreList = new ArrayList<>();
            int bookId = rSetGetBook.getInt("id");
            PreparedStatement pStmtGetAuthorId = connection.prepareStatement(
                    "SELECT * FROM libraryDB.book_author WHERE id_book = ?");
            pStmtGetAuthorId.setString(1, String.valueOf(bookId));
            ResultSet rSetGetAuthorId = pStmtGetAuthorId.executeQuery();
            while (rSetGetAuthorId.next()) {
                int authorId = rSetGetAuthorId.getInt("id_author");
                PreparedStatement pStmtGetAuthor = connection.prepareStatement(
                        "SELECT * FROM libraryDB.authors WHERE id = ?");
                pStmtGetAuthor.setString(1, String.valueOf(authorId));
                ResultSet rSetGetAuthor = pStmtGetAuthor.executeQuery();
                while (rSetGetAuthor.next()) {
                    authorList.add(new Author(
                            rSetGetAuthor.getInt("id"),
                            rSetGetAuthor.getString("first_name"),
                            rSetGetAuthor.getString("last_name"),
                            rSetGetAuthor.getString("additional_info"),
                            rSetGetAuthor.getDate("birth_date").toLocalDate(),
                            LocalDate.now()
                    ));
                }
            }
            PreparedStatement pStmtGetGenreId = connection.prepareStatement(
                    "SELECT * FROM libraryDB.book_genre WHERE id_book = ?");
            pStmtGetGenreId.setString(1, String.valueOf(bookId));
            ResultSet rSetGetGenreId = pStmtGetGenreId.executeQuery();
            while (rSetGetGenreId.next()) {
                int genreId = rSetGetGenreId.getInt("id_genre");
                PreparedStatement pStmtGetGenre = connection.prepareStatement(
                        "SELECT * FROM libraryDB.genre WHERE id = ?");
                pStmtGetGenre.setString(1, String.valueOf(genreId));
                ResultSet rSetGetGenre = pStmtGetGenre.executeQuery();
                while (rSetGetGenre.next()) {
                    String genre = rSetGetGenre.getString("genre_type").replace(" ", "_").toUpperCase();
                    genreList.add(Genre.valueOf(genre));
                }
            }
            return new Book(
                    bookId,
                    rSetGetBook.getString("book_name"),
                    authorList,
                    genreList,
                    rSetGetBook.getLong("isbn"),
                    rSetGetBook.getDate("release_date").toLocalDate(),
                    rSetGetBook.getInt("stock")
            );
        }
        return null;
    }

    @Override
    public boolean create(Connection connection, Book book) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Connection connection, Book book) throws SQLException {
        return false;
    }

    @Override
    public boolean remove(Connection connection, Book book) throws SQLException {
        return false;
    }

    @Override
    public boolean remove(Connection connection, int id) throws SQLException {
        return false;
    }
}
