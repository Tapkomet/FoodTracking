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

        foodService.create(food);
        List<Food> foods = foodService.getAllFoods();
        request.setAttribute("foods" , foods);
        return "/WEB-INF/foodlist.jsp";
    }
}
