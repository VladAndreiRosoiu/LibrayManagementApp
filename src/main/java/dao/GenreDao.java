package dao;

import java.util.List;

public interface GenreDao extends EntityDao<String> {

    List<String> findGenresByBookId(int bookId);

    String findByKeyword(String keyword);
}
