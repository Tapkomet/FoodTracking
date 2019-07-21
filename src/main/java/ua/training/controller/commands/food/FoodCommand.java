package ua.training.controller.commands.food;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.controller.commands.Command;
import ua.training.controller.util.Path;
import ua.training.model.entity.Food;
import ua.training.model.service.FoodService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class FoodCommand implements Command {
    private FoodService foodService;

    public FoodCommand(FoodService foodService) {
        this.foodService = foodService;
    }

    private static final Logger logger = LogManager.getLogger(FoodCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sid = request.getParameter("food_id");
        int id = Integer.parseInt(sid);
        try {
            Food food = foodService.getFoodById(id);
            request.setAttribute("food", food);
        } catch (SQLException e) {
            logger.debug("Database error when requesting food {}"+id);
            request.setAttribute("sql_error_message", "Database problem: " + e.getMessage());
        }
        forward(request, response, Path.FOOD);
    }
}