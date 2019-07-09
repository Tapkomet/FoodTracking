package ua.training.model.dao.impl;

import ua.training.model.dao.ItemDao;
import ua.training.model.dao.mapper.ItemMapper;
import ua.training.model.entity.Item;
import ua.training.model.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCItemDao implements ItemDao {
    private Connection connection;


    public JDBCItemDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Item entity) {

    }

    @Override
    public Item findById(int id) {
        return null;
    }

    @Override
    public List<Item> findAll() {
        Map<Integer, Item> items = new HashMap<>();
        Map<Integer, User> users = new HashMap<>();

        final String query = "" +
                " select * from item";
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);

            ItemMapper itemMapper = new ItemMapper();

            while (rs.next()) {
                Item item = itemMapper
                        .extractFromResultSet(rs);
                item = itemMapper
                        .makeUnique(items, item);
            }
            return new ArrayList<>(items.values());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public void update(Item entity) {

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
    public void addItem(int itemId, String name, boolean available, long price) {

    }
}
