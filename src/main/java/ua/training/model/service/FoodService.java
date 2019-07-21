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

    public List<Food> getFoodsSortedBy(String sortBy) throws SQLException {
        try (FoodDao foodDao = daoFactory.createFoodDao()) {
            List<Food> foods = foodDao.findAll();
            switch (sortBy) {
                case "food_id":
                    foods.sort(Food.FoodIdComparator);
                    break;
                case "name":
                    foods.sort(Food.FoodNameComparator);
                    break;
                case "calories":
                    foods.sort(Food.FoodCaloriesComparator);
                    break;
                case "protein":
                    foods.sort(Food.FoodProteinComparator);
                    break;
                case "fat":
                    foods.sort(Food.FoodFatComparator);
                    break;
                case "carbohydrates":
                    foods.sort(Food.FoodCarbohydratesComparator);
                    break;
                default:
                    break;
            }
            return foods;
        }
    }
}
