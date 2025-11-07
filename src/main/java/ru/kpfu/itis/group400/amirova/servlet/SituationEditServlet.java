package ru.kpfu.itis.group400.amirova.servlet;

import ru.kpfu.itis.group400.amirova.dao.impl.database.SituationDaoDB;
import ru.kpfu.itis.group400.amirova.dao.interfaces.SituationDao;
import ru.kpfu.itis.group400.amirova.entity.Situation;
import ru.kpfu.itis.group400.amirova.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "SituationEditServlet", urlPatterns = "/situation_edit")
public class SituationEditServlet extends HttpServlet {

    private SituationDao situationDao = new SituationDaoDB();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        int situation_id = Integer.parseInt(req.getParameter("situationId"));

        Situation situation = situationDao.getSituationById(situation_id);

        req.setAttribute("situation", situation);
        req.setAttribute("title", situation.getTitle());
        req.setAttribute("description", situation.getDescription());

        req.getRequestDispatcher("situation_edit.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        int situation_id = Integer.parseInt(req.getParameter("situationId"));

        Situation situation = situationDao.getSituationById(situation_id);

        String title = req.getParameter("title");
        String description = req.getParameter("description");

        situation.setTitle(title);
        situation.setDescription(description);

        situationDao.update(situation);

        resp.sendRedirect("/my_situation");

    }
}
