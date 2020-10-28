package services;

import dao.DbBookDao;
import database.GetConnection;
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

public class BorrowReturnServiceImpl implements BorrowReturnService {

    Connection connection = new GetConnection().getConnection();

    @Override
    public void borrowBook(Book book, Client client) {
        try {
            String borrowBookQuery = "INSERT into libraryDB.borrowed_book_user(borrowed_on, id_user, id_book) VALUES (CURDATE(),?,?);";
            PreparedStatement pStmtBorrowBook = connection.prepareStatement(borrowBookQuery);
            pStmtBorrowBook.setString(1, String.valueOf(client.getId()));
            pStmtBorrowBook.setString(2, String.valueOf(book.getId()));
            pStmtBorrowBook.executeUpdate();
            String updateBooks = "UPDATE libraryDB.books SET stock = (stock-1) WHERE id=?;";
            PreparedStatement pStmtUpdateBook = connection.prepareStatement(updateBooks);
            pStmtUpdateBook.setString(1, String.valueOf(book.getId()));
            pStmtUpdateBook.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void returnBook(Book book, Client client) {
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BorrowedBook getCurrentBorrowedBook(Client client) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM libraryDB.borrowed_book_user WHERE id_user = ? AND returned_on IS NULL");
            preparedStatement.setString(1, String.valueOf(client.getId()));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Book book = new DbBookDao().findById(resultSet.getInt("id_book"));
                LocalDate borrowDate = resultSet.getDate("borrowed_on").toLocalDate();
                return new BorrowedBook(book, borrowDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BorrowedBook> getBorrowHistory(Client client) {
        List<BorrowedBook> borrowedBookList = new ArrayList<>();
        try {
            String query = "SELECT * FROM libraryDB.borrowed_book_user WHERE id_user = ? AND returned_on IS NOT NULL";
            PreparedStatement pStmtGetBorrowHistory = connection.prepareStatement(query);
            pStmtGetBorrowHistory.setString(1, String.valueOf(client.getId()));
            ResultSet rSetHistory = pStmtGetBorrowHistory.executeQuery();
            while (rSetHistory.next()) {
                Book book = new DbBookDao().findById(rSetHistory.getInt("id_book"));
                LocalDate borrowDate = rSetHistory.getDate("borrowed_on").toLocalDate();
                LocalDate returnDate = rSetHistory.getDate("returned_on").toLocalDate();
                borrowedBookList.add(new BorrowedBook(book, borrowDate, returnDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrowedBookList;
    }
}
