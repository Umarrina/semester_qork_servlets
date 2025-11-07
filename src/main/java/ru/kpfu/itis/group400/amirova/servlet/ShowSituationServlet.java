package ru.kpfu.itis.group400.amirova.servlet;

import ru.kpfu.itis.group400.amirova.dao.impl.database.SituationDaoDB;
import ru.kpfu.itis.group400.amirova.dao.interfaces.SituationDao;
import ru.kpfu.itis.group400.amirova.dto.SituationDto;
import ru.kpfu.itis.group400.amirova.dto.UserDto;
import ru.kpfu.itis.group400.amirova.entity.Situation;
import ru.kpfu.itis.group400.amirova.entity.User;
import ru.kpfu.itis.group400.amirova.service.SituationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ShowSituation", urlPatterns = "/situation")
public class ShowSituationServlet extends HttpServlet {

    private SituationService situationService;

    @Override
    public void init() throws ServletException {
        super.init();
        situationService = (SituationService) getServletContext().getAttribute("situationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserDto user = (UserDto) session.getAttribute("user");

        String situationIdParam = req.getParameter("situationId");
        if (situationIdParam == null) {
            situationIdParam = (String) session.getAttribute("situationId");
        }

        if (situationIdParam == null) {
            resp.sendRedirect("/main");
            return;
        }

        int idSituation = Integer.parseInt(situationIdParam);
        session.setAttribute("situationId", situationIdParam);

        SituationDto situation = situationService.getSituationById(idSituation);

        req.setAttribute("username", user.getUsername());
        req.setAttribute("situation", situation);
        req.setAttribute("situationId", situation.getId());

        req.getRequestDispatcher("situation.ftl").forward(req, resp);
    }
}
