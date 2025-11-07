package ru.kpfu.itis.group400.amirova.servlet;

import ru.kpfu.itis.group400.amirova.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/checkLogin")
public class CheckLoginServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        String login = req.getParameter("login");
        boolean isUserExist = userService.getUserByLogin(login).isPresent();

        if (isUserExist) {
            resp.getWriter().write("exist");
        } else {
            resp.getWriter().write("free");
        }
    }
}
