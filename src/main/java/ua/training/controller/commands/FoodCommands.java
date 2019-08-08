package ua.training.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.controller.util.Regex;
import ua.training.model.entity.Food;
import ua.training.model.service.FoodService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static ua.training.controller.commands.FoodCommands.FoodFields.*;
import static ua.training.controller.commands.FoodCommands.FoodLoggerMessageEnum.*;
import static ua.training.controller.commands.FoodCommands.FoodRequestAttributeStrings.*;
import static ua.training.controller.util.Path.*;

public class FoodCommands implements CommandCRUD, Command {

    private static final String ADD = "add";
    private static final String PREVIOUS = "previous";
    private static final String NEXT = "next";
    private FoodService foodService;
    private static final Logger logger = LogManager.getLogger(FoodCommands.class);
    private final int ROWS_ON_PAGE = 5;

    public FoodCommands(FoodService foodService) {
        this.foodService = foodService;
    }

    public enum FoodFields {
        FOOD_ID("food_id"),
        NAME("name"),
        CALORIES("calories"),
        PROTEIN("protein"),
        FAT("fat"),
        CARBOHYDRATES("carbohydrates");
        public final String field;

        FoodFields(String field) {
            this.field = field;
        }
    }

    enum FoodLoggerMessageEnum {
        FOOD_DB_ERROR("Database error when requesting food {}"),
        PAGE_NUMBER_TEMPLATE(":: page = {}"),
        FOOD_COUNT_DB_ERROR("Database error when requesting food count"),
        FOODS_DB_ERROR("Database error when requesting foods");
        public final String message;

        FoodLoggerMessageEnum(String message) {
            this.message = message;
        }
    }

    enum FoodRequestAttributeStrings {
        NAME_ERROR_MESSAGE("name_error_message"),
        SQL_ERROR_MESSAGE("sql_error_message"),
        FOOD("food"),
        FOODS("foods"),
        PUT_IN_NAME("Put in the name"),
        DATABASE_PROBLEM("Database problem: "),
        TOSORT("tosort"),
        PAGE("page"),
        NEXT_PAGE("nextPage"),
        LAST_PAGE("lastPage"),
        NEXT(FoodCommands.NEXT),
        PREVIOUS(FoodCommands.PREVIOUS),
        ERROR_MESSAGE_TEMPLATE("_error_message"),
        INVALID("Invalid ");
        public final String label;

