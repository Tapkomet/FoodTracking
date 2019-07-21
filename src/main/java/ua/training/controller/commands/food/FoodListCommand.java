package ua.training.controller.commands.food;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.entity.Food;
import ua.training.model.service.FoodService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class FoodListCommand implements ua.training.controller.commands.Command {
    private FoodService foodService;

    public FoodListCommand(FoodService foodService) {
        this.foodService = foodService;
    }
    private static final Logger logger = LogManager.getLogger(FoodListCommand.class);

    /**
     * Lists all foods available
     * @param request Servlet request
     * @param response Servlet response
     * @throws ServletException if forwarding or redirecting fails
     * @throws IOException if forwarding or redirecting
     * @author Roman Kobzar
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Food> foods = foodService.getAllFoods();
            request.setAttribute("foods", foods);
        } catch (SQLException e) {
            logger.debug("Database error when requesting foods");
            request.setAttribute("sql_error_message", "Database problem: " + e.getMessage());
        }
        forward(request, response, "/WEB-INF/foodlist.jsp");
    }
}