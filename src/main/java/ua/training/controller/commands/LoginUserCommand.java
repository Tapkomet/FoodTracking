package ua.training.controller.commands;

import ua.training.controller.commands.Command;
import ua.training.controller.util.Path;
import ua.training.controller.util.Regex;
import ua.training.model.entity.User;
import ua.training.model.service.UserService;
import ua.training.model.service.exception.LoginException;
import ua.training.model.service.exception.WrongEmailException;
import ua.training.model.service.exception.WrongPasswordException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static ua.training.controller.util.Path.*;


public class LoginUserCommand implements Command {

    private UserService userService;

    public LoginUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");
        if (email == null || email.equals("")) {
            request.setAttribute("email_error_message", "Put in the email");
            forward(request, response, LOGIN_JSP.label);
            return;
        }
        if (pass == null || pass.equals("")) {
            request.setAttribute("password_error_message", "Put in the password");
            forward(request, response, LOGIN_JSP.label);
            return;
        }
        if (Regex.isPasswordWrong(pass)) {
            request.setAttribute("password_error_message", "Invalid password");
            forward(request, response, LOGIN_JSP.label);
            return;
        }
        if (Regex.isEmailWrong(email)) {
            request.setAttribute("email_error_message", "Invalid email");
            forward(request, response, LOGIN_JSP.label);
            return;
        }

        if (checkUserIsLogged(request, email)) {
            forward(request, response, EXCEPTION.label);
            return;
        }
        try {
            Optional<User> user = userService.login(email, pass);
            setUser(request, user.get());
            if (user.get().getRole() == User.ROLE.ADMIN) {
                redirect(request, response, ADMIN_BASE.label);
                return;

            }
            if (user.get().getRole() == User.ROLE.CLIENT) {
                redirect(request, response, CLIENT_BASE.label);
                return;
            }
            forward(request, response, EXCEPTION.label);
            return;
        } catch (WrongEmailException e) {
            request.setAttribute("email_error_message", "Wrong email");
        } catch (WrongPasswordException e) {
            request.setAttribute("password_error_message", "Wrong password");
        } catch (LoginException e) {
            request.setAttribute("login_error_message", "Login failed");
        }
        forward(request, response, LOGIN_JSP.label);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setUser(request, null);
        forward(request, response, INDEX_JSP.label);


    }

    static void setUserRole(HttpServletRequest request,
                            User.ROLE role, String email) {
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        context.setAttribute("email", email);
        session.setAttribute("user", role);
    }

    private static void setUser(HttpServletRequest request,
                                User user) {
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        session.setAttribute("user", user);
    }

    private static boolean checkUserIsLogged(HttpServletRequest request, String email) {
        return request.getSession().getAttribute("user") != null;
    }
}