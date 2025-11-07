package ru.kpfu.itis.group400.amirova.servlet.account;

import ru.kpfu.itis.group400.amirova.service.SituationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserSituationDelete", urlPatterns = "/user_situation_delete")
public class UserSituationDeleteServlet extends HttpServlet {

    private SituationService situationService;

    @Override
    public void init() throws ServletException {
        super.init();
        situationService = (SituationService) getServletContext().getAttribute("situationService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String situationId = req.getParameter("situationId");
        if (situationId != null && !situationId.isEmpty()) {
            situationService.deleteSituation(Integer.parseInt(situationId));
        }
        resp.sendRedirect("/my_situation");
    }
}