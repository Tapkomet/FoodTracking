package ua.training.model.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * This interface declares the methods used in direct use of a database
 *
 * @author Roman Kobzar
 * @version 1.0
 * @since 2019-09-09
 */
public interface GenericDao<T> extends AutoCloseable {
    void create(T entity) throws SQLException;

    Optional<T> findById(int id) throws SQLException;

    List<T> findAll() throws SQLException;

    void update(T entity) throws SQLException;

    void delete(int id) throws SQLException;

    void close();

    int getCount() throws SQLException;

    Logger logger = LogManager.getLogger(GenericDao.class);
}
