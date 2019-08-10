package ua.training.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.controller.util.Regex;
import ua.training.model.entity.User;
import ua.training.model.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static ua.training.controller.commands.UserCommands.UserFields.*;
import static ua.training.controller.commands.UserCommands.UserLoggerMessageEnum.*;
import static ua.training.controller.commands.UserCommands.UserRequestAttributeStrings.*;
import static ua.training.controller.util.Path.*;

/**
 * This class implements the CRUD methods for User objects, and included several utility methods
 *
 * @author Roman Kobzar
 * @version 1.0
 * @since 2019-09-09
 */
public class UserCommands implements CommandCRUD {

    private static final String ADD = "add";
    private static final String EDIT = "edit";
    private static final int MAX_MILLIGRAMS_PER_100_GRAMS = 100000;
    private UserService userService;
    private static final Logger logger = LogManager.getLogger(UserCommands.class);
    private final int ROWS_ON_PAGE = 5;

    public UserCommands(UserService userService) {
        this.userService = userService;
    }

    /**
     * Contains the field names of the user object, as they appear on the web pages
     */
    public enum UserFields {
        USER_ID("user_id"),
        NAME("name"),
        CALORIES("calories"),
        PROTEIN("protein"),
        FAT("fat"),
        CARBOHYDRATES("carbohydrates");
        public final String field;

        UserFields(String field) {
            this.field = field;
        }
    }

    /**
     * Contains the Strings that will appear in the Logs
     */
    enum UserLoggerMessageEnum {
        USER_DB_ERROR("Database error when requesting user by id "),
        USER_DB_NOT_FOUND_ERROR("Element not found when requesting user by id "),
        PAGE_NUMBER_TEMPLATE(":: page = {}"),
        USER_COUNT_DB_ERROR("Database error when requesting user count"),
        USERS_DB_ERROR("Database error when requesting users");
        public final String message;

        UserLoggerMessageEnum(String message) {
            this.message = message;
        }
    }

    /**
     * Contains the various Strings that can be assigned to attributes of a request object in this class's methods
     */
    enum UserRequestAttributeStrings {
        NAME_ERROR_MESSAGE("name_error_message"),
        SQL_ERROR_MESSAGE("sql_error_message"),
        USER("user"),
        USERS("users"),
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

        UserRequestAttributeStrings(String label) {
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
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");
        if (surname == null || surname.equals("")) {
            request.setAttribute("surname_error_message", "Put in the surname");
            forward(request, response, REGISTRATION_JSP.label);
            return;
        }
        if (email == null || email.equals("")) {
            request.setAttribute("email_error_message", "Put in the email");
            forward(request, response, REGISTRATION_JSP.label);
            return;
        }
        if (pass == null || pass.equals("")) {
            request.setAttribute("password_error_message", "Put in the password");
            forward(request, response, REGISTRATION_JSP.label);
            return;
        }


        if (Regex.isEmailWrong(email)) {
            request.setAttribute("email_error_message", "Invalid email");
            forward(request, response, REGISTRATION_JSP.label);
            return;
        }
        if (Regex.isSurnameWrong(surname)) {
            request.setAttribute("surname_error_message", "Invalid name");
            forward(request, response, REGISTRATION_JSP.label);
            return;
        }
        if (Regex.isPasswordWrong(pass)) {
            request.setAttribute("password_error_message", "Invalid password");
            forward(request, response, REGISTRATION_JSP.label);
            return;
        }
        try {
            userService.register(surname, email, pass);
        } catch (SQLException e) {
            showError(request, response, e);
            return;
        }


        request.setAttribute("index_message", "Registration successful");
        forward(request, response, INDEX_JSP.label);
    }

    @Override
    public void edit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String role = request.getParameter("role");
        String sid = request.getParameter("id");
        int id = Integer.parseInt(sid);
        User user = new User();
        user.setId(id);
        user.setRoleFromString(role);
        userService.update(user);
        redirect(request, response, ADMIN_USERS.label);
    }

    @Override
    public void getOne(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sid = request.getParameter(USER_ID.field);
        int id = Integer.parseInt(sid);
        try {
            Optional<User> user = userService.getUserById(id);
            request.setAttribute(USER.label, user.get());
        } catch (NoSuchElementException e) {
            logger.error(USER_DB_NOT_FOUND_ERROR.message + id);
            request.setAttribute(SQL_ERROR_MESSAGE.label, USER_DB_NOT_FOUND_ERROR.message + id);
        } catch (SQLException e) {
            logger.error(USER_DB_ERROR.message + id);
            request.setAttribute(SQL_ERROR_MESSAGE.label, DATABASE_PROBLEM.label + e.getMessage());
        }
        forward(request, response, PROFILE_JSP.label);

    }

    @Override
    public void getAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> users = userService.getAllUsers();
        request.setAttribute("users", users);
        forward(request, response, USER_LIST_JSP.label);
    }

    @Override
    public void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sid = request.getParameter(USER_ID.field);
        int userId = Integer.parseInt(sid);
        try {
            userService.delete(userId);
        } catch (SQLException e) {
            showError(request, response, e);
            return;
        }
        redirect(request, response, ADMIN_USERS.label);
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
                                        String userIdStr) throws ServletException, IOException {
        if (Regex.isNumberFormatWrong(userIdStr)) {
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

