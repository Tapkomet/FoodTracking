package ua.training.model.dao.impl;

import ua.training.model.dao.FoodDao;
import ua.training.model.dao.mapper.FoodMapper;
import ua.training.model.entity.Food;
import ua.training.model.entity.User;

import java.sql.*;
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
    public void create(Food food) throws SQLException {
        int id = food.getId();
        String name = food.getName();
        int calories = food.getCalories();
        int protein = food.getProtein();
        int fat = food.getFat();
        int carbohydrates = food.getCarbohydrates();
        PreparedStatement stmt = connection.prepareStatement(
                "insert into food (food_id, name, calories, protein, fat, carbohydrates)" +
                        " values (?, ?, ?, ?, ?, ?)");
        stmt.setInt(1, id);
        stmt.setString(2, name);
        stmt.setInt(3, calories);
        stmt.setInt(4, protein);
        stmt.setInt(5, fat);
        stmt.setInt(6, carbohydrates);
        stmt.executeUpdate();

        stmt.close();
        connection.close();
    }

    @Override
    public Food findById(int id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "select * from food where food_id = (?)");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        FoodMapper foodMapper = new FoodMapper();

        rs.next();
        Food food = foodMapper.extractFromResultSet(rs);

        stmt.close();
        connection.close();
        return food;
    }

    @Override
    public List<Food> findAll() throws SQLException {
        Map<Integer, Food> foods = new HashMap<>();

        final String query = "" +
                " select * from food";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);

        FoodMapper foodMapper = new FoodMapper();

        while (rs.next()) {
            Food food = foodMapper
                    .extractFromResultSet(rs);
            foodMapper
                    .makeUnique(foods, food);
        }
        return new ArrayList<>(foods.values());
    }


    @Override
    public void update(Food food) throws SQLException {
        int id = food.getId();
        String name = food.getName();
        int calories = food.getCalories();
        int protein = food.getProtein();
        int fat = food.getFat();
        int carbohydrates = food.getCarbohydrates();
        PreparedStatement stmt = connection.prepareStatement(
                "update food set calories = ?, protein = ?, fat = ?, carbohydrates = ?" +
                        " where id = ?");
        stmt.setInt(5, id);
        stmt.setString(2, name);
        stmt.setInt(1, calories);
        stmt.setInt(2, protein);
        stmt.setInt(3, fat);
        stmt.setInt(4, carbohydrates);
        stmt.executeUpdate();

        stmt.close();
        connection.close();
    }

    @Override
    public void delete(int id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "delete from food where id = (?)");
        stmt.setInt(1, id);
        stmt.executeUpdate();

        stmt.close();
        connection.close();
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getCount() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement
                ("select count(*) as count from food");
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            return rs.getInt("count");
        }
        return 0;
    }


    @Override
    public List<Food> findAllSorted(String sortBy) throws SQLException {
        Map<Integer, Food> foods = new HashMap<>();

        PreparedStatement stmt = connection.prepareStatement(" select * from food ORDER BY ?");
        stmt.setString(1, sortBy);
        ResultSet rs = stmt.executeQuery();

        FoodMapper foodMapper = new FoodMapper();

        while (rs.next()) {
            Food food = foodMapper
                    .extractFromResultSet(rs);
            foodMapper
                    .makeUnique(foods, food);
        }
        return new ArrayList<>(foods.values());
    }
}
