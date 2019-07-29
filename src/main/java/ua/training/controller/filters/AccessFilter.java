package ua.training.controller.filters;

import ua.training.controller.util.Path;
import ua.training.model.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccessFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        User user = (User) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String loginURI = request.getContextPath() + Path.LOGIN;
        String path = request.getRequestURI();
        String roleRequired = "";
        /*
        if (path.contains("manager")) roleRequired = "manager";
        else if (path.contains("admin")) roleRequired = "admin";
        else if (path.contains("cashier")) roleRequired = "cashier";
        switch (roleRequired) {
            case "manager":
                if (user!=null&&(user.getRole() == User.ROLE.PRODUCT_MANAGER || user.getRole() == User.ROLE.SENIOR_CASHIER)) {
                    filterChain.doFilter(servletRequest, servletResponse);

                } else {
                    response.sendRedirect(loginURI);
                }
                break;
            case "admin":
                if (user!=null&&user.getRole() == User.ROLE.SENIOR_CASHIER) {
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    response.sendRedirect(loginURI);
                }
                break;
            case "cashier":
                if (user!=null&&(user.getRole() == User.ROLE.CASHIER || user.getRole() == User.ROLE.SENIOR_CASHIER)) {
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    response.sendRedirect(loginURI);
                }
                break;
            default:*/
        filterChain.doFilter(servletRequest, servletResponse);
    }


    @Override
    public void destroy() {

    }
}
