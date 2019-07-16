package ua.training.model.service;

import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.FoodDao;
import ua.training.model.entity.Food;

import java.sql.SQLException;
import java.util.List;

public class FoodService {

    DaoFactory daoFactory = DaoFactory.getInstance();

    public List<Food> getAllFoods() {
        try (FoodDao foodDao = daoFactory.createFoodDao()) {
            return foodDao.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    

    public void create(Food food){
        try (FoodDao foodDao = daoFactory.createFoodDao()) {
            foodDao.create(food);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
