package ua.training.controller.commands;

import ua.training.model.entity.Food;
import ua.training.model.service.FoodService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AddFoodCommand implements Command {
    private FoodService foodService;

    public AddFoodCommand(FoodService foodService) {
        this.foodService = foodService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int foodId = Integer.parseInt(request.getParameter("food_id"));
        String name = request.getParameter("name");
        boolean available = Boolean.parseBoolean(request.getParameter("available"));
        long price = Long.parseLong(request.getParameter("price"));
        foodService.addFood(foodId, name, available, price);
        List<Food> foods = foodService.getAllFoods();
        request.setAttribute("foods" , foods);
        return "/WEB-INF/foodlist.jsp";
    }
}