        FoodRequestAttributeStrings(String label) {
            this.label = label;
        }
    }


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        determineMethod(request, response);
    }

    @Override
    public void add(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        addOrEdit(request, response, ADD);
    }

    @Override
    public void edit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        addOrEdit(request, response, "edit");
    }

    private void addOrEdit(HttpServletRequest request, HttpServletResponse response, String operation)
            throws ServletException, IOException {
        String foodIdStr = request.getParameter(FOOD_ID.field);
        String caloriesStr = request.getParameter(CALORIES.field);
        String proteinStr = request.getParameter(PROTEIN.field);
        String fatStr = request.getParameter(FAT.field);
        String carbohydratesStr = request.getParameter(CARBOHYDRATES.field);

        if (numberFormatIsWrong(request, response, FOOD_ID.field, foodIdStr)
                || numberFormatIsWrong(request, response, CALORIES.field, caloriesStr)
                || numberFormatIsWrong(request, response, PROTEIN.field, proteinStr)
                || numberFormatIsWrong(request, response, FAT.field, fatStr)
                || numberFormatIsWrong(request, response, CARBOHYDRATES.field, carbohydratesStr))
            return;

        int foodId = Integer.parseInt(foodIdStr);
        int calories = Integer.parseInt(caloriesStr);
        int protein = Integer.parseInt(proteinStr);
        int fat = Integer.parseInt(fatStr);
        int carbohydrates = Integer.parseInt(carbohydratesStr);

        String name = request.getParameter(NAME.field);
        if (name == null || name.equals("")) {
            request.setAttribute(NAME_ERROR_MESSAGE.label, PUT_IN_NAME.label);
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
            if (operation.equals(ADD)) {
                foodService.create(food);
            } else {
                foodService.update(food);
            }
        } catch (SQLException e) {
            showError(request, response, e);
            return;
        }
        redirect(request, response, CLIENT_FOODS.label);
    }

    @Override
    public void getOne(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sid = request.getParameter(FOOD_ID.field);
        int id = Integer.parseInt(sid);
        try {
            Food food = foodService.getFoodById(id);
            request.setAttribute(FOOD.label, food);
        } catch (SQLException e) {
            logger.debug(FOOD_DB_ERROR.message + id);
            request.setAttribute(SQL_ERROR_MESSAGE.label, DATABASE_PROBLEM.label + e.getMessage());
        }
        forward(request, response, FOOD_JSP.label);

    }

    @Override
    public void getAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sortBy = request.getParameter(TOSORT.label);
        if (sortBy == null || sortBy.equals("")) {
            sortBy = FOOD_ID.field;
        }

        String pageString = request.getParameter(PAGE.label);
        int page;
        if (pageString == null || pageString.isEmpty()) {
            page = 1;
        } else {
            page = Integer.parseInt(pageString);
        }
        logger.debug(PAGE_NUMBER_TEMPLATE.message, page);
        int nextPage;
        String nextPageString = request.getParameter(NEXT_PAGE.label);
        if (PREVIOUS.equals(nextPageString)) {
            nextPage = page - 1;
        } else if (NEXT.equals(nextPageString)) {
            nextPage = page + 1;
        } else {
            nextPage = page;
        }
        page = nextPage;
        int foodAmount;
        try {
            foodAmount = foodService.getFoodCount();
        } catch (SQLException e) {
            logger.debug(FOOD_COUNT_DB_ERROR.message);
            request.setAttribute(SQL_ERROR_MESSAGE.label, DATABASE_PROBLEM.label + e.getMessage());
            forward(request, response, FOOD_LIST_JSP.label);
            return;
        }

        int lastPage = foodAmount / ROWS_ON_PAGE
                + ((foodAmount % ROWS_ON_PAGE) == 0 ? 0 : 1);

        List<Food> foods;
        try {
            if (lastPage > page || lastPage == 0) {
                Integer offset = (page - 1) * ROWS_ON_PAGE;
                foods = foodService.getFoodsSorted(sortBy, ROWS_ON_PAGE, offset);
            } else {
                Integer offset = (lastPage - 1) * ROWS_ON_PAGE;
                foods = foodService.getFoodsSorted(sortBy, ROWS_ON_PAGE, offset);
                page = lastPage;
            }
            request.setAttribute(TOSORT.label, sortBy);
            request.setAttribute(FOODS.label, foods);
            request.setAttribute(PAGE.label, page);
            request.setAttribute(LAST_PAGE.label, lastPage);
        } catch (SQLException e) {
            logger.debug(FOODS_DB_ERROR.message);
            request.setAttribute(SQL_ERROR_MESSAGE.label, DATABASE_PROBLEM.label + e.getMessage());
        }


        forward(request, response, FOOD_LIST_JSP.label);
    }

    @Override
    public void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sid = request.getParameter(FOOD_ID.field);
        int foodId = Integer.parseInt(sid);
        try {
            foodService.delete(foodId);
        } catch (SQLException e) {
            showError(request, response, e);
            return;
        }
        redirect(request, response, CLIENT_FOODS.label);
    }

    private boolean numberFormatIsWrong(HttpServletRequest request, HttpServletResponse response, String paramName,
                                        String foodIdStr) throws ServletException, IOException {
        if (Regex.isNumberWrong(foodIdStr)) {
            request.setAttribute(paramName + ERROR_MESSAGE_TEMPLATE.label, INVALID.label + paramName);
            logger.error(INVALID.label + paramName);
            getAll(request, response);
            return true;
        }
        return false;
    }

    private void showError(HttpServletRequest request, HttpServletResponse response, SQLException e)
            throws ServletException, IOException {
        request.setAttribute(SQL_ERROR_MESSAGE.label, DATABASE_PROBLEM.label + e.getMessage());
        logger.error(DATABASE_PROBLEM.label + e.getMessage());
        getAll(request, response);
    }
}

