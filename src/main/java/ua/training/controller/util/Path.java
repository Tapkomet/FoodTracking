package ua.training.controller.util;

/**
 * Contains all the paths possible during the app's work
 */
public enum Path {
    CLIENT("/api/client"),
    ADMIN("/api/admin"),
    ADMIN_USERS("/api/admin/users"),
    ADMIN_EDIT_USER("/api/admin/users/edit"),
    CLIENT_FOODS("/api/client/food/getAll"),
    CLIENT_FOOD("/api/client/food/getOne"),
    CLIENT_ADD_FOOD("/api/client/food/add"),
    CLIENT_EDIT_FOOD("/api/client/food/edit"),
    CLIENT_DELETE_FOOD("/api/client/food/delete"),
    USER_LOGIN_JSP("/api/user-login"),
    USER_LOGOUT("/api/logout"),
    USER_REGISTER("/api/user-register"),
    EXCEPTION("/api/exception"),

    ADMIN_BASE_JSP("/WEB-INF/adminbase.jsp"),
    CLIENT_BASE_JSP("/WEB-INF/clientbase.jsp"),
    ERROR_JSP("/WEB-INF/error.jsp"),
    FOOD_LIST_JSP("/WEB-INF/foodlist.jsp"),
    USER_LIST_JSP("/WEB-INF/userlist.jsp"),
    FOOD_JSP("/WEB-INF/food.jsp"),
    INDEX_JSP("/index.jsp"),
    LOGIN_JSP("/login.jsp"),
    REGISTRATION_JSP("/registration.jsp");

    public final String label;

    Path(String label) {
        this.label = label;
    }
}
