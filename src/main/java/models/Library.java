package models;

import dao.*;
import models.book.Author;
import models.book.Book;
import models.book.Genre;
import models.user.Client;
import models.user.Librarian;
import services.AuthService;
import services.AuthServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Library {
    private final Scanner scanner = new Scanner(System.in);
    private final Connection connection;
    private Client client;
    private Librarian librarian;
    List<Book> bookList = new ArrayList<>();
    AuthService authService = new AuthServiceImpl();
    AuthorDao authorDao = new DbAuthorDao();
    BookDao bookDao = new DbBookDao();
    GenreDao genreDao = new DbGenreDao();

    public Library(Connection connection) {
        this.connection = connection;
    }

    public void initLibrary() throws SQLException {
        int option;
        welcomeMenu();
        option = scanner.nextInt();
        doWelcomeMenu(option);
        do {
            if (client != null) {
                clientLogic();
            }
            if (librarian != null) {
                //TODO librarian logic
                librarianMenu();
                option = scanner.nextInt();
                switch (option) {
                    case 1:
                        //TODO
                        break;
                    case 2:
                        //TODO
                        //TODO
                        break;
                    default:
                        librarianMenu();
                }
            }
        } while (client != null || librarian != null);
    }

//------------------- CLIENT RELATED METHODS ---------------------------------------------------------------------------

    private void clientLogic() throws SQLException {
        //TODO client logic
        clientMenu();
        int option;
        option = scanner.nextInt();
        switch (option) {
            case 1:
                bookListingMenu();
                System.out.println("enter option");
                option = scanner.nextInt();
                listBooks(option);
                break;
            case 2:
                //TODO
                //TODO
                break;
            default:
                clientLogic();
        }
    }


    private void clientMenu() {
        System.out.println("Client Menu");
        System.out.println("1 - Show books");
        System.out.println("2 - Show borrow history");
        System.out.println("3 - Show current borrowed book");
        System.out.println("4 - Borrow book");
        System.out.println("5 - Return book");
        System.out.println("6 - Log out");
        System.out.println("7 - Exit");
    }

    private List<Book> getClientBorrowedBooks(int id) throws SQLException {
        List<Book> borrowedBooks = new ArrayList<>();
        String query = "SELECT * FROM libraryDB.borrowed_book_user WHERE id_user = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, String.valueOf(id));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {

        }
        return borrowedBooks;
    }

//------------------- LIBRARIAN RELATED METHODS ------------------------------------------------------------------------

    private void librarianLogic() {
    }

    private void librarianMenu() {
    }

// ------------------- GENERAL USE METHODS -----------------------------------------------------------------------------

    private void welcomeMenu() {
        System.out.println("Welcome to library!");
        System.out.println("1 - Sign in");
        System.out.println("2 - Sign up");
        System.out.println("Please enter an option!");
    }

    private void bookListingMenu() {
        System.out.println("Listing menu");
        System.out.println("1 - List books A-Z");
        System.out.println("2 - List books Z-A");
        System.out.println("3 - Return");
    }

    private void searchMenu() {
        System.out.println("Search menu");
        System.out.println("1 - Search by title");
        System.out.println("2 - Search by author");
        System.out.println("3 - Search by genre");
        System.out.println("4 - Search by ISBN");
        System.out.println("3 - Return");
    }

    private void doLogin() throws SQLException {
        System.out.println("Please enter username:");
        String username = scanner.next();
        int id = authService.getUserId(connection, username);
        if (id > 0) {
            byte tries = 3;
            do {
                System.out.println("Please enter password");
                String password = scanner.next();
                ResultSet resultSet = authService.getUser(connection, id, password);
                if (authService.getClient(connection, resultSet) != null) {
                    client = authService.getClient(connection, resultSet);
                } else if (authService.getLibrarian(connection, resultSet) != null) {
                    librarian = authService.getLibrarian(connection, resultSet);
                }
                tries--;
            } while (client == null && librarian == null);
        } else {
            System.out.println("Username could not be found! Please try again!");
        }
    }

    private void doWelcomeMenu(int option) throws SQLException {
        switch (option) {
            case 1:
                doLogin();
                break;
            case 2:
                //TODO -- register new models.user
                System.out.println("Work in progress!");
                break;
            default:
                initLibrary();
        }
    }

    private void listBooks(int option) throws SQLException {
        switch (option) {
            case 1:
                createBookAuthorGenre();
                bookList.sort(Comparator.comparing(Book::getBookName));
                for (Book book : bookList) {
                    System.out.print(book.getBookName()+" - ");
                    for (Author author: book.getAuthors()) {
                        System.out.print(author.getFirstName() + author.getLastName()+", ");
                    }
                    for (Genre genre: book.getGenres()){
                        System.out.print(genre+", ");
                    }
                    System.out.println();
                }
                break;
            case 2:
                createBookAuthorGenre();
                bookList.sort((book, t1) -> t1.getBookName().compareTo(book.getBookName()));
                bookList.forEach(System.out::println);
                break;
            case 3:
                //return
                if (client != null) {
                    clientLogic();
                } else {
                    librarianLogic();
                }
                break;
            default:
                //default
        }
    }

    private void createBookAuthorGenre() throws SQLException {
        bookList = bookDao.findAll(connection);
        bookList.forEach(book -> {
            try {
                book.setAuthors(authorDao.findByBookId(connection, book.getId()));
                book.setGenres(genreDao.findGenreByBookId(connection,book.getId()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}