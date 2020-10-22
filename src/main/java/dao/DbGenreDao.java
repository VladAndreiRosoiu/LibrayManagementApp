package dao;

import models.book.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbGenreDao implements GenreDao {
    @Override
    public List<Genre> findGenreByBookId(Connection connection, int bookId) throws SQLException {
        List<Genre> genreList = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id_genre FROM libraryDB.book_genre WHERE id_book = ?");
        preparedStatement.setString(1, String.valueOf(bookId));
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int idGenre = resultSet.getInt("id_genre");
            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT genre_type FROM libraryDB.genre WHERE id = ?");
            preparedStatement1.setString(1, String.valueOf(idGenre));
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            while (resultSet1.next()) {
                String genre = resultSet1.getString("genre_type").replace(" ", "_").toUpperCase();
                genreList.add(Genre.valueOf(genre));
            }
        }
        return genreList;
    }

    @Override
    public List<Genre> findAll(Connection connection) throws SQLException {
        List<Genre> genreList = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM libraryDB.genre");
        while (resultSet.next()) {
            String genre = resultSet.getString("genre_type").replace(" ", "_").toUpperCase();
            genreList.add(Genre.valueOf(genre));
        }
        return genreList;
    }

    @Override
    public Genre findById(Connection connection, int itemId) throws SQLException {
        return null;
    }

    @Override
    public boolean create(Connection connection, Genre item) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Connection connection, Genre item) throws SQLException {
        return false;
    }

    @Override
    public boolean remove(Connection connection, Genre item) throws SQLException {
        return false;
    }

    @Override
    public boolean remove(Connection connection, int itemId) throws SQLException {
        return false;
    }
}
