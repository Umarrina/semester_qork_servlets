package ru.kpfu.itis.group400.amirova.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/*", filterName = "Authentification")
public class AuthentificationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        // TODO check for servlets
        if (session == null
                && ((!((HttpServletRequest) request).getRequestURI().contains("login")))
                && ((!((HttpServletRequest) request).getRequestURI().contains("sign_up")))
                && ((!((HttpServletRequest) request).getRequestURI().contains("checkLogin")))
                && ((!((HttpServletRequest) request).getRequestURI().contains("main")))
                && ((!((HttpServletRequest) request).getRequestURI().contains("my_account")))) {
            ((HttpServletResponse) response).sendRedirect("/login");
        } else {
            filterChain.doFilter(request, response);
        }
      }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
