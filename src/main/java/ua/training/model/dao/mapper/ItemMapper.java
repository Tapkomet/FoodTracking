package ua.training.model.dao.mapper;

import ua.training.model.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class ItemMapper implements ObjectMapper<Item> {


    @Override
    public Item extractFromResultSet(ResultSet rs) throws SQLException {
        Item item = new Item();
        item.setItemId(rs.getInt("item_id"));
        item.setName(rs.getString("name"));
        item.setPrice(rs.getLong("price"));
        return item;
    }

    @Override
    public Item makeUnique(Map<Integer, Item> cache,
                              Item item) {
        cache.putIfAbsent(item.getItemId(), item);
        return cache.get(item.getItemId());
    }
}
