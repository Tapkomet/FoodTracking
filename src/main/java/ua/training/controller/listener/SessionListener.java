package ua.training.controller.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;

import static ua.training.controller.util.AppConstants.LOGGED_USERS;


public class SessionListener implements HttpSessionListener {
    public static final String USER_NAME = "userName";

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }


    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HashSet<String> loggedUsers = (HashSet<String>) httpSessionEvent
                .getSession().getServletContext()
                .getAttribute(LOGGED_USERS.label);
        String userName = (String) httpSessionEvent.getSession()
                .getAttribute(USER_NAME);
        loggedUsers.remove(userName);
        httpSessionEvent.getSession().setAttribute(LOGGED_USERS.label, loggedUsers);
    }
}
