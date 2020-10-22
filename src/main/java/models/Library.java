package models;

import dao.*;
import models.book.Author;
import models.book.Book;
import models.book.Genre;
import models.user.Client;
import models.user.Librarian;
import services.*;

import java.sql.Connection;
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
    SearchService searchService = new SearchServiceImpl();
    BorrowReturnService borrowReturnService = new BorrowReturnServiceImpl();
    AuthorDao authorDao = new DbAuthorDao();
    BookDao bookDao = new DbBookDao();
    GenreDao genreDao = new DbGenreDao();

    public Library(Connection connection) {
        this.connection = connection;
    }

    public void initLibrary() throws SQLException {
        int option;
        printWelcomeMenu();
        option = scanner.nextInt();
        doWelcomeMenu(option);
        do {
            if (client != null) {
                setClientProperties();
                clientLogic();
            }
            if (librarian != null) {
                librarianLogic();
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
                //SHOW AVAILABLE BOOKS IN LIBRARY
                printBookListingMenu();
                System.out.println("enter option");
                option = scanner.nextInt();
                listBooks(option);
                break;
            case 2:
                //SEARCH BOOK
                doSearchBook();
                break;
            case 3:
                //SHOW BORROW HISTORY
                client.getBorrowedBooks()
                        .forEach(borrowedBook -> System.out.println(borrowedBook.getBook().getBookName() +
                                " borrowed on "+ borrowedBook.getBorrowedOn()));
                break;
            case 4:
                //SHOW CURRENT BORROWED BOOK
                System.out.println(client.getCurrentBorrowedBook().getBook().getBookName() +
                        " borrowed on " + client.getCurrentBorrowedBook().getBorrowedOn());
                break;
            case 5:
                //BORROW BOOK
                if (client.getCurrentBorrowedBook() == null){
                    System.out.println("ISBN");
                    long isbn = scanner.nextLong();
                    borrowReturnService.borrowBook(searchService.searchByIsbn(isbn, bookList),client, connection);
                }else {
                    System.out.println("please first return your borrowed book");
                    System.out.println(client.getCurrentBorrowedBook().getBook().getBookName());
                }
                break;
            case 6:
                //RETURN BORROWED BOOK
                borrowReturnService.returnBook(borrowReturnService
                        .getCurrentBorrowedBook(client,bookList,connection).getBook(),client,connection);
                break;
            case 7:
                //LOG OUT
                client = null;
                break;
            case 8:
                //EXIT
                System.exit(0);
                break;
            default:
                clientLogic();
        }
    }

    private void clientMenu() {
        System.out.println("Client Menu");
        System.out.println("1 - Show books");
        System.out.println("2 - Search book");
        System.out.println("3 - Show borrow history");
        System.out.println("4 - Show current borrowed book");
        System.out.println("5 - Borrow book");
        System.out.println("6 - Return book");
        System.out.println("7 - Log out");
        System.out.println("8 - Exit");
    }

    private void setClientProperties() throws SQLException{
        setBookAuthorGenre();
        client.setBorrowedBooks(borrowReturnService.getBorrowHistory(client,bookList,connection));
        client.setCurrentBorrowedBook(borrowReturnService.getCurrentBorrowedBook(client,bookList,connection));
    }

//------------------- LIBRARIAN RELATED METHODS ------------------------------------------------------------------------

    private void librarianLogic() {
        //TODO librarian logic
        int option;
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

    private void librarianMenu() {
    }

// ------------------- GENERAL USE METHODS -----------------------------------------------------------------------------

    private void printWelcomeMenu() {
        System.out.println("Welcome to library!");
        System.out.println("1 - Sign in");
        System.out.println("2 - Sign up");
        System.out.println("Please enter an option!");
    }

    private void printBookListingMenu() {
        System.out.println("Listing menu");
        System.out.println("1 - List books A-Z");
        System.out.println("2 - List books Z-A");
        System.out.println("3 - Return");
    }

    private void printSearchMenu() {
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
                if (tries == 0) {
                    System.out.println("Exceeded maximum tries number!");
                    break;
                }
            } while (client == null && librarian == null && tries > 0);
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
                //TODO -- register new client
                System.out.println("Work in progress!");
                break;
            default:
                initLibrary();
        }
    }

    private void listBooks(int option) throws SQLException {
        switch (option) {
            case 1:
                bookList.sort(Comparator.comparing(Book::getBookName));
                for (Book book : bookList) {
                    System.out.print(book.getBookName() + " - ");
                    System.out.println(" - stock : " + book.getStock() + " ");
                    System.out.println(" ISBN - " + book.getIsbn() + " - ");
                    for (Author author : book.getAuthors()) {
                        System.out.print(author.getFirstName() + " " + author.getLastName() + " - ");
                    }
                    for (Genre genre : book.getGenres()) {
                        System.out.print(genre + ", ");
                    }
                    System.out.println();
                }
                break;
            case 2:
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

    private void setBookAuthorGenre() throws SQLException {
        bookList = bookDao.findAll(connection);
        bookList.forEach(book -> {
            try {
                book.setAuthors(authorDao.findByBookId(connection, book.getId()));
                book.setGenres(genreDao.findGenreByBookId(connection, book.getId()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void doSearchBook() throws SQLException {
        printSearchMenu();
        int option = scanner.nextInt();
        switch (option) {
            case 1:
                //search by title
                System.out.println("Title");
                scanner.skip("\n");
                String title = scanner.nextLine();
                searchService.searchByTitle(title, bookList).forEach(System.out::println);
                break;
            case 2:
                //search by author
                System.out.println("First name");
                String firstName = scanner.nextLine();
                System.out.println("Last name");
                String lastName = scanner.nextLine();
                searchService.searchByAuthor(searchService.getAuthor(firstName, lastName, authorDao.findAll(connection)), bookList)
                        .forEach(System.out::println);
                break;
            case 3:
                //search by genre
                System.out.println("Genre");
                scanner.skip("\n");
                String genre = scanner.nextLine().replace(" ", "_").toUpperCase();
                searchService.searchByGenre(searchService.getGenre(genre, genreDao.findAll(connection)), bookList).forEach(System.out::println);
                break;
            case 4:
                //search by isbn
                System.out.println("ISBN");
                long isbn = scanner.nextLong();
                searchService.searchByIsbn(isbn, bookList);
                break;
            default:
                doSearchBook();
        }
    }

    private Book selectBook(List<Book> bookList) {
        for (int i = 0; i < bookList.size(); i++) {
            System.out.println((i + 1) + " - " + bookList.get(i));
        }
        System.out.println("Enter choice");
        int choice = scanner.nextInt();
        return bookList.get(choice - 1);
    }

}