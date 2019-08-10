package ua.training.model.dao.mapper;

import ua.training.model.entity.Food;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static ua.training.model.dao.mapper.FoodMapper.FoodDatabaseFields.*;

public class FoodMapper implements ObjectMapper<Food> {
    /**
     * Contains the field names of the food object, as they appear in the database
     */
    enum FoodDatabaseFields {
        FOOD_ID("food_id"),
        NAME("name"),
        CALORIES("calories"),
        PROTEIN("protein"),
        FAT("fat"),
        CARBOHYDRATES("carbohydrates");
        public final String field;

        FoodDatabaseFields(String field) {
            this.field = field;
        }
    }

    @Override
    public Food extractFromResultSet(ResultSet rs) throws SQLException {
        Food food = new Food();
        food.setId(rs.getInt(FOOD_ID.field));
        food.setName(rs.getString(NAME.field));
        food.setCalories(rs.getInt(CALORIES.field));
        food.setProtein(rs.getInt(PROTEIN.field));
        food.setFat(rs.getInt(FAT.field));
        food.setCarbohydrates(rs.getInt(CARBOHYDRATES.field));
        return food;
    }

    @Override
    public void makeUnique(Map<Integer, Food> cache,
                           Food food) {
        cache.putIfAbsent(food.getId(), food);
        cache.get(food.getId());
    }
}
