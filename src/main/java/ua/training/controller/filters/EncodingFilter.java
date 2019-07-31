package ua.training.controller.filters;

import javax.servlet.*;
import java.io.IOException;

import static ua.training.controller.util.AppConstants.*;

public class EncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }


    /**
     * Implementation of encoding filter. Ensures
     * UTF-8 encoding for multi-language support.
     *
     * @author Roman Kobzar
     * @see javax.servlet.Filter
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setContentType(TYPE_TEXT_HTML.label);
        servletResponse.setCharacterEncoding(UTF8.label);
        servletRequest.setCharacterEncoding(UTF8.label);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
