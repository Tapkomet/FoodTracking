package ua.training.controller;

import ua.training.controller.commands.*;
import ua.training.controller.commands.food.*;
import ua.training.controller.util.Path;
import ua.training.model.service.FoodService;
import ua.training.model.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Servlet extends HttpServlet {

    private Map<String, Command> commands = new HashMap<>();

    public void init(){
        commands.put("client/food",
                new FoodCommand(new FoodService()));
        commands.put("client/foods",
                new FoodListCommand(new FoodService()));
        commands.put("client/addFood",
                new AddFoodCommand(new FoodService()));
        commands.put("client/editFood",
                new EditFoodCommand(new FoodService()));
        commands.put("client/deleteFood",
                new DeleteFoodCommand(new FoodService()));
        commands.put("user-login",
                new LoginUserCommand(new UserService()));
        commands.put("user-register",
                new RegisterUserCommand(new UserService()));
        commands.put("exception" , new ExceptionCommand());
    }
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String path = request.getRequestURI();
        path = path.replaceAll(".*/api/" , "");
        Command command = commands.containsKey(path) ? commands.get(path) : commands.get(Path.INDEX);
        command.execute(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);
    }
}
