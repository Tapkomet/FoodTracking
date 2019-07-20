package ua.training.controller.commands.food;

import ua.training.controller.commands.Command;
import ua.training.model.entity.Food;
import ua.training.model.service.FoodService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditFoodCommand implements Command {
    private FoodService foodService;

    public EditFoodCommand(FoodService foodService) {
        this.foodService = foodService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int foodId = Integer.parseInt(request.getParameter("food_id"));
        String name = request.getParameter("name");
        int calories = Integer.parseInt(request.getParameter("calories"));
        int protein = Integer.parseInt(request.getParameter("protein"));
        int fat = Integer.parseInt(request.getParameter("fat"));
        int carbohydrates = Integer.parseInt(request.getParameter("carbohydrates"));

        Food food = new Food.Builder(foodId)
                .foodName(name)
                .calories(calories)
                .fat(fat)
                .protein(protein)
                .carbohydrates(carbohydrates)
                .build();

        foodService.update(food);
        response.sendRedirect(request.getContextPath() + "/api/client/foods");
    }
}