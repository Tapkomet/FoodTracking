package ua.training.model.dao;

import ua.training.model.entity.Food;

import java.sql.SQLException;

public interface FoodDao extends GenericDao<Food> {
    void addFood(int foodId, String name, boolean available, long price) throws SQLException;
}
