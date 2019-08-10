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
import java.util.NoSuchElementException;
import java.util.Optional;

import static ua.training.controller.commands.FoodCommands.FoodFields.*;
import static ua.training.controller.commands.FoodCommands.FoodLoggerMessageEnum.*;
import static ua.training.controller.commands.FoodCommands.FoodRequestAttributeStrings.*;
import static ua.training.controller.util.Path.*;

/**
 * This class implements the CRUD methods for Food objects, and included several utility methods
 *
 * @author Roman Kobzar
 * @version 1.0
 * @since 2019-09-09
 */
public class FoodCommands implements CommandCRUD {

    private static final String ADD = "add";
    private static final String EDIT = "edit";
    private static final int MAX_MILLIGRAMS_PER_100_GRAMS = 100000;
    private FoodService foodService;
    private static final Logger logger = LogManager.getLogger(FoodCommands.class);
    private final int ROWS_ON_PAGE = 5;

    public FoodCommands(FoodService foodService) {
        this.foodService = foodService;
    }

    /**
     * Contains the field names of the food object, as they appear on the web pages
     */
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

    /**
     * Contains the Strings that will appear in the Logs
     */
    enum FoodLoggerMessageEnum {
        FOOD_DB_ERROR("Database error when requesting food by id "),
        FOOD_DB_NOT_FOUND_ERROR("Element not found when requesting food by id "),
        PAGE_NUMBER_TEMPLATE(":: page = {}"),
        FOOD_COUNT_DB_ERROR("Database error when requesting food count"),
        FOODS_DB_ERROR("Database error when requesting foods");
        public final String message;

        FoodLoggerMessageEnum(String message) {
            this.message = message;
        }
    }

    /**
     * Contains the various Strings that can be assigned to attributes of a request object in this class's methods
     */
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
        NEXT("next"),
        PREVIOUS("previous"),
        ERROR_MESSAGE_TEMPLATE("_error_message"),
        INVALID("Invalid value "),
        BELOW_ZERO("Value below zero "),
        ABOVE_MAX("Value above 100 grams per 100 grams ");
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
        addOrEdit(request, response, EDIT);
    }

    //adding and editing have very similar flows. They are combined to avoid needless repeating
    private void addOrEdit(HttpServletRequest request, HttpServletResponse response, String operation)
            throws ServletException, IOException {
        String foodIdStr = request.getParameter(FOOD_ID.field);
        String caloriesStr = request.getParameter(CALORIES.field);
        String proteinStr = request.getParameter(PROTEIN.field);
        String fatStr = request.getParameter(FAT.field);
        String carbohydratesStr = request.getParameter(CARBOHYDRATES.field);

        if (numberFieldsAreWrongFormat(request, response, caloriesStr, proteinStr, fatStr, carbohydratesStr)) return;

        int foodId = Integer.parseInt(foodIdStr);
        int calories = Integer.parseInt(caloriesStr);
        int protein = Integer.parseInt(proteinStr);
        int fat = Integer.parseInt(fatStr);
        int carbohydrates = Integer.parseInt(carbohydratesStr);

        if (numberFieldsHaveWrongContent(request, response, calories, protein, fat, carbohydrates)) return;

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
            Optional<Food> food = foodService.getFoodById(id);
            request.setAttribute(FOOD.label, food.get());
        } catch (NoSuchElementException e) {
            logger.error(FOOD_DB_NOT_FOUND_ERROR.message + id);
            request.setAttribute(SQL_ERROR_MESSAGE.label, FOOD_DB_NOT_FOUND_ERROR.message + id);
        } catch (SQLException e) {
            logger.error(FOOD_DB_ERROR.message + id);
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
        if (PREVIOUS.label.equals(nextPageString)) {
            nextPage = page - 1;
        } else if (NEXT.label.equals(nextPageString)) {
            nextPage = page + 1;
        } else {
            nextPage = page;
        }
        page = nextPage;
        int foodAmount;
        try {
            foodAmount = foodService.getFoodCount();
        } catch (SQLException e) {
            logger.error(FOOD_COUNT_DB_ERROR.message);
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
            logger.error(FOODS_DB_ERROR.message);
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

    //runs all the number input fields through regex
    private boolean numberFieldsAreWrongFormat(HttpServletRequest request, HttpServletResponse response,
                                               String caloriesStr, String proteinStr, String fatStr,
                                               String carbohydratesStr) throws ServletException, IOException {
        return numberFormatIsWrong(request, response, CALORIES.field, caloriesStr)
                || numberFormatIsWrong(request, response, PROTEIN.field, proteinStr)
                || numberFormatIsWrong(request, response, FAT.field, fatStr)
                || numberFormatIsWrong(request, response, CARBOHYDRATES.field, carbohydratesStr);
    }

    //checks all the fields for sane content
    private boolean numberFieldsHaveWrongContent(HttpServletRequest request, HttpServletResponse response,
                                                 int calories, int protein, int fat,
                                                 int carbohydrates) throws ServletException, IOException {
        return numberContentIsWrong(request, response, CALORIES.field, calories)
                || numberContentIsWrong(request, response, PROTEIN.field, protein)
                || numberContentIsWrong(request, response, FAT.field, fat)
                || numberContentIsWrong(request, response, CARBOHYDRATES.field, carbohydrates);
    }

    private boolean numberContentIsWrong(HttpServletRequest request, HttpServletResponse response,
                                         String paramName, int param) throws ServletException, IOException {
        //block commented out because regex already precludes values below 0

        /*if (param < 0) {
            request.setAttribute(paramName + ERROR_MESSAGE_TEMPLATE.label, BELOW_ZERO.label);
            logger.error(BELOW_ZERO.label + paramName);
            getAll(request, response);
            return true;
        }*/
        if (param > MAX_MILLIGRAMS_PER_100_GRAMS) {
            request.setAttribute(paramName + ERROR_MESSAGE_TEMPLATE.label, ABOVE_MAX.label);
            logger.error(ABOVE_MAX.label + paramName);
            getAll(request, response);
            return true;
        }
        return false;
    }

    private boolean numberFormatIsWrong(HttpServletRequest request, HttpServletResponse response, String paramName,
                                        String foodIdStr) throws ServletException, IOException {
        if (Regex.isNumberFormatWrong(foodIdStr)) {
            request.setAttribute(paramName + ERROR_MESSAGE_TEMPLATE.label, INVALID.label);
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

