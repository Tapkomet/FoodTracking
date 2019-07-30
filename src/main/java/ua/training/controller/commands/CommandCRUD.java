package ua.training.controller.commands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public interface CommandCRUD {

    void add(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    void getOne(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    void getAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    void edit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;
}
