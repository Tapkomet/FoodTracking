package ua.training.model.dao;

import ua.training.model.entity.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserDao extends GenericDao<User> {

    Optional<User> findByEmail(String email);
    void register(String surname, String email, String pass) throws SQLException;
}
