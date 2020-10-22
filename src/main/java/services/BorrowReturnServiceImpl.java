package services;

import models.book.Book;
import models.book.BorrowedBook;
import models.user.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class BorrowReturnServiceImpl implements BorrowReturnService {

    @Override
    public void borrowBook(Book book, Connection connection, Client client) throws SQLException {
        String borrowBookQuery = "INSERT into libraryDB.borrowed_book_user(borrowed_on, id_user, id_book) VALUES (CURDATE(),?,?);";
        PreparedStatement pStmtBorrowBook = connection.prepareStatement(borrowBookQuery);
        pStmtBorrowBook.setString(1, String.valueOf(client.getId()));
        pStmtBorrowBook.setString(2, String.valueOf(book.getId()));
        pStmtBorrowBook.executeUpdate();

        String updateBooks = "UPDATE libraryDB.books\n" +
                "SET stock = (stock-1)\n" +
                "WHERE id=?;";
        PreparedStatement pStmtUpdateBook = connection.prepareStatement(updateBooks);
        pStmtUpdateBook.setString(1, String.valueOf(book.getId()));
        pStmtUpdateBook.executeUpdate();
    }

    @Override
    public void returnBook(Book book, Connection connection, Client client) throws SQLException {
        String returnBookQuery = "UPDATE libraryDB.borrowed_book_user\n" +
                "SET returned_on=CURDATE()\n" +
                "WHERE id_user = ? AND id_book = ?;";
        PreparedStatement pStmtReturnBook = connection.prepareStatement(returnBookQuery);
        pStmtReturnBook.setString(1, String.valueOf(client.getId()));
        pStmtReturnBook.setString(2, String.valueOf(book.getId()));
        pStmtReturnBook.executeUpdate();

        String updateBooks = "UPDATE libraryDB.books\n" +
                "SET stock = (stock+1)\n" +
                "WHERE id=?;";
        PreparedStatement pStmtUpdateBook = connection.prepareStatement(updateBooks);
        pStmtUpdateBook.setString(1, String.valueOf(book.getId()));
        pStmtUpdateBook.executeUpdate();
    }

    @Override
    public Book getCurrentBorrowedBook(Client client, List<Book> bookList, Connection connection) throws SQLException {
        String query = "SELECT * FROM libraryDB.borrowed_book_user WHERE id_user = ? AND returned_on IS NULL";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, String.valueOf(client.getId()));
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int bookId = resultSet.getInt("id_book");
            LocalDate borrowDate = resultSet.getDate("borrowed_on").toLocalDate();
            Optional<Book> bookOptional = bookList.stream().filter(book -> book.getId() == bookId).findAny();
            bookOptional.ifPresent(book -> System.out.println(book + " borrowed on " + borrowDate));
            return bookOptional.orElse(null);
        }
        return null;
    }

    @Override
    public List<BorrowedBook> getBorrowHistory(Client clientId, Connection connection) throws SQLException {
        return null;
    }
}
