package ua.training.controller.filters;

import ua.training.controller.util.Path;
import ua.training.model.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ua.training.controller.util.Path.*;

public class AccessFilter implements Filter {

    private static final String ADMIN = "admin";
    private static final String CLIENT = "client";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        User user = (User) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String loginURI = request.getContextPath() + LOGIN_JSP.label;
        String path = request.getRequestURI();
        String roleRequired = "";
        if (path.contains(ADMIN)) roleRequired = ADMIN;
        else if (path.contains(CLIENT)) roleRequired = CLIENT;
        switch (roleRequired) {
            case ADMIN:
                if (user != null && (user.getRole() == User.ROLE.ADMIN)) {
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    response.sendRedirect(loginURI);
                }
                break;
            case CLIENT:
                if (user != null && (user.getRole() == User.ROLE.CLIENT || user.getRole() == User.ROLE.ADMIN)) {
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    response.sendRedirect(loginURI);
                }
                break;
            default:
                filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}