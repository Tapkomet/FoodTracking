package ua.training.controller.util;

public enum AppConstants {
    UTF8("UTF-8"),
    TYPE_TEXT_HTML("text/html"),
    LOGGED_USERS("loggedUsers");
    public final String label;

    AppConstants(String label) {
        this.label = label;
    }
}
