package dao;

import models.book.Author;

import java.util.List;

public interface AuthorDao extends EntityDao<Author> {

    public List<Author> findByBookId(int bookId);

    List<Integer> getInsertedAuthorsIds(List<Author> authorList);
}
