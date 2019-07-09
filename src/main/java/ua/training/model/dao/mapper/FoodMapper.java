package ua.training.model.dao.mapper;

import ua.training.model.entity.Food;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class FoodMapper implements ObjectMapper<Food> {


    @Override
    public Food extractFromResultSet(ResultSet rs) throws SQLException {
        Food food = new Food();
        food.setId(rs.getInt("food_id"));
        food.setName(rs.getString("name"));
        return food;
    }

    @Override
    public Food makeUnique(Map<Integer, Food> cache,
                           Food food) {
        cache.putIfAbsent(food.getId(), food);
        return cache.get(food.getId());
    }
}
