package ua.training.model.service;

import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.UserDao;
import ua.training.model.entity.User;

import java.sql.SQLException;
import java.util.Optional;

public class UserService {

    DaoFactory daoFactory = DaoFactory.getInstance();
    public Optional<User> login(String email){
        Optional<User> result; //= Optional.empty();
        try(UserDao userDao = daoFactory.createUserDao()){
            result = userDao.findByEmail(email);
        }
        return result;
    }

    public void register(String surname, String email, String pass){
        UserDao userDao = daoFactory.createUserDao();
        try {
            userDao.register(surname, email, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
