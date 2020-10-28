package dao;

import database.GetConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbGenreDao implements GenreDao {

    Connection connection = new GetConnection().getConnection();

//    @Override
//    public List<String> findGenresByBookId(int bookId) {
//        List<String> genreList = new ArrayList<>();
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id_genre FROM libraryDB.book_genre WHERE id_book = ?");
//            preparedStatement.setString(1, String.valueOf(bookId));
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                int idGenre = resultSet.getInt("id_genre");
//                PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT genre_type FROM libraryDB.genre WHERE id = ?");
//                preparedStatement1.setString(1, String.valueOf(idGenre));
//                ResultSet resultSet1 = preparedStatement1.executeQuery();
//                while (resultSet1.next()) {
//                    String genre = resultSet1.getString("genre_type");
//                    genreList.add(genre);
//                }
//            }
//            return genreList;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return genreList;
//    }

    @Override
    public List<String> findAll() {
        List<String> genreList = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM libraryDB.genre");
            while (resultSet.next()) {
                String genre = resultSet.getString("genre_type");
                genreList.add(genre);
            }
            return genreList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genreList;
    }

    @Override
    public String findById(int itemId) {
        return null;
    }

    @Override
    public boolean create(String item) {
        return false;
    }

    @Override
    public boolean update(String item) {
        return false;
    }

    @Override
    public boolean remove(String item) {
        return false;
    }

    @Override
    public List<Integer> getInsertedGenresIds(List<String> genreList) {
        List<Integer> genresIds = new ArrayList<>();
        try {
            for (String genre : genreList) {
                PreparedStatement pStmtCheckGenre = connection.prepareStatement(
                        "SELECT * FROM libraryDB.genre WHERE genre_type LIKE ?");
                pStmtCheckGenre.setString(1, genre);
                ResultSet rSetCheckGenre = pStmtCheckGenre.executeQuery();
                if (rSetCheckGenre.next()) {
                    genresIds.add(rSetCheckGenre.getInt("id"));
                } else {
                    PreparedStatement pStmtInsertGenre = connection.prepareStatement(
                            "INSERT INTO libraryDB.genre(genre_type) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                    pStmtInsertGenre.setString(1, genre);
                    pStmtInsertGenre.executeUpdate();
                    try (ResultSet generatedKeys = pStmtInsertGenre.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            genresIds.add(generatedKeys.getInt(1));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genresIds;
    }

}
