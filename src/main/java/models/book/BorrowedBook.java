package models.book;

import java.time.LocalDate;

public class BorrowedBook {

    private Book book;
    private LocalDate borrowedOn;
    private LocalDate returnedOn;

    public BorrowedBook(Book book, LocalDate borrowedOn) {
        this.book = book;
        this.borrowedOn = borrowedOn;
    }

    public BorrowedBook(Book book, LocalDate borrowedOn, LocalDate returnedOn) {
        this.book = book;
        this.borrowedOn = borrowedOn;
        this.returnedOn = returnedOn;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getBorrowedOn() {
        return borrowedOn;
    }

    public void setBorrowedOn(LocalDate borrowedOn) {
        this.borrowedOn = borrowedOn;
    }

    public LocalDate getReturnedOn() {
        return returnedOn;
    }

    public void setReturnedOn(LocalDate returnedOn) {
        this.returnedOn = returnedOn;
    }
}
