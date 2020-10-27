package services;

import models.book.Book;
import models.book.BorrowedBook;
import models.user.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BorrowReturnServiceImpl implements BorrowReturnService {

    @Override
    public void borrowBook(Book book, Client client, Connection connection) throws SQLException {
        String borrowBookQuery = "INSERT into libraryDB.borrowed_book_user(borrowed_on, id_user, id_book) VALUES (CURDATE(),?,?);";
        PreparedStatement pStmtBorrowBook = connection.prepareStatement(borrowBookQuery);
        pStmtBorrowBook.setString(1, String.valueOf(client.getId()));
        pStmtBorrowBook.setString(2, String.valueOf(book.getId()));
        pStmtBorrowBook.executeUpdate();

        String updateBooks = "UPDATE libraryDB.books SET stock = (stock-1) WHERE id=?;";
        PreparedStatement pStmtUpdateBook = connection.prepareStatement(updateBooks);
        pStmtUpdateBook.setString(1, String.valueOf(book.getId()));
        pStmtUpdateBook.executeUpdate();
    }

    @Override
    public void returnBook(Book book, Client client, Connection connection) throws SQLException {
        if (client.getCurrentBorrowedBook() != null) {
            String returnBookQuery = "UPDATE libraryDB.borrowed_book_user " +
                    "SET returned_on=CURDATE() WHERE id_user = ? AND id_book = ?;";
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
    }

    @Override
    public BorrowedBook getCurrentBorrowedBook(Client client, List<Book> bookList, Connection connection) throws SQLException {
        String query = "SELECT * FROM libraryDB.borrowed_book_user WHERE id_user = ? AND returned_on IS NULL";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, String.valueOf(client.getId()));
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int bookId = resultSet.getInt("id_book");
            LocalDate borrowDate = resultSet.getDate("borrowed_on").toLocalDate();
            Optional<Book> bookOptional = bookList.stream().filter(book -> book.getId() == bookId).findAny();
            if (bookOptional.isPresent())
                return new BorrowedBook(bookOptional.get(), borrowDate);
        }
        return null;
    }

    @Override
    public List<BorrowedBook> getBorrowHistory(Client client, List<Book> bookList, Connection connection) throws SQLException {
        List<BorrowedBook> borrowedBookList = new ArrayList<>();
        String query = "SELECT * FROM libraryDB.borrowed_book_user WHERE id_user = ? AND returned_on IS NOT NULL";
        PreparedStatement pStmtGetBorrowHistory = connection.prepareStatement(query);
        pStmtGetBorrowHistory.setString(1, String.valueOf(client.getId()));
        ResultSet rSetHistory = pStmtGetBorrowHistory.executeQuery();
        while (rSetHistory.next()) {
            int bookId = rSetHistory.getInt("id_book");
            Optional<Book> bookOptional = bookList.stream().filter(book -> book.getId() == bookId).findAny();
            LocalDate borrowDate = rSetHistory.getDate("borrowed_on").toLocalDate();
            LocalDate returnDate = rSetHistory.getDate("returned_on").toLocalDate();
            bookOptional.ifPresent(book -> borrowedBookList.add(new BorrowedBook(book, borrowDate, returnDate)));
        }
        return borrowedBookList;
    }
}
