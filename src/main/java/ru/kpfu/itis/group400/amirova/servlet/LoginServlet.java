package ru.kpfu.itis.group400.amirova.servlet;

import ru.kpfu.itis.group400.amirova.dto.UserDto;
import ru.kpfu.itis.group400.amirova.service.UserService;
import ru.kpfu.itis.group400.amirova.util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = PasswordUtil.encrypt(req.getParameter("password"));

        if (userService.validateUser(login, password)) {
            HttpSession session = req.getSession();
            session.setMaxInactiveInterval(60 * 60);

            Cookie cookie = new Cookie("user", login);
            cookie.setMaxAge(24 * 60 * 60);
            resp.addCookie(cookie);

            UserDto user = userService.getUserByLogin(login).orElse(null);
            session.setAttribute("user", user);

            resp.sendRedirect("/main");
        } else  {
            req.setAttribute("error", "Неверный пароль");
            req.getRequestDispatcher("login.ftl").forward(req, resp);
        }
    }



}
