package ua.training.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * This class is responsible for mapping data from the database into POJOs of various types
 *
 * @author Roman Kobzar
 * @version 1.0
 * @since 2019-09-09
 */
public interface ObjectMapper<T> {
    /**
     * Maps an SQL response to a POJO
     *
     * @param rs the ResultSet that contains the SQL response to a select query
     * @throws SQLException if there is an error extracting data from the ResultSet
     */
    T extractFromResultSet(ResultSet rs) throws SQLException;

    /**
     * Processes objects into a map to eliminate any repeats
     *
     * @param cache the map of objects processed so far
     * @param obj   the actual object to process next
     */
    void makeUnique(Map<Integer, T> cache,
                    T obj);
}
