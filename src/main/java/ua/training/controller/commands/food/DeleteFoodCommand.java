package ua.training.controller.commands.food;

import ua.training.model.service.FoodService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteFoodCommand implements ua.training.controller.commands.Command {
    private FoodService foodService;

    public DeleteFoodCommand(FoodService foodService) {
        this.foodService = foodService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sid = request.getParameter("food_id");
        int id = Integer.parseInt(sid);
        foodService.delete(id);
        response.sendRedirect(request.getContextPath() + "/api/client/foods");
    }
}