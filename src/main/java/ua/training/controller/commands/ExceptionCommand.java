package ua.training.controller.commands;

import ua.training.controller.util.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static ua.training.controller.util.Path.ERROR_JSP;

public class ExceptionCommand implements Command {

    public static final String MESSAGE = "message";
    public static final String JAVAX_SERVLET_ERROR_STATUS_CODE = "javax.servlet.error.status_code";
    public static final String CODE = "code";

    /**
     * Handles exceptions that aren't handled individually elsewhere.
     *
     * @param request  contains the exception info
     * @param response basic servlet response
     * @throws IOException      if forwarding fails
     * @throws ServletException if forwarding fails
     * @author Roman Kobzar
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Integer statusCode = (Integer) request.getAttribute(JAVAX_SERVLET_ERROR_STATUS_CODE);
        request.setAttribute(CODE, statusCode);
        switch (statusCode) {
            case 500:
                request.setAttribute(MESSAGE, "Internal server error");
                break;

            case 404:
                request.setAttribute(MESSAGE, "Not found");
                break;

            default:
                request.setAttribute(MESSAGE, "An error has occurred");
        }

        forward(request, response, ERROR_JSP.label);
    }
}
