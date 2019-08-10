package ua.training.controller.commands;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ua.training.controller.util.Path.CLIENT_BASE_JSP;

public class ClientCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        forward(request, response, CLIENT_BASE_JSP.label);
    }
}