package ua.training.controller.commands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This is the main interface for commands called through the servlet.
 * It serves as the Controller layer of the architecture, requesting data from the Model
 * through Services where necessary and rendering web pages
 *
 * @author Roman Kobzar
 * @version 1.0
 * @since 2019-09-09
 */
public interface Command {
    /**
     * The main method called for many Command implementations, it covers a variety of purposes
     *
     * @param request  contains the request info
     * @param response basic servlet response
     * @throws IOException      if forwarding fails
     * @throws ServletException if forwarding fails
     */
    void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

    /**
     * Redirects the request to a different path
     *
     * @param request  contains the request info
     * @param response basic servlet response
     * @throws IOException if forwarding fails
     */
    default void redirect(HttpServletRequest request, HttpServletResponse response, String path)
            throws IOException {
        response.sendRedirect(request.getContextPath() + path);
    }

    /**
     * Forwards the request and response to actually render a web page that is shown to the user
     *
     * @param request  contains the request info
     * @param response basic servlet response
     * @throws IOException      if forwarding fails
     * @throws ServletException if forwarding fails
     */
    default void forward(HttpServletRequest request, HttpServletResponse response, String path)
            throws ServletException, IOException {
        request.getServletContext().getRequestDispatcher(path).forward(request, response);
    }

    /**
     * Determines which method of the class, other than execute, is actually called
     *
     * @param request  contains the request info
     * @param response basic servlet response
     * @throws IOException      if forwarding fails
     * @throws ServletException if forwarding fails
     */
    default void determineMethod(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
