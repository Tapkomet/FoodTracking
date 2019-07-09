package ua.training.controller.commands;

import ua.training.model.entity.Food;
import ua.training.model.service.FoodService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class FoodListCommand implements Command {
    private FoodService foodService;

    public FoodListCommand(FoodService foodService) {
        this.foodService = foodService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        List<Food> foods = foodService.getAllFoods();
        request.setAttribute("foods" , foods);
        return "/WEB-INF/foodlist.jsp";
    }
}
