package services;

import models.book.Book;
import models.book.BorrowedBook;
import models.user.Client;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BorrowReturnService {

    void borrowBook(Book book, Client client, Connection connection) throws SQLException;

    void returnBook(Book book, Client client, Connection connection) throws SQLException;

    BorrowedBook getCurrentBorrowedBook(Client client, List<Book> bookList, Connection connection) throws SQLException;

    List<BorrowedBook> getBorrowHistory(Client client, List<Book> bookList, Connection connection) throws SQLException;
}
