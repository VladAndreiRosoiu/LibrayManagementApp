package models.user;

import models.book.Book;

import java.util.List;

public class Client extends User {

    private List<Book> borrowedBooks;
    private Book currentBorrowedBook;
    private boolean isActive;

    public Client(int id, String firstName, String lastName, String user, String email, List<Book> borrowedBooks, Book currentBorrowedBook, boolean isActive) {
        super(id, firstName, lastName, user, email, UserType.CLIENT);
        this.borrowedBooks=borrowedBooks;
        this.currentBorrowedBook=currentBorrowedBook;
        this.isActive=isActive;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
