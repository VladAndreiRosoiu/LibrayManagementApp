package models;

import dao.*;
import models.book.*;
import models.user.Client;
import models.user.Librarian;
import services.*;

import java.sql.*;
import java.util.*;

public class Library {
    private Scanner scanner = new Scanner(System.in);
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
    ClientDao clientDao = new DbClientDao();

    public Library(Connection connection) {
        this.connection = connection;
    }

    public void initLibrary() throws SQLException {
        do {
            int option;
            printWelcomeMenu();
            option = scanner.nextInt();
            doWelcomeMenu(option);
            do {
                setBookAuthorGenre();
                if (client != null) {
                    setClientProperties();
                    clientLogic();
                }
                if (librarian != null) {
                    librarianLogic();
                }
            } while (client != null || librarian != null);
        } while (true);
    }

//------------------- CLIENT RELATED METHODS ---------------------------------------------------------------------------

    private void clientLogic() throws SQLException {
        //TODO client logic
        try {
            clientMenu();
            int option;
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    //SHOW AVAILABLE BOOKS IN LIBRARY
//                    listBooks();
                    findAll();
                    break;
                case 2:
                    //SEARCH BOOK
                    doSearchBook();
                    break;
                case 3:
                    //SHOW BORROW HISTORY
                    if (client.getBorrowedBooks().size() > 0) {
                        client.getBorrowedBooks()
                                .forEach(borrowedBook -> System.out.println(borrowedBook.getBook().getBookName() +
                                        " borrowed on " + borrowedBook.getBorrowedOn()));
                    } else {
                        System.out.println("You haven't borrowed any books yet!");
                    }
                    break;
                case 4:
                    //SHOW CURRENT BORROWED BOOK
                    if (client.getCurrentBorrowedBook() != null) {
                        System.out.println(client.getCurrentBorrowedBook().getBook().getBookName() +
                                " borrowed on " + client.getCurrentBorrowedBook().getBorrowedOn());
                    } else {
                        System.out.println("You don't have a current borrowed book!");
                    }

                    break;
                case 5:
                    //BORROW BOOK
                    if (client.getCurrentBorrowedBook() == null) {
                        System.out.println("ISBN");
                        long isbn = scanner.nextLong();
                        borrowReturnService.borrowBook(searchService.searchByIsbn(isbn, bookList), client, connection);
                    } else {
                        System.out.println("please first return your borrowed book");
                        System.out.println(client.getCurrentBorrowedBook().getBook().getBookName());
                    }
                    break;
                case 6:
                    //RETURN BORROWED BOOK
                    borrowReturnService.returnBook(borrowReturnService
                            .getCurrentBorrowedBook(client, bookList, connection).getBook(), client, connection);
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
        } catch (InputMismatchException e) {
            System.out.println("Invalid input");
            scanner = new Scanner(System.in);
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

    private void setClientProperties() throws SQLException {
        client.setBorrowedBooks(borrowReturnService.getBorrowHistory(client, bookList, connection));
        client.setCurrentBorrowedBook(borrowReturnService.getCurrentBorrowedBook(client, bookList, connection));
    }

//------------------- LIBRARIAN RELATED METHODS ------------------------------------------------------------------------

    private void librarianLogic() throws SQLException {
        //TODO librarian logic

        try {
            int option;
            librarianMenu();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    //SHOW AVAILABLE BOOKS IN LIBRARY
                    listBooks();
                    break;
                case 2:
                    clientDao.findAll(connection).forEach(client -> System.out.println(client.getFirstName() + " " + client.getLastName()));
                    break;
                case 3:
                    currentBorrowedBooks().forEach(borrowedBookByClient -> System.out.println(borrowedBookByClient.getBook().getBookName() + " borrowed by " +
                            borrowedBookByClient.getClient().getFirstName() + " " + borrowedBookByClient.getClient().getLastName() + " on " + borrowedBookByClient.getBorrowedOn()));
                    break;
                case 4:
                    borrowHistory().forEach(borrowedBookByClient -> System.out.println(borrowedBookByClient.getBook().getBookName() + " borrowed by " +
                            borrowedBookByClient.getClient().getFirstName() + " " + borrowedBookByClient.getClient().getLastName() +
                            " borrowed on " + borrowedBookByClient.getBorrowedOn() + " returned on " + borrowedBookByClient.getReturnedOn()));
                    break;
                case 5:
                    //add new book
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
            scanner = new Scanner(System.in);
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

    private List<BorrowedBookByClient> currentBorrowedBooks() throws SQLException {
        List<BorrowedBookByClient> currentBorrowedBooks = new ArrayList<>();
        for (Client client : clientDao.findAll(connection)) {
            if (borrowReturnService.getCurrentBorrowedBook(client, bookList, connection) != null) {
                currentBorrowedBooks.add(new BorrowedBookByClient(borrowReturnService.getCurrentBorrowedBook(client, bookList, connection).getBook(),
                        borrowReturnService.getCurrentBorrowedBook(client, bookList, connection).getBorrowedOn(), client));
            }
        }
        return currentBorrowedBooks;
    }

    private List<BorrowedBookByClient> borrowHistory() throws SQLException {
        List<BorrowedBookByClient> borrowedBookByClients = new ArrayList<>();
        for (Client client : clientDao.findAll(connection)) {
            if (borrowReturnService.getBorrowHistory(client, bookList, connection).size() > 0) {
                for (BorrowedBook borrowedBook : borrowReturnService.getBorrowHistory(client, bookList, connection)) {
                    borrowedBookByClients.add(new BorrowedBookByClient(borrowedBook.getBook(), borrowedBook.getBorrowedOn(), borrowedBook.getReturnedOn(), client));
                }
            }
        }
        return borrowedBookByClients;
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

    private void listBooks() throws SQLException {
        try {
            printBookListingMenu();
            System.out.println("enter option");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    bookList.sort(Comparator.comparing(Book::getBookName));
                    for (Book book : bookList) {
                        System.out.print(book.getBookName() + " - ");
                        System.out.print(" - stock : " + book.getStock() + " ");
                        System.out.print(" ISBN - " + book.getIsbn() + " - ");
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
                    for (Book book : bookList) {
                        System.out.print(book.getBookName() + " - ");
                        System.out.print(" - stock : " + book.getStock() + " ");
                        System.out.print(" ISBN - " + book.getIsbn() + " - ");
                        for (Author author : book.getAuthors()) {
                            System.out.print(author.getFirstName() + " " + author.getLastName() + " - ");
                        }
                        for (Genre genre : book.getGenres()) {
                            System.out.print(genre + ", ");
                        }
                        System.out.println();
                    }
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
            scanner = new Scanner(System.in);
            listBooks();
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
                bookDao.findByTitle(connection,title).forEach(book -> System.out.println(book.getBookName()));
                //searchService.searchByTitle(title, bookList).forEach(System.out::println);
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
//                searchService.searchByIsbn(isbn, bookList);
                System.out.println(bookDao.findByIsbn(connection,isbn).getBookName());
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

    private void findAll() throws SQLException{
        List<Book> books = new ArrayList<>();
        List<Author> authors = new ArrayList<>();
        Statement preparedStatement = connection.createStatement();
        ResultSet resultSet = preparedStatement.executeQuery("SELECT libraryDB.authors.first_name, libraryDB.authors.last_name, libraryDB.books.book_name\n" +
                "FROM libraryDB.authors\n" +
                "JOIN libraryDB.book_author\n" +
                "ON libraryDB.book_author.id_author = libraryDB.authors.id\n" +
                "JOIN libraryDB.books\n" +
                "ON libraryDB.book_author.id_book = libraryDB.books.id;");
        while (resultSet.next()){
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String bookName = resultSet.getString("book_name");
            authors.add(new Author(firstName,lastName));
            books.add(new Book(firstName,authors));
            if (books.size()>0){
                for (Book book: books){
                    if (book.getBookName().equals(bookName)){
                        authors.add(new Author(firstName,lastName));
                        book.setAuthors(authors);
                        books.add(book);
                    }else {
                        authors.add(new Author(firstName,lastName));
                        books.add(new Book(bookName, authors));
                    }
                }
            }
        }
        books.forEach(book -> System.out.println(book.getBookName()));
    }

}