package ua.training.controller.commands.food;

import ua.training.model.service.FoodService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class DeleteFoodCommand implements ua.training.controller.commands.Command {
    private FoodService foodService;

    public DeleteFoodCommand(FoodService foodService) {
        this.foodService = foodService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sid = request.getParameter("id");
        int code = Integer.parseInt(sid);
        try {
            foodService.delete(code);
        } catch (SQLException e) {
            request.setAttribute("sql_error_message", "Database problem: " + e.getMessage());
            FoodListCommand listCommand = new FoodListCommand(foodService);
            listCommand.getAllFoods(request, response);
            return;
        }
        redirect(request, response, "/api/client/foods");
    }
}