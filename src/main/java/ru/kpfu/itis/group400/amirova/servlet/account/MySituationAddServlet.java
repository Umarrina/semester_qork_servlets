package ru.kpfu.itis.group400.amirova.servlet.account;

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
import java.sql.Timestamp;

@WebServlet(name = "MySituationAdd", urlPatterns = "/my_situation_add")
public class MySituationAddServlet extends HttpServlet {

    private SituationService situationService;

    @Override
    public void init() throws ServletException {
        super.init();
        situationService = (SituationService) getServletContext().getAttribute("situationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("my_situation_add.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        if (session == null) {
            resp.sendRedirect("/login");
            return;
        }

        UserDto user = (UserDto) session.getAttribute("user");

        String title = req.getParameter("title");
        String description = req.getParameter("description");
        Timestamp date = new Timestamp(System.currentTimeMillis());

        SituationDto situationDto = new SituationDto();
        situationDto.setUserId(user.getId());
        situationDto.setTitle(title);
        situationDto.setDescription(description);
        situationDto.setDate(date);
        situationDto.setApproved(false);

        situationService.createSituation(situationDto);
        resp.sendRedirect("/my_situation");
    }
}

