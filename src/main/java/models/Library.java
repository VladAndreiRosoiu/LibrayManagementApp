package models;

import dao.AuthorDao;
import dao.BookDao;
import dao.DbAuthorDao;
import dao.DbBookDao;
import models.book.Book;
import models.user.Client;
import models.user.Librarian;
import models.user.UserType;
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
        do {
            int option;
            welcomeMenu();
            option = scanner.nextInt();
            doWelcomeMenu(option);
        } while (client != null || librarian != null);
    }

    private String doLogin() throws SQLException {
        System.out.println("Please enter username:");
        String username = scanner.next();
        int id = authService.getUserId(connection, username);
        if (id > 0) {
            byte tries = 3;
            StringBuilder s= new StringBuilder();
            do {
                System.out.println("Please enter password");
                String password = scanner.next();
                tries--;
                s.append(authService.getUserDetails(connection, id, authService.getPassword(password)));
            } while (tries > 0 && s.equals(null));
            System.out.println(s);
            return s.toString();
        } else {
            System.out.println("Something went wrong..");
            System.out.println("Username could not be found! Please try again!");
        }
        return null;
    }

    private void setLoggedInUser(String userDetails) {
        if (userDetails != null){
            String [] arrayDetails = userDetails.split(",");
            if (arrayDetails[5].equals(UserType.CLIENT.toString())){
                //client = new Client(arrayDetails[0],arrayDetails[1], arrayDetails[2], arrayDetails[3], arrayDetails[4], );
            }else if (arrayDetails[5].equals(UserType.LIBRARIAN.toString())){
                librarian = new Librarian(Integer.parseInt(arrayDetails[0]),arrayDetails[1], arrayDetails[2], arrayDetails[3], arrayDetails[4]);
            }
        } else {
            System.out.println("Please login first!");
        }

    }

    private void welcomeMenu() {
        System.out.println("Welcome to library!");
        System.out.println("1 - Sign in");
        System.out.println("2 - Sign up");
        System.out.println("Please enter an option!");
    }

    private void doWelcomeMenu(int option) throws SQLException {
        switch (option) {
            case 1:
                setLoggedInUser(doLogin());
                break;
            case 2:
                //TODO -- register new models.user
                System.out.println("Work in progress!");
                break;
            default:
                initLibrary();
        }
    }

    private List<Book> getClientBorrowedBooks(int id) throws SQLException{
        List<Book> borrowedBooks = new ArrayList<>();
        String query = "SELECT * FROM libraryDB.borrowed_book_user WHERE id_user = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, String.valueOf(id));
        ResultSet rs = ps.executeQuery();
        while (rs.next()){

        }
        return borrowedBooks;
    }
}
