package ua.training.controller.util;

public interface Path {
    String CLIENT = "/api/client";
    String ADMIN = "/api/admin";
    String ADMIN_USERS = "/api/admin/users";
    String ADMIN_EDIT_USER = "/api/admin/users/edit";
    String CLIENT_FOODS = "/api/client/food/getAll";
    String CLIENT_FOOD = "/api/client/food/getOne";
    String CLIENT_ADD_FOOD = "/api/client/food/add";
    String CLIENT_EDIT_FOOD = "/api/client/food/edit";
    String CLIENT_DELETE_FOOD = "/api/client/food/delete";
    String USER_LOGIN = "/api/user-login";
    String USER_LOGOUT = "/api/logout";
    String USER_REGISTER = "/api/user-register";
    String EXCEPTION = "/api/exception";

    String ADMIN_BASE = "/WEB-INF/adminbase.jsp";
    String CLIENT_BASE = "/WEB-INF/clientbase.jsp";
    String ERROR = "/WEB-INF/error.jsp";
    String FOOD_LIST = "/WEB-INF/foodlist.jsp";
    String USER_LIST = "/WEB-INF/userlist.jsp";
    String FOOD = "/WEB-INF/food.jsp";
    String INDEX = "/index.jsp";
    String LOGIN = "/login.jsp";
    String REGISTRATION = "/registration.jsp";
}
