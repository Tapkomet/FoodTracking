package ua.training.controller.util;

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
    USER_LOGIN("/api/user-login"),
    USER_LOGOUT("/api/logout"),
    USER_REGISTER("/api/user-register"),
    EXCEPTION("/api/exception"),

    ADMIN_BASE("/WEB-INF/adminbase.jsp"),
    CLIENT_BASE("/WEB-INF/clientbase.jsp"),
    ERROR("/WEB-INF/error.jsp"),
    FOOD_LIST("/WEB-INF/foodlist.jsp"),
    USER_LIST("/WEB-INF/userlist.jsp"),
    FOOD("/WEB-INF/food.jsp"),
    INDEX("/index.jsp"),
    LOGIN("/login.jsp"),
    REGISTRATION("/registration.jsp");

    public final String label;

    Path(String label) {
        this.label = label;
    }
}
