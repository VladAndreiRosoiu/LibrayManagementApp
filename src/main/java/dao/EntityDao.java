package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface EntityDao<T>{
    List<T> findAll(Connection connection) throws SQLException;

    T findById(Connection connection, int itemId) throws SQLException;

    boolean create(Connection connection, T item)throws SQLException;

    boolean update(Connection connection, T item) throws SQLException;

    boolean remove(Connection connection, T item) throws SQLException;

    boolean remove(Connection connection, int itemId)throws  SQLException;
}
