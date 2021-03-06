package models.book;

import models.user.Client;

import java.time.LocalDate;

public class BorrowedBookByClient {

    private Book book;
    private LocalDate borrowedOn;
    private LocalDate returnedOn;
    private Client client;

    public BorrowedBookByClient(Book book, LocalDate borrowedOn, Client client) {
        this.book = book;
        this.borrowedOn = borrowedOn;
        this.client = client;
    }

    public BorrowedBookByClient(Book book, LocalDate borrowedOn, LocalDate returnedOn, Client client) {
        this.book = book;
        this.borrowedOn = borrowedOn;
        this.returnedOn = returnedOn;
        this.client = client;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
