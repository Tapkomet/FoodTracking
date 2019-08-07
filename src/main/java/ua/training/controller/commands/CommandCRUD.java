package ua.training.controller.commands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static ua.training.controller.commands.CommandCRUD.CRUDOperations.*;


public interface CommandCRUD extends Command {

    public enum CRUDOperations {
        add,
        getOne,
        getAll,
        edit,
        delete;

    }

    Map<String, Runnable> commands = new HashMap<>();

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

    @Override
    default void determineMethod(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        path = path.replaceAll(".*/", "");
        switch (CRUDOperations.valueOf(path)) {
            case add:
                add(request, response);
                break;
            case getOne:
                getOne(request, response);
                break;
            case edit:
                edit(request, response);
                break;
            case delete:
                delete(request, response);
                break;
            default:
                getAll(request, response);

        }
    }
}