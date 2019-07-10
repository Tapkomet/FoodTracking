package ua.training.model.dao;

import ua.training.model.entity.Food;

import java.sql.SQLException;
import java.util.List;

public interface FoodDao extends GenericDao<Food> {
    List<Food> findAllSorted(String sortBy) throws SQLException;
}
