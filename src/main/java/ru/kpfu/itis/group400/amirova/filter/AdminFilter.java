package ru.kpfu.itis.group400.amirova.filter;

import ru.kpfu.itis.group400.amirova.dto.UserDto;
import ru.kpfu.itis.group400.amirova.entity.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/admin_manage_users", "/admin_manage_situations", "/admin_manage_tracks",
        "/admin_user_delete", "/admin_track_delete", "/admin_situation_delete",
        "/admin_track_approve", "/admin_situation_approve"})
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            httpResponse.sendRedirect("/login");
            return;
        }

        UserDto user = (UserDto) session.getAttribute("user");
        if (user == null || user.getRole() != Role.ADMIN) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Доступ запрещен");
            return;
        }

        chain.doFilter(request, response);
    }
}