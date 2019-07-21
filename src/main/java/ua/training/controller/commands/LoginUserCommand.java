package ua.training.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.controller.util.Path;
import ua.training.model.entity.User;
import ua.training.model.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;



public class LoginUserCommand implements Command {

    private static final Logger logger = LogManager.getLogger(LoginUserCommand.class);
    private UserService userService ;

    public LoginUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");
        if( email == null || email.equals("") || pass == null || pass.equals("")  ){
            forward(request, response, Path.LOGIN);
        }
        Optional<User> user = userService.login(email);
        if( user.isPresent() && user.get().getPassword().equals(pass)){
            request.getSession().setAttribute("user" , user.get());
            logger.info("User " + email + " logged successfully.");
            forward(request, response, Path.FOOD_LIST);

        }
        logger.info("Invalid attempt of login user:'"+ email+"'");
        forward(request, response, Path.LOGIN);
    }
}
