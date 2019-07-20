package ua.training.controller.commands.food;

import ua.training.controller.commands.Command;
import ua.training.model.entity.Food;
import ua.training.model.service.FoodService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FoodCommand implements Command {
    private FoodService foodService;

    public FoodCommand(FoodService foodService) {
        this.foodService = foodService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sid = request.getParameter("food_id");
        int id = Integer.parseInt(sid);
        Food food = foodService.getFoodById(id);
        request.setAttribute("food" , food);
        forward(request, response, "/WEB-INF/food.jsp");
    }
}