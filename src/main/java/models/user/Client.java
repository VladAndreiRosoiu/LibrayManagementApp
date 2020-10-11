package models.user;

import models.book.Book;

import java.util.List;

public class Client extends User {

    private List<Book> borrowedBooks;
    private boolean isActive;

    public Client(int id, String firstName, String lastName, String user, String email, boolean isActive, List<Book> borrowedBooks) {
        super(id, firstName, lastName, user, email, UserType.CLIENT);
        this.isActive = isActive;
        this.borrowedBooks = borrowedBooks;
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
