package ua.training.controller.commands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static ua.training.controller.commands.CommandCRUD.CRUDOperations.*;

/**
 * This interface is the extension of the Command interface, designed for handling CRUD operations
 * that are most common in the process of app usage
 *
 * @author Roman Kobzar
 * @version 1.0
 * @since 2019-09-09
 */

public interface CommandCRUD extends Command {

    /**
     * The enum containing names of CRUD operations that occur in the URIs
     */
    enum CRUDOperations {
        add,
        getOne,
        getAll,
        edit,
        delete;

    }

    Map<String, Runnable> commands = new HashMap<>();

    /**
     * CRUD Create, or Add method, takes and parses field values from the page before calling a respective Create
     * Service method
     */
    void add(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    /**
     * CRUD Read, or Get method, issues a request to the respective Service for one object by its id
     */
    void getOne(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    /**
     * CRUD Read, or Get method, issues a request to the respective Service for all or (where pagination is implemented)
     * several objects
     */
    void getAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    /**
     * CRUD Update, or Edit method, takes and parses field values from the page before calling a respective Update
     * Service method
     */
    void edit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    /**
     * CRUD Delete method, calls the Delete method of the respective service by its id
     */
    void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    /**
     * Determines which particular CRUD method is called
     */
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