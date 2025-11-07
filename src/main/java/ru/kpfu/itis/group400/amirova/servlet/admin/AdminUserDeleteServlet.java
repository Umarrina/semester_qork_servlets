package ru.kpfu.itis.group400.amirova.servlet.admin;

import ru.kpfu.itis.group400.amirova.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminUserDelete", urlPatterns = "/admin_user_delete")
public class AdminUserDeleteServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        if (userId != null && !userId.isEmpty()) {
            userService.deleteUser(userId);
        }
        resp.sendRedirect("/admin_manage_users");
    }
}
