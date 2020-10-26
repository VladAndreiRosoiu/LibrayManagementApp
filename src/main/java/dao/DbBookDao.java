package dao;

import database.GetDBConnection;
import models.book.Author;
import models.book.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DbBookDao implements BookDao {

    Connection connection = new GetDBConnection().getConnection();

    @Override
    public Book findByIsbn(long isbn) {
        PreparedStatement pStmtGetBook = connection.prepareStatement(
                "SELECT * FROM libraryDB.books WHERE isbn = ?");
        pStmtGetBook.setString(1, String.valueOf(isbn));
        ResultSet rSetGetBook = pStmtGetBook.executeQuery();
        if (rSetGetBook.next()) {
            List<Author> authorList = new ArrayList<>();
            List<String> genreList = new ArrayList<>();
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
                            rSetGetAuthor.getDate("birth_date").toLocalDate()
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
                    String genre = rSetGetGenre.getString("genre_type");
                    genreList.add(genre);
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
    public List<Book> findByTitle(String name) {
        List<Book> bookList = new ArrayList<>();
        PreparedStatement pStmtGetBook = connection.prepareStatement(
                "SELECT * FROM libraryDB.books WHERE book_name LIKE ?");
        pStmtGetBook.setString(1, "%" + name + "%");
        ResultSet rSetGetBook = pStmtGetBook.executeQuery();
        while (rSetGetBook.next()) {
            List<Author> authorList = new ArrayList<>();
            List<String> genreList = new ArrayList<>();
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
                            rSetGetAuthor.getDate("birth_date").toLocalDate()
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
                    String genre = rSetGetGenre.getString("genre_type");
                    genreList.add(genre);
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
    public List<Book> findByAuthor(Author author) {
        List<Book> bookList = new ArrayList<>();
        Statement stmtGetBook = connection.createStatement();
        ResultSet rSetGetBook = stmtGetBook.executeQuery("SELECT * FROM libraryDB.books");
        while (rSetGetBook.next()) {
            List<Author> authorList = new ArrayList<>();
            List<String> genreList = new ArrayList<>();
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
                            rSetGetAuthor.getDate("birth_date").toLocalDate()
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
                    String genre = rSetGetGenre.getString("genre_type");
                    genreList.add(genre);
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
    public List<Book> findByGenre(String genre) {
        List<Book> bookList = new ArrayList<>();
        Statement stmtGetBook = connection.createStatement();
        ResultSet rSetGetBook = stmtGetBook.executeQuery("SELECT * FROM libraryDB.books");
        while (rSetGetBook.next()) {
            List<Author> authorList = new ArrayList<>();
            List<String> genreList = new ArrayList<>();
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
                            rSetGetAuthor.getDate("birth_date").toLocalDate()
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
                    String genreResult = rSetGetGenre.getString("genre_type");
                    genreList.add(genreResult);
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
    public boolean removeByIsbn(long isbn) {
        return false;
    }

    @Override
    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        Statement stmtGetBook = connection.createStatement();
        ResultSet rSetGetBook = stmtGetBook.executeQuery("SELECT * FROM libraryDB.books");
        while (rSetGetBook.next()) {
            List<Author> authorList = new ArrayList<>();
            List<String> genreList = new ArrayList<>();
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
                            rSetGetAuthor.getDate("birth_date").toLocalDate()
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
                    String genre = rSetGetGenre.getString("genre_type");
                    genreList.add(genre);
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
    public Book findById(int id) {
        PreparedStatement pStmtGetBook = connection.prepareStatement(
                "SELECT * FROM libraryDB.books WHERE id = ?");
        pStmtGetBook.setString(1, String.valueOf(id));
        ResultSet rSetGetBook = pStmtGetBook.executeQuery();
        if (rSetGetBook.next()) {
            List<Author> authorList = new ArrayList<>();
            List<String> genreList = new ArrayList<>();
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
                            rSetGetAuthor.getDate("birth_date").toLocalDate()
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
                    String genre = rSetGetGenre.getString("genre_type");
                    genreList.add(genre);
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
    public boolean create(Book book) {

//        genreDao.createGenre();  if it doesn't exists! if it exists, get his id. maybe do a for-each?
//        authorDao.createAuthor();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO libraryDB.books(book_name, isbn, stock, release_date) VALUES (?,?,?,?)");
        preparedStatement.setString(1, book.getBookName());
        preparedStatement.setString(2, String.valueOf(book.getIsbn()));
        preparedStatement.setString(3, String.valueOf(book.getStock()));
        preparedStatement.setString(4, String.valueOf(book.getReleaseDate()));
        preparedStatement.executeUpdate();
        return false;
    }

    @Override
    public boolean update(Book book) {
        return false;
    }

    @Override
    public boolean remove(Book book) {
        return false;
    }

    @Override
    public boolean remove(int id) {
        return false;
    }
}
