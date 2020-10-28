package models;

import dao.BookDao;
import dao.ClientDao;
import dao.DbBookDao;
import dao.DbClientDao;
import models.book.Author;
import models.book.Book;
import models.book.BorrowedBook;
import models.book.BorrowedBookByClient;
import models.user.Client;
import models.user.Librarian;
import models.user.User;
import services.AuthService;
import services.AuthServiceImpl;
import services.BorrowReturnService;
import services.BorrowReturnServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Library {
    private Scanner sc = new Scanner(System.in);
    private Client client;
    private Librarian librarian;
    AuthService authService = new AuthServiceImpl();
    BorrowReturnService borrowReturnService = new BorrowReturnServiceImpl();
    BookDao bookDao = new DbBookDao();
    ClientDao clientDao = new DbClientDao();

    public Library() {
    }

    public void initLibrary() {
        do {
            int option;
            printWelcomeMenu();
            option = sc.nextInt();
            doWelcomeMenu(option);
            do {
                //setBookAuthorGenre();
                if (client != null) {
                    setClientProperties();
                    System.out.println("Welcome " + client.getFirstName() + " " + client.getLastName());
                    clientLogic();
                }
                if (librarian != null) {
                    System.out.println("Welcome " + librarian.getFirstName() + " " + librarian.getLastName());
                    librarianLogic();
                }
            } while (client != null || librarian != null);
        } while (true);
    }

//------------------- CLIENT RELATED METHODS ---------------------------------------------------------------------------

    private void clientLogic() {
        //TODO client logic
        try {
            clientMenu();
            int option;
            option = sc.nextInt();
            switch (option) {
                case 1:
                    //SHOW AVAILABLE BOOKS IN LIBRARY
                    bookDao.findAll().forEach(book -> System.out.println(book.getTitle()));
                    break;
                case 2:
                    //SEARCH BOOK
                    doSearchBook();
                    break;
                case 3:
                    //SHOW BORROW HISTORY
                    borrowReturnService.getBorrowHistory(client).forEach(borrowedBook -> System.out.println(borrowedBook.getBook().getTitle() + " on " + borrowedBook.getBorrowedOn()));
                    //TODO
                    break;
                case 4:
                    //SHOW CURRENT BORROWED BOOK
                    BorrowedBook borrowedBook = borrowReturnService.getCurrentBorrowedBook(client);
                    if (borrowedBook != null) {
                        System.out.println(borrowedBook.getBook().getTitle());
                        System.out.println("Borrowed on " + borrowedBook.getBorrowedOn());
                    }
                    //TODO
                    break;
                case 5:
                    //BORROW BOOK
                    if (borrowReturnService.getCurrentBorrowedBook(client) == null) {
                        Book book = selectBook(bookDao.findAll());
                        borrowReturnService.borrowBook(book, client);
                    } else {
                        System.out.println("Please first return current borrowed book");
                    }
                    //TODO
                    break;
                case 6:
                    //TODO
                    BorrowedBook borrowedBook1 = borrowReturnService.getCurrentBorrowedBook(client);
                    if (borrowedBook1 != null) {
                        borrowReturnService.returnBook(borrowedBook1.getBook(), client);
                    } else {
                        System.out.println("no current borrowed books detected");
                    }
                    break;
                case 7:
                    //LOG OUT
                    client = null;
                    break;
                case 8:
                    //EXIT
                    System.exit(0);
                    break;
            }
        } catch (
                InputMismatchException e) {
            System.out.println("Invalid input");
            sc = new Scanner(System.in);
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

    private void setClientProperties() {
        client.setBorrowedBooks(borrowReturnService.getBorrowHistory(client));
        client.setCurrentBorrowedBook(borrowReturnService.getCurrentBorrowedBook(client));
    }

//------------------- LIBRARIAN RELATED METHODS ------------------------------------------------------------------------

    private void librarianLogic() {
        //TODO librarian logic

        try {
            int option;
            librarianMenu();
            option = sc.nextInt();
            switch (option) {
                case 1:
                    //SHOW AVAILABLE BOOKS IN LIBRARY
                    listBooks();
                    break;
                case 2:
                    clientDao.findAll().forEach(client -> System.out.println(client.getFirstName() + " " + client.getLastName()));
                    break;
                case 3:
                    for (BorrowedBookByClient borrowedBookByClient : currentBorrowedBooks()) {
                        System.out.println(borrowedBookByClient.getBook().getTitle() + "borrowed by " + borrowedBookByClient.getClient().getUser());
                    }
                    //TODO
                    break;
                case 4:
                    for (BorrowedBookByClient borrowedBookByClient : borrowHistoryAllClients()) {
                        System.out.println(borrowedBookByClient.getBook().getTitle() +
                                " borrowed by " + borrowedBookByClient.getClient().getUser() +
                                " on " + borrowedBookByClient.getBorrowedOn() + " returned on " +
                                borrowedBookByClient.getReturnedOn());
                    }
                    //TODO
                    break;
                case 5:
                    addNewBook();
                    break;
                case 6:
                    //edit book
                    break;
                case 7:
                    librarian = null;
                    break;
                case 8:
                    System.exit(0);
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input");
            sc = new Scanner(System.in);
            librarianLogic();
        }

    }

    private void librarianMenu() {
        System.out.println("Librarian Menu");
        System.out.println("1 - Display books");
        System.out.println("2 - Display clients");
        System.out.println("3 - Display current borrowed books");
        System.out.println("4 - Display borrow history");
        System.out.println("5 - Add new book");
        System.out.println("6 - Edit book");
        System.out.println("7 - Log out");
        System.out.println("8 - Exit");
    }

    private List<BorrowedBookByClient> currentBorrowedBooks() {
        List<BorrowedBookByClient> currentBorrowedBooks = new ArrayList<>();
        for (Client client : clientDao.findAll()) {
            if (borrowReturnService.getCurrentBorrowedBook(client) != null) {
                currentBorrowedBooks.add(new BorrowedBookByClient(borrowReturnService.getCurrentBorrowedBook(client).getBook(),
                        borrowReturnService.getCurrentBorrowedBook(client).getBorrowedOn(), client));
            }
        }
        return currentBorrowedBooks;
    }

    private List<BorrowedBookByClient> borrowHistoryAllClients() {
        List<BorrowedBookByClient> borrowedBookByClients = new ArrayList<>();
        for (Client client : clientDao.findAll()) {
            if (borrowReturnService.getBorrowHistory(client).size() > 0) {
                for (BorrowedBook borrowedBook : borrowReturnService.getBorrowHistory(client)) {
                    borrowedBookByClients.add(new BorrowedBookByClient(borrowedBook.getBook(), borrowedBook.getBorrowedOn(), borrowedBook.getReturnedOn(), client));
                }
            }
        }
        return borrowedBookByClients;
    }

    public void addNewBook() {
        List<Author> authorList = new ArrayList<>();
        List<String> genreList = new ArrayList<>();
        boolean continueAdding;
        do {
            System.out.println("First name");
            String firstName = sc.next();
            System.out.println("Last name");
            String lastName = sc.next();
            System.out.println("Description");
            String description = sc.next();
            System.out.println("Birth Date");
            System.out.println("Year");
            int year = sc.nextInt();
            System.out.println("Month");
            int month = sc.nextInt();
            System.out.println("Day");
            int day = sc.nextInt();
            LocalDate birthDate = LocalDate.of(year, month, day);
            authorList.add(new Author(firstName, lastName, description, birthDate));
            sc.skip("\n");
            System.out.println("Enter genre");
            String genre = sc.nextLine();
            genreList.add(genre);
            System.out.println("Continue? Y/N");
            String answer = sc.next().trim();
            sc.skip("\n");
            continueAdding = answer.equalsIgnoreCase("y");
        } while (continueAdding);
        System.out.println("Enter book title");
        String title = sc.nextLine();
        System.out.println("Enter isbn");
        long isbn = sc.nextLong();
        sc.skip("\n");
        System.out.println("Enter release date");
        System.out.println("Year");
        int year = sc.nextInt();
        System.out.println("Month");
        int month = sc.nextInt();
        System.out.println("Day");
        int day = sc.nextInt();
        LocalDate releaseDate = LocalDate.of(year, month, day);
        System.out.println("Enter stock");
        int stock = sc.nextInt();
        Book newBook = new Book(title, authorList, genreList, isbn, releaseDate, stock);
        DbBookDao dbBookDao = new DbBookDao();
        dbBookDao.create(newBook);
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

    private void doLogin() {
        System.out.println("Please enter username:");
        String username = sc.next();
        int userId = authService.getUserId(username);
        if (userId > 0) {
            int tries = 3;
            do {
                System.out.println("Enter password");
                System.out.println(tries + " tries remaining!");
                String password = sc.next();
                User user = authService.getUser(userId, password);
                if (user instanceof Client) {
                    client = (Client) user;
                } else if (user instanceof Librarian) {
                    librarian = (Librarian) user;
                } else {
                    tries--;
                }
            } while (tries > 0 && client == null && librarian == null);
            if (tries == 0) {
                System.out.println("Password entered wrong too many times!");
            }
        }
    }

    private void doWelcomeMenu(int option) {
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

    private void listBooks() {
        try {
            printBookListingMenu();
            System.out.println("enter option");
            int option = sc.nextInt();
            switch (option) {
                case 1:
                    List<Book> booksAZ = new ArrayList<>(bookDao.findAll());
                    booksAZ.sort((book, t1) -> book.getTitle().compareTo(t1.getTitle()));
                    booksAZ.forEach(book -> System.out.println(book.getTitle()));
                    break;
                case 2:
                    List<Book> booksZA = new ArrayList<>(bookDao.findAll());
                    booksZA.sort((book, t1) -> t1.getTitle().compareTo(book.getTitle()));
                    booksZA.forEach(book -> System.out.println(book.getTitle()));
                    break;
                case 3:
                    //return
                    if (client != null) {
                        clientLogic();
                    } else {
                        librarianLogic();
                    }
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input");
            sc = new Scanner(System.in);
            listBooks();
        }
    }


    private void doSearchBook() {
        printSearchMenu();
        int option = sc.nextInt();
        switch (option) {
            case 1:
                //search by title
                System.out.println("Title");
                sc.skip("\n");
                String title = sc.nextLine();
                bookDao.findByTitle(title).forEach(book -> System.out.println(book.getTitle()));
                break;
            case 2:
                //search by author
                System.out.println("First name");
                String firstName = sc.nextLine();
                System.out.println("Last name");
                String lastName = sc.nextLine();
                break;
            case 3:
                //search by genre
                System.out.println("Genre");
                sc.skip("\n");
                String genre = sc.nextLine();
                bookDao.findByGenre(genre).forEach(book -> System.out.println(book.getTitle()));
                break;
            case 4:
                //search by isbn
                System.out.println("ISBN");
                long isbn = sc.nextLong();
                System.out.println(bookDao.findByIsbn(isbn).getTitle());
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
        int choice = sc.nextInt();
        return bookList.get(choice - 1);
    }


}