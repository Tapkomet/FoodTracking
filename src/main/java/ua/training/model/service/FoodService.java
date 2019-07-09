package ua.training.model.service;

import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.FoodDao;
import ua.training.model.entity.Food;

import java.sql.SQLException;
import java.util.List;

public class FoodService {

    DaoFactory daoFactory = DaoFactory.getInstance();

    public List<Food> getAllFoods(){
        try (FoodDao foodDao = daoFactory.createFoodDao()) {
            return foodDao.findAll();
        }
    }

    public void addFood(int foodId, String name, boolean available, long price) {
        try (FoodDao foodDao = daoFactory.createFoodDao()) {
            foodDao.addFood(foodId, name, available, foodId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
