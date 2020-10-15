package models.user;

import models.book.Book;

import java.util.List;

public class Client extends User {

    private List<Book> borrowedBooks;
    private boolean isActive;

    public Client(int id, String firstName, String lastName, String user, String email, UserType userType) {
        super(id, firstName, lastName, user, email, userType);
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
