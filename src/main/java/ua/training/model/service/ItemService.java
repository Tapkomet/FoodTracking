package ua.training.model.service;

import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.ItemDao;
import ua.training.model.entity.Item;

import java.sql.SQLException;
import java.util.List;

public class ItemService {

    DaoFactory daoFactory = DaoFactory.getInstance();

    public List<Item> getAllItems(){
        try (ItemDao itemDao = daoFactory.createItemDao()) {
            return itemDao.findAll();
        }
    }

    public void addItem(int itemId, String name, boolean available, long price) {
        try (ItemDao itemDao = daoFactory.createItemDao()) {
            itemDao.addItem(itemId, name, available, itemId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
