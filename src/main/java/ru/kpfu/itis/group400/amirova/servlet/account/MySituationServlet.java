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
import java.util.List;

@WebServlet(name = "MySituationServlet", urlPatterns = "/my_situation")
public class MySituationServlet extends HttpServlet {

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
        List<SituationDto> situations = situationService.getSituationsByUserId(user.getId());
        req.setAttribute("user_situations", situations);
        req.getRequestDispatcher("my_situation.ftl").forward(req, resp);
    }

}
