package ua.training.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.controller.util.Path;
import ua.training.controller.util.Regex;
import ua.training.model.entity.Food;
import ua.training.model.service.FoodService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class FoodCommands implements CommandCRUD, Command {

    private FoodService foodService;
    private static final Logger logger = LogManager.getLogger(FoodCommands.class);
    private final int ROWS_ON_PAGE = 5;

    public FoodCommands(FoodService foodService) {
        this.foodService = foodService;
    }


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }

    @Override
    public void add(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String foodIdStr = request.getParameter("food_id");
        String caloriesStr = request.getParameter("calories");
        String proteinStr = request.getParameter("protein");
        String fatStr = request.getParameter("fat");
        String carbohydratesStr = request.getParameter("carbohydrates");

        if (numberFormatIsWrong(request, response, "id", foodIdStr)
                || numberFormatIsWrong(request, response, "calories", caloriesStr)
                || numberFormatIsWrong(request, response, "protein", proteinStr)
                || numberFormatIsWrong(request, response, "fat", fatStr)
                || numberFormatIsWrong(request, response, "carbohydrates", carbohydratesStr))
            return;

        int foodId = Integer.parseInt(foodIdStr);
        int calories = Integer.parseInt(caloriesStr);
        int protein = Integer.parseInt(proteinStr);
        int fat = Integer.parseInt(fatStr);
        int carbohydrates = Integer.parseInt(carbohydratesStr);

        String name = request.getParameter("name");
        if (name == null || name.equals("")) {
            request.setAttribute("name_error_message", "Put in the name");
            getAll(request, response);
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
            foodService.create(food);
        } catch (SQLException e) {
            request.setAttribute("sql_error_message", "Database problem: " + e.getMessage());
            getAll(request, response);
            return;
        }
        redirect(request, response, Path.CLIENT_FOODS);
    }

    @Override
    public void getOne(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sid = request.getParameter("food_id");
        int id = Integer.parseInt(sid);
        try {
            Food food = foodService.getFoodById(id);
            request.setAttribute("food", food);
        } catch (SQLException e) {
            logger.debug("Database error when requesting food {}" + id);
            request.setAttribute("sql_error_message", "Database problem: " + e.getMessage());
        }
        forward(request, response, Path.FOOD);

    }

    @Override
    public void getAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Food> foods = foodService.getAllFoods();
            request.setAttribute("foods", foods);
        } catch (SQLException e) {
            logger.debug("Database error when requesting foods");
            request.setAttribute("sql_error_message", "Database problem: " + e.getMessage());
        }
        forward(request, response, Path.FOOD_LIST);
    }

    @Override
    public void edit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String foodIdStr = request.getParameter("food_id");
        String caloriesStr = request.getParameter("calories");
        String proteinStr = request.getParameter("protein");
        String fatStr = request.getParameter("fat");
        String carbohydratesStr = request.getParameter("carbohydrates");

        if (numberFormatIsWrong(request, response, "id", foodIdStr)
                || numberFormatIsWrong(request, response, "calories", caloriesStr)
                || numberFormatIsWrong(request, response, "protein", proteinStr)
                || numberFormatIsWrong(request, response, "fat", fatStr)
                || numberFormatIsWrong(request, response, "carbohydrates", carbohydratesStr))
            return;

        int foodId = Integer.parseInt(foodIdStr);
        int calories = Integer.parseInt(caloriesStr);
        int protein = Integer.parseInt(proteinStr);
        int fat = Integer.parseInt(fatStr);
        int carbohydrates = Integer.parseInt(carbohydratesStr);

        String name = request.getParameter("name");
        if (name == null || name.equals("")) {
            request.setAttribute("name_error_message", "Put in the name");
            getAll(request, response);
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
            getAll(request, response);
            return;
        }
        redirect(request, response, Path.CLIENT_FOODS);
    }

    @Override
    public void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sid = request.getParameter("food_id");
        int foodId = Integer.parseInt(sid);
        try {
            foodService.delete(foodId);
        } catch (SQLException e) {
            request.setAttribute("sql_error_message", "Database problem: " + e.getMessage());
            getAll(request, response);
            return;
        }
        redirect(request, response, Path.CLIENT_FOODS);
    }

    private boolean numberFormatIsWrong(HttpServletRequest request, HttpServletResponse response, String paramName,
                                        String foodIdStr) throws ServletException, IOException {
        if (Regex.isNumberWrong(foodIdStr)) {
            request.setAttribute(paramName + "_error_message", "Invalid " + paramName);
            getAll(request, response);
            return true;
        }
        return false;
    }
}
