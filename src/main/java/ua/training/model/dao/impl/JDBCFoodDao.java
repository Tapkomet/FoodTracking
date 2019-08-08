package ua.training.model.dao.impl;

import ua.training.model.dao.FoodDao;
import ua.training.model.dao.mapper.FoodMapper;
import ua.training.model.dao.mapper.ObjectMapper;
import ua.training.model.entity.Food;
import ua.training.model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ua.training.controller.commands.FoodCommands.FoodFields.NAME;
import static ua.training.model.dao.impl.JDBCFoodDao.JDBCFoodLoggerMessageEnum.*;

public class JDBCFoodDao implements FoodDao {
    private Connection connection;

    public enum JDBCFoodLoggerMessageEnum {
        CREATE("Making a JDBC update to create a new food object, statement: {}"),
        FIND_ONE("Making a JDBC request to find a food object by id, statement: {}"),
        FIND_ALL("Making a JDBC request to find all food objects, statement: {}"),
        EDIT("Making a JDBC update to edit a food object, statement: {}"),
        DELETE("Making a JDBC update to delete a food object, statement: {}"),
        GET_COUNT("Making a JDBC request to get a count of all food objects, statement: {}"),
        FIND_ALL_SORTED("Making a JDBC request to get all food objects sorted, statement: {}"),
        FIND_NUMBER_SORTED("Making a JDBC request to sort food objects and get a set number of them, statement: {}");
        public final String message;

        JDBCFoodLoggerMessageEnum(String message) {
            this.message = message;
        }
    }

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
        logger.debug(CREATE.message, stmt);
        stmt.executeUpdate();

        stmt.close();
        connection.close();
    }

    @Override
    public Food findById(int id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "select * from food where food_id = (?)");
        stmt.setInt(1, id);
        logger.debug(FIND_ONE.message, stmt);
        ResultSet rs = stmt.executeQuery();
        ObjectMapper<Food> foodMapper = new FoodMapper();

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
        Statement stmt = connection.createStatement();
        logger.debug(FIND_ALL.message, stmt);
        ResultSet rs = stmt.executeQuery(query);

        ObjectMapper<Food> foodMapper = new FoodMapper();

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
                        " where food_id = ?");
        stmt.setInt(5, id);
        stmt.setString(2, name);
        stmt.setInt(1, calories);
        stmt.setInt(2, protein);
        stmt.setInt(3, fat);
        stmt.setInt(4, carbohydrates);
        logger.debug(EDIT.message, stmt);
        stmt.executeUpdate();

        stmt.close();
        connection.close();
    }

    @Override
    public void delete(int id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "delete from food where food_id = (?)");
        stmt.setInt(1, id);
        logger.debug(DELETE.message, stmt);
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
        final String query = "" +
                "select count(*) as count from food";
        Statement stmt = connection.createStatement();
        logger.debug(GET_COUNT.message, stmt);
        ResultSet rs = stmt.executeQuery(query);

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
        logger.debug(FIND_ALL_SORTED.message, stmt);
        ResultSet rs = stmt.executeQuery();
        ObjectMapper<Food> foodMapper = new FoodMapper();

        while (rs.next()) {
            Food food = foodMapper
                    .extractFromResultSet(rs);
            foodMapper
                    .makeUnique(foods, food);
        }
        return new ArrayList<>(foods.values());
    }

    @Override
    public List<Food> findNumberSorted(String sortBy, int integer, int offset) throws SQLException {
        PreparedStatement stmt = null;
        if (sortBy.equals(NAME.field)) {
            stmt = connection.prepareStatement
                    (" select * from food order by name limit ? offset ?");
        } else {
            stmt = connection.prepareStatement
                    (" select * from food order by " + sortBy + "+0 limit ? offset ?");
        }

        stmt.setInt(1, integer);
        stmt.setInt(2, offset);

        logger.debug(FIND_NUMBER_SORTED.message, stmt);
        ResultSet rs = stmt.executeQuery();

        List<Food> foods = new ArrayList<>();
        ObjectMapper<Food> foodMapper = new FoodMapper();

        while (rs.next()) {
            Food food = foodMapper
                    .extractFromResultSet(rs);
            foods.add(food);
        }
        return foods;
    }
}
