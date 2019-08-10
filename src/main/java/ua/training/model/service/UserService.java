package ua.training.model.service;

import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.UserDao;
import ua.training.model.entity.User;
import ua.training.model.service.exception.LoginException;
import ua.training.model.service.exception.WrongEmailException;
import ua.training.model.service.exception.WrongPasswordException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserService {

    DaoFactory daoFactory = DaoFactory.getInstance();

    public List<User> getAllUsers() {
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Optional<User> login(String email, String pass) throws LoginException {
        Optional<User> result;
        try (UserDao userDao = daoFactory.createUserDao()) {
            result = userDao.findByEmail(email);
        }
        if (result.isPresent()) {
            if (result.get().getPassword().equals(pass)) {
                return result;
            }
            throw new WrongPasswordException("Password does not match.");
        }
        throw new WrongEmailException("User with email " + email + " is not found.");
    }

    public void register(String surname, String email, String pass) throws SQLException {
        try (UserDao userDao = daoFactory.createUserDao()) {
            userDao.register(surname, email, pass);
        }
    }


    public Optional<User> getUserById(int id) throws SQLException {
        Optional<User> result;
        try (UserDao userDao = daoFactory.createUserDao()) {
            result = userDao.findById(id);
        }
        return result;
    }

    public void update(User user) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            userDao.update(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) throws SQLException {
        try (UserDao userDao = daoFactory.createUserDao()) {
            userDao.delete(id);
        }
    }
}