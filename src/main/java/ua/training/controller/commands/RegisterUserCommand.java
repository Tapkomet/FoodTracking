package ua.training.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;


public class RegisterUserCommand implements Command {

    private static final Logger logger = LogManager.getLogger(RegisterUserCommand.class);
    private UserService userService ;

    public RegisterUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");
        if( email == null || email.equals("") || pass == null || pass.equals("")  ){
            return "/index.jsp";
        }
        userService.register(surname, email, pass);
        logger.info("Registration attempt");
        return "/index.jsp";
    }
}
