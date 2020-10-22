package models.user;

import models.book.Book;
import models.book.BorrowedBook;

import java.util.List;

public class Client extends User {

    private List<BorrowedBook> borrowedBooks;
    private Book currentBorrowedBook;
    private boolean isActive;

    public Client(int id, String firstName, String lastName, String user, String email, List<BorrowedBook> borrowedBooks, Book currentBorrowedBook, boolean isActive) {
        super(id, firstName, lastName, user, email, UserType.CLIENT);
        this.borrowedBooks = borrowedBooks;
        this.currentBorrowedBook = currentBorrowedBook;
        this.isActive = isActive;
    }

    public Book getCurrentBorrowedBook() {
        return currentBorrowedBook;
    }

    public void setCurrentBorrowedBook(Book currentBorrowedBook) {
        this.currentBorrowedBook = currentBorrowedBook;
    }

    public List<BorrowedBook> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<BorrowedBook> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
