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
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
        printWelcomeMenu();
        option = scanner.nextInt();
        doWelcomeMenu(option);
        do {
            if (client != null) {
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
                getClientBorrowHistory();
                break;
            case 4:
                //SHOW CURRENT BORROWED BOOK
                getCurrentBorrowedBook();
                break;
            case 5:
                //BORROW BOOK
                break;
            case 6:
                //RETURN BORROWED BOOK
                break;
            case 7:
                //LOG OUT
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

    private void getClientBorrowHistory() throws SQLException {
        setBookAuthorGenre();
        String query = "SELECT * FROM libraryDB.borrowed_book_user WHERE id_user = ? AND returned_on IS NOT NULL";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, String.valueOf(client.getId()));
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int bookId = resultSet.getInt("id_book");
            LocalDate borrowDate = resultSet.getDate("borrowed_on").toLocalDate();
            LocalDate returnDate = resultSet.getDate("returned_on").toLocalDate();
            Optional<Book> bookOptional= bookList.stream().filter(book -> book.getId()==bookId).findAny();
            bookOptional.ifPresent(book -> System.out.println(book + " borrowed on " + borrowDate + " returned on " + returnDate));
            System.out.println();
        }
    }

    private Book getCurrentBorrowedBook() throws SQLException{
        setBookAuthorGenre();
        String query = "SELECT * FROM libraryDB.borrowed_book_user WHERE id_user = ? AND returned_on IS NULL";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, String.valueOf(client.getId()));
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int bookId = resultSet.getInt("id_book");
            LocalDate borrowDate = resultSet.getDate("borrowed_on").toLocalDate();
            Optional<Book> bookOptional= bookList.stream().filter(book -> book.getId()==bookId).findAny();
            bookOptional.ifPresent(book -> System.out.println(book + " borrowed on " + borrowDate));
            return bookOptional.orElse(null);
        }
        return null;
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
        setBookAuthorGenre();
        switch (option) {
            case 1:
                bookList.sort(Comparator.comparing(Book::getBookName));
                for (Book book : bookList) {
                    System.out.print(book.getBookName() + " - ");
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

    private List<Book> searchBookByTitle(String title) {
        return bookList.stream().
                filter(book -> book.getBookName()
                        .toLowerCase()
                        .contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    private List<Book> searchBookByAuthor(Author author) {
        return bookList.stream().
                filter(book -> book.getAuthors().contains(author))
                .collect(Collectors.toList());
    }

    private List<Book> searchByGenre(Genre genre) {
        return bookList.stream()
                .filter(book -> book.getGenres().contains(genre))
                .collect(Collectors.toList());
    }

    private Book searchByIsbn(long isbn) {
//        Optional <Book> bookOptional =  bookList.stream().filter(book -> book.getIsbn()==isbn).findAny();
//        if (bookOptional.isPresent()){
//            return bookOptional.get();
//
//        }else {
//            System.out.println("Book not found!");
//            return null;
//        }
        return bookList.stream().filter(book -> book.getIsbn() == isbn).findAny().orElse(null);
    }

    private Author getAuthor(String firstName, String lastName) throws SQLException {
//        Optional<Author> authorOptional = authorDao.findAll(connection).stream()
//                .filter(author -> author.getFirstName().equalsIgnoreCase(firstName) &&
//                        author.getLastName().equalsIgnoreCase(lastName))
//                .findAny();
        return authorDao.findAll(connection).stream()
                .filter(author -> author.getFirstName().equalsIgnoreCase(firstName) &&
                        author.getLastName().equalsIgnoreCase(lastName))
                .findAny()
                .orElse(null);
    }

    private Genre getGenre(String genre) throws SQLException {
//        Optional<Genre> genreOptional = genreDao.findAll(connection).stream()
//                .filter(genreObj -> genreObj.equals(Genre.valueOf(genre)))
//                .findAny();
        return genreDao.findAll(connection).stream()
                .filter(genreObj -> genreObj.equals(Genre.valueOf(genre)))
                .findAny()
                .orElse(null);
    }

    private void doSearchBook()throws SQLException{
        setBookAuthorGenre();
        printSearchMenu();
        int option = scanner.nextInt();
        switch (option){
            case 1:
                //search by title
                System.out.println("Title");
                scanner.skip("\n");
                String title = scanner.nextLine();
                searchBookByTitle(title).forEach(System.out::println);
                break;
            case 2:
                //search by author
                System.out.println("First name");
                String firstName = scanner.nextLine();
                System.out.println("Last name");
                String lastName = scanner.nextLine();
                searchBookByAuthor(getAuthor(firstName,lastName)).forEach(System.out::println);
                break;
            case 3:
                //search by genre
                System.out.println("Genre");
                String genre = scanner.nextLine().replace(" ", "_").toUpperCase();
                searchByGenre(getGenre(genre)).forEach(System.out::println);
                break;
            case 4:
                //search by isbn
                System.out.println("ISBN");
                long isbn = scanner.nextLong();
                System.out.println(searchByIsbn(isbn));
                break;
            default:
                doSearchBook();
        }
    }

    private Book selectBook(List<Book> bookList){
        for (int i = 0; i < bookList.size(); i++) {
            System.out.println((i+1) +" - "+ bookList.get(i));
        }
        System.out.println("Enter choice");
        int choice = scanner.nextInt();
        return bookList.get(choice-1);
    }

}