package ua.training.controller.commands.food;

import ua.training.controller.commands.Command;
import ua.training.model.entity.Food;
import ua.training.model.service.FoodService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class FoodListCommand implements Command {
    private FoodService foodService;

    public FoodListCommand(FoodService foodService) {
        this.foodService = foodService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Food> foods = foodService.getAllFoods();
        request.setAttribute("foods", foods);
        forward(request, response, "/WEB-INF/foodlist.jsp");
    }
}
