package ua.training.controller.commands.food;

import ua.training.controller.commands.Command;
import ua.training.controller.util.Path;
import ua.training.controller.util.Regex;
import ua.training.model.entity.Food;
import ua.training.model.service.FoodService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class EditFoodCommand implements Command {
    private FoodService foodService;

    public EditFoodCommand(FoodService foodService) {
        this.foodService = foodService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String foodIdStr = request.getParameter("food_id");
        String caloriesStr = request.getParameter("calories");
        String proteinStr = request.getParameter("protein");
        String fatStr = request.getParameter("fat");
        String carbohydratesStr = request.getParameter("carbohydrates");

        if (checkNumber(request, response, "id", foodIdStr)
                || checkNumber(request, response, "calories", caloriesStr)
                || checkNumber(request, response, "protein", proteinStr)
                || checkNumber(request, response, "fat", fatStr)
                || checkNumber(request, response, "carbohydrates", carbohydratesStr))
            return;

        int foodId = Integer.parseInt(foodIdStr);
        int calories = Integer.parseInt(caloriesStr);
        int protein = Integer.parseInt(proteinStr);
        int fat = Integer.parseInt(fatStr);
        int carbohydrates = Integer.parseInt(carbohydratesStr);

        String name = request.getParameter("name");
        if (name == null || name.equals("")) {
            request.setAttribute("name_error_message", "Put in the name");
            showError(request, response);
            return;
        }

        Food food = new Food.Builder(foodId)
                .foodName(name)
                .calories(calories)
                .fat(fat)
                .protein(protein)
                .carbohydrates(carbohydrates)
                .build();

        try {
            foodService.update(food);
        } catch (SQLException e) {
            request.setAttribute("sql_error_message", "Database problem: " + e.getMessage());
            showError(request, response);
            return;
        }
        redirect(request, response, Path.CLIENT_FOODS);
    }


    private boolean checkNumber(HttpServletRequest request, HttpServletResponse response, String paramName,
                                String foodIdStr) throws ServletException, IOException {
        if (Regex.isNumberWrong(foodIdStr)) {
            request.setAttribute(paramName + "_error_message", "Invalid " + paramName);
            showError(request, response);
            return true;
        }
        return false;
    }


    private void showError(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FoodListCommand listCommand = new FoodListCommand(foodService);
        listCommand.execute(request, response);
    }
}