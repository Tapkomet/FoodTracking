package ua.training.controller.commands;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


import static ua.training.controller.commands.ExceptionCommand.ExceptionLoggerMessageEnum.*;
import static ua.training.controller.util.Path.ERROR_JSP;

public class ExceptionCommand implements Command {

    private static final String MESSAGE = "message";
    private static final String JAVAX_SERVLET_ERROR_STATUS_CODE = "javax.servlet.error.status_code";
    private static final String CODE = "code";
    private static final Logger logger = LogManager.getLogger(ExceptionCommand.class);

    enum ExceptionLoggerMessageEnum {
        INTERNAL_SERVER_ERROR("Internal server error"),
        NOT_FOUND_ERROR("Not found"),
        DEFAULT_ERROR("An error has occurred");
        public final String message;

        ExceptionLoggerMessageEnum(String message) {
            this.message = message;
        }
    }

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
                request.setAttribute(MESSAGE, INTERNAL_SERVER_ERROR);
                logger.error(INTERNAL_SERVER_ERROR.message);
                break;

            case 404:
                request.setAttribute(MESSAGE, NOT_FOUND_ERROR);
                logger.error(NOT_FOUND_ERROR.message);
                break;

            default:
                request.setAttribute(MESSAGE, DEFAULT_ERROR);
                logger.error(DEFAULT_ERROR.message);
        }

        forward(request, response, ERROR_JSP.label);
    }
}
