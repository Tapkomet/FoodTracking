package ua.training.model.service;

import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.FoodDao;
import ua.training.model.entity.Food;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * This class processes calls from the controller related to Food objects, processes them, creates the respective
 * Dao object, calls the Dao methods, and processes the return as necessary before returning it to controller
 *
 * @author Roman Kobzar
 * @version 1.0
 * @since 2019-09-09
 */
public class FoodService {

    private DaoFactory daoFactory = DaoFactory.getInstance();

    public List<Food> getAllFoods() throws SQLException {
        try (FoodDao foodDao = daoFactory.createFoodDao()) {
            return foodDao.findAll();
        }
    }


    public int getFoodCount() throws SQLException {
        try (FoodDao foodDao = daoFactory.createFoodDao()) {
            return foodDao.getCount();
        }

    }

    public Optional<Food> getFoodById(int id) throws SQLException {
        Optional<Food> result;
        try (FoodDao foodDao = daoFactory.createFoodDao()) {
            result = foodDao.findById(id);
        }
        return result;
    }


    public void create(Food food) throws SQLException {
        try (FoodDao foodDao = daoFactory.createFoodDao()) {
            foodDao.create(food);
        }
    }

    public void update(Food food) throws SQLException {
        try (FoodDao foodDao = daoFactory.createFoodDao()) {
            foodDao.update(food);
        }
    }

    public void delete(int id) throws SQLException {
        try (FoodDao foodDao = daoFactory.createFoodDao()) {
            foodDao.delete(id);
        }
    }


    public List<Food> getFoodsSorted(String sortBy, int rows_on_page, Integer offset) throws SQLException {
        try (FoodDao foodDao = daoFactory.createFoodDao()) {
            List<Food> foods = foodDao.findNumberSorted(sortBy, rows_on_page, offset);
            return foods;
        }
    }
}

