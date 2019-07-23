package ua.training.controller.commands.food;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.controller.util.Path;
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
    private final int ROWS_ON_PAGE = 5;

    /**
     * Lists all foods available, possibly sorted
     *
     * @param request  Servlet request
     * @param response Servlet response
     * @throws ServletException if forwarding or redirecting fails
     * @throws IOException      if forwarding or redirecting
     * @author Roman Kobzar
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sortBy = request.getParameter("tosort");
        if (sortBy == null || sortBy.equals("")) {
            sortBy = "food_id";
        }

        String pageString = request.getParameter("page");
        int page;
        if (pageString == null || pageString.isEmpty()) {
            logger.debug(":: page = {}", pageString);
            page = 1;
        } else {
            page = Integer.parseInt(pageString);
        }
        int nextPage;
        String nextPageString = request.getParameter("nextPage");
        if ("previous".equals(nextPageString)) {
            nextPage = page - 1;
        } else if ("next".equals(nextPageString)) {
            nextPage = page + 1;
        } else {
            nextPage = page;
        }
        page = nextPage;
        int foodAmount;
        try {
            foodAmount = foodService.getFoodCount();
        } catch (SQLException e) {
            logger.debug("Database error when requesting food count");
            request.setAttribute("sql_error_message", "Database problem: " + e.getMessage());
            forward(request, response, Path.FOOD_LIST);
            return;
        }

        int lastPage = foodAmount / ROWS_ON_PAGE
                + ((foodAmount % ROWS_ON_PAGE) == 0 ? 0 : 1);

        List<Food> foods;
        try {
            if (lastPage > page) {
                Integer offset = (page - 1) * ROWS_ON_PAGE;
                foods = foodService.getFoodsSorted(sortBy, ROWS_ON_PAGE, offset);
            } else {
                Integer offset = (lastPage - 1) * ROWS_ON_PAGE;
                foods = foodService.getFoodsSorted(sortBy, ROWS_ON_PAGE, offset);
                page = lastPage;
            }
            request.setAttribute("tosort", sortBy);
            request.setAttribute("foods", foods);
            request.setAttribute("page", page);
            request.setAttribute("lastPage", lastPage);
        }
        catch (SQLException e){
            logger.debug("Database error when requesting foods");
            request.setAttribute("sql_error_message", "Database problem: " + e.getMessage());
        }

        forward(request, response, Path.FOOD_LIST);
    }

    void getAllFoods(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Food> foods = foodService.getAllFoods();
            request.setAttribute("foods", foods);
        } catch (SQLException e) {
            logger.debug("Database error when requesting foods");
            request.setAttribute("sql_error_message", "Database problem: " + e.getMessage());
        }
        forward(request, response, Path.FOOD_LIST);
    }
}