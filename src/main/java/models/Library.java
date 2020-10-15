package models;

import models.user.Client;
import models.user.Librarian;
import models.user.User;
import models.user.UserType;
import services.AuthService;
import services.AuthServiceImpl;

import java.sql.Connection;
import java.util.Scanner;

public class Library {
    private final Scanner scanner = new Scanner(System.in);
    private final Connection connection;
    private Client client;
    private Librarian librarian;
    AuthService authService = new AuthServiceImpl();

    public Library(Connection connection) {
        this.connection = connection;
    }

    public void initLibrary() {
        do {
            int option;
            welcomeMenu();
            option = scanner.nextInt();
            doWelcomeMenu(option);
        } while (client != null || librarian != null);
    }

    private User doLogin() {
        System.out.println("Please enter username:");
        String username = scanner.next();
        int id = authService.getUserId(connection, username);
        if (id > 0) {
            byte tries = 3;
            User user;
            do {
                System.out.println("Please enter password");
                String password = scanner.next();
                tries--;
                user = authService.getUser(connection, id, authService.getPassword(password));
                if (tries == 0) {
                    System.out.println("Exceeded maxim tries number!");
                }
            } while (tries > 0 && user != null);
            return user;
        } else {
            System.out.println("Something went wrong..");
            System.out.println("Username could not be found! Please try again!");
        }
        return null;
    }

    private void setLoggedInUser(User user) {
        if (user != null) {
            if (user.getUserType() == UserType.CLIENT) {
                client = (Client) user;
            } else if (user.getUserType() == UserType.LIBRARIAN) {
                librarian = (Librarian) user;
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

    private void doWelcomeMenu(int option) {
        switch (option) {
            case 1:
                setLoggedInUser(doLogin());
                break;
            case 2:
                //TODO -- register new user
                System.out.println("Work in progress!");
                break;
            default:
                initLibrary();
        }
    }
}
