package ua.training.controller.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        throw new RuntimeException("Generated exception");
    }
}
