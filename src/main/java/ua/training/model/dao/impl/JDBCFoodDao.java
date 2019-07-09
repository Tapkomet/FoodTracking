package ua.training.model.dao.impl;

import ua.training.model.dao.FoodDao;
import ua.training.model.dao.mapper.FoodMapper;
import ua.training.model.entity.Food;
import ua.training.model.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCFoodDao implements FoodDao {
    private Connection connection;


    public JDBCFoodDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Food entity) {

    }

    @Override
    public Food findById(int id) {
        return null;
    }

    @Override
    public List<Food> findAll() {
        Map<Integer, Food> foods = new HashMap<>();
        Map<Integer, User> users = new HashMap<>();

        final String query = "" +
                " select * from food";
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);

            FoodMapper foodMapper = new FoodMapper();

            while (rs.next()) {
                Food food = foodMapper
                        .extractFromResultSet(rs);
                food = foodMapper
                        .makeUnique(foods, food);
            }
            return new ArrayList<>(foods.values());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public void update(Food entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close()  {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addFood(int foodId, String name, boolean available, long price) {

    }
}
