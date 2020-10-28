package services;

import models.book.Book;
import models.book.BorrowedBook;
import models.user.Client;

import java.util.List;

public interface BorrowReturnService {

    void borrowBook(Book book, Client client);

    void returnBook(Book book, Client client);

    BorrowedBook getCurrentBorrowedBook(Client client);

    List<BorrowedBook> getBorrowHistory(Client client);

}
