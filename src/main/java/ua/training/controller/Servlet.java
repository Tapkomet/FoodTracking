package ua.training.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.controller.commands.*;
import ua.training.model.service.FoodService;
import ua.training.model.service.UserService;

import static ua.training.controller.util.Path.*;
import static ua.training.controller.util.AppConstants.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class Servlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(Servlet.class);
    private static final String RECEIVED_REQUEST = "Received request on path ";
    private Map<String, Command> commands = new HashMap<>();

    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        servletConfig.getServletContext()
                .setAttribute(LOGGED_USERS.label, new HashSet<String>());
        commands.put(CLIENT_FOOD.label,
                new FoodCommands(new FoodService()));
        commands.put(CLIENT_FOODS.label,
                new FoodCommands(new FoodService()));
        commands.put(CLIENT_ADD_FOOD.label,
                new FoodCommands(new FoodService()));
        commands.put(CLIENT_DELETE_FOOD.label,
                new FoodCommands(new FoodService()));
        commands.put(CLIENT_EDIT_FOOD.label,
                new FoodCommands(new FoodService()));
        commands.put(USER_LOGIN_JSP.label,
                new LoginUserCommand(new UserService()));
        commands.put(USER_REGISTER.label,
                new RegisterUserCommand(new UserService()));
        commands.put(EXCEPTION.label, new ExceptionCommand());
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType(TYPE_TEXT_HTML.label);
        response.setCharacterEncoding(UTF8.label);
        request.setCharacterEncoding(UTF8.label);
        String path = request.getRequestURI();
        logger.debug(RECEIVED_REQUEST + path);

        Command command = commands.containsKey(path) ? commands.get(path) : commands.get(INDEX_JSP.label);
        command.execute(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);
    }
}
