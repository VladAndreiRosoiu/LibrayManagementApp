package models;

import dao.AuthorDao;
import dao.BookDao;
import dao.DbAuthorDao;
import dao.DbBookDao;
import models.book.Book;
import models.user.Client;
import models.user.Librarian;
import services.AuthService;
import services.AuthServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Library {
    private final Scanner scanner = new Scanner(System.in);
    private final Connection connection;
    private Client client;
    private Librarian librarian;
    AuthService authService = new AuthServiceImpl();
    AuthorDao authorDao = new DbAuthorDao();
    BookDao bookDao = new DbBookDao();

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
                //TODO client logic
                clientMenu();
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
                        clientMenu();
                }
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


//------------------- CLIENT MENUS && SUBMENUS -------------------------------------------------------------------------

    private void clientMenu() {
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

//------------------- LIBRARIAN MENUS && SUBMENUS ----------------------------------------------------------------------

    private void librarianMenu() {
    }

//------------------- PRINT METHODS ------------------------------------------------------------------------------------

    private void welcomeMenu() {
        System.out.println("Welcome to library!");
        System.out.println("1 - Sign in");
        System.out.println("2 - Sign up");
        System.out.println("Please enter an option!");
    }

    private void bookListingMenu(){
        System.out.println("Listing menu");
        System.out.println("1 - List books A-Z");
        System.out.println("2 - List books Z-A");
        System.out.println("3 - Return");
    }

    private void searchMenu(){
        System.out.println("Search menu");
        System.out.println("1 - Search by title");
        System.out.println("2 - Search by author");
        System.out.println("3 - Search by genre");
        System.out.println("4 - Search by ISBN");
        System.out.println("3 - Return");
    }

//------------------- CLIENT RELATED METHODS ---------------------------------------------------------------------------


//------------------- LIBRARIAN RELATED METHODS ------------------------------------------------------------------------


// ------------------- GENERAL USE METHODS -----------------------------------------------------------------------------

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
                    break;
                } else if (authService.getLibrarian(connection, resultSet) != null) {
                    librarian = authService.getLibrarian(connection, resultSet);
                    break;
                }
                tries--;
            } while (tries > 0 && client == null || librarian == null);
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

}