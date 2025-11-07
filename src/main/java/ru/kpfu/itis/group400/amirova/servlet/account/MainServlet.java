package ru.kpfu.itis.group400.amirova.servlet.account;

import ru.kpfu.itis.group400.amirova.dao.impl.database.SituationDaoDB;
import ru.kpfu.itis.group400.amirova.dao.interfaces.SituationDao;
import ru.kpfu.itis.group400.amirova.dto.SituationDto;
import ru.kpfu.itis.group400.amirova.entity.Situation;
import ru.kpfu.itis.group400.amirova.service.SituationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "MainServlet", urlPatterns = "/main")
public class MainServlet extends HttpServlet {

    private SituationService situationService;

    @Override
    public void init() throws ServletException {
        super.init();
        situationService = (SituationService) getServletContext().getAttribute("situationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<SituationDto> situations = situationService.getApprovedSituations();
        req.setAttribute("situations", situations);
        req.getRequestDispatcher("main.ftl").forward(req, resp);
    }
}
