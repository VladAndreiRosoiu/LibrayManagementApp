package dao;

import models.book.Author;

import java.time.LocalDate;

public interface AuthorDao extends EntityDao <Author>{
    Author findByBirthDate (LocalDate birthDate);
}
