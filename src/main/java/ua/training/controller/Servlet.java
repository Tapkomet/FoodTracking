package ua.training.controller;

import ua.training.controller.commands.*;
import ua.training.controller.util.Path;
import ua.training.model.service.FoodService;
import ua.training.model.service.UserService;

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

    private Map<String, Command> commands = new HashMap<>();

    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());
        commands.put(Path.CLIENT_FOOD,
                new FoodCommands(new FoodService()));
        commands.put(Path.CLIENT_FOODS,
                new FoodCommands(new FoodService()));
        commands.put(Path.CLIENT_ADD_FOOD,
                new FoodCommands(new FoodService()));
        commands.put(Path.CLIENT_DELETE_FOOD,
                new FoodCommands(new FoodService()));
        commands.put(Path.CLIENT_EDIT_FOOD,
                new FoodCommands(new FoodService()));
        commands.put(Path.USER_LOGIN,
                new LoginUserCommand(new UserService()));
        commands.put(Path.USER_REGISTER,
                new RegisterUserCommand(new UserService()));
        commands.put(Path.EXCEPTION, new ExceptionCommand());
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String path = request.getRequestURI();
        Command command = commands.containsKey(path) ? commands.get(path) : commands.get(Path.INDEX);
        command.execute(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);
    }
}
