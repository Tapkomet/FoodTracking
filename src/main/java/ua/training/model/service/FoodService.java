package ua.training.model.service;

import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.FoodDao;
import ua.training.model.entity.Food;

import java.sql.SQLException;
import java.util.List;

public class FoodService {

    DaoFactory daoFactory = DaoFactory.getInstance();

    public List<Food> getAllFoods() throws SQLException {
        FoodDao foodDao = daoFactory.createFoodDao();
        return foodDao.findAll();
    }

    public Food getFoodById(int id) throws SQLException {
        FoodDao foodDao = daoFactory.createFoodDao();
        return foodDao.findById(id);
    }


    public void create(Food food) throws SQLException {
        FoodDao foodDao = daoFactory.createFoodDao();
        foodDao.create(food);
    }

    public void update(Food food) throws SQLException {
        FoodDao foodDao = daoFactory.createFoodDao();
        foodDao.update(food);
    }

    public void delete(int code) throws SQLException {
        FoodDao foodDao = daoFactory.createFoodDao();
        foodDao.delete(code);
    }
}
