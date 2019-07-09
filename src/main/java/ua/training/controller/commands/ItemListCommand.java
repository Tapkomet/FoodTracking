package ua.training.controller.commands;

import ua.training.model.entity.Item;
import ua.training.model.service.ItemService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ItemListCommand implements Command {
    private ItemService itemService;

    public ItemListCommand(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        List<Item> items = itemService.getAllItems();
        request.setAttribute("items" , items);
        return "/WEB-INF/itemlist.jsp";
    }
}
