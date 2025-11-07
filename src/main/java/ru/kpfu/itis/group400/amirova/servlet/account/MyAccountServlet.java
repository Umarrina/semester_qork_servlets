package ru.kpfu.itis.group400.amirova.servlet.account;

import ru.kpfu.itis.group400.amirova.dao.impl.database.UserDaoDB;
import ru.kpfu.itis.group400.amirova.dao.interfaces.UserDao;
import ru.kpfu.itis.group400.amirova.dto.UserDto;
import ru.kpfu.itis.group400.amirova.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "MyAccount", urlPatterns = "/my_account")
public class MyAccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("/login");
            return;
        }

        UserDto user = (UserDto) session.getAttribute("user");

        req.setAttribute("profilePhoto", user.getProfilePhoto());
        req.setAttribute("username", user.getUsername());
        req.setAttribute("firstName", user.getFirstName());
        req.setAttribute("lastName", user.getLastName());
        req.setAttribute("bio", user.getBio());

        req.getRequestDispatcher("my_account.ftl").forward(req, resp);
    }
}
