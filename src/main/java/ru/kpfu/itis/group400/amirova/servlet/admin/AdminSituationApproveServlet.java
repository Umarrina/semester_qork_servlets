package ru.kpfu.itis.group400.amirova.servlet.admin;

import ru.kpfu.itis.group400.amirova.dto.SituationDto;
import ru.kpfu.itis.group400.amirova.dto.UserDto;
import ru.kpfu.itis.group400.amirova.entity.Role;
import ru.kpfu.itis.group400.amirova.service.SituationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AdminSituationApprove", urlPatterns = "/admin_situation_approve")
public class AdminSituationApproveServlet extends HttpServlet {

    private SituationService situationService;

    @Override
    public void init() throws ServletException {
        super.init();
        situationService = (SituationService) getServletContext().getAttribute("situationService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserDto user = (UserDto) session.getAttribute("user");

        if (user == null || user.getRole() != Role.ADMIN) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Доступ запрещен");
            return;
        }

        String situationId = req.getParameter("situationId");
        if (situationId != null && !situationId.isEmpty()) {
            SituationDto situation = situationService.getSituationById(Integer.parseInt(situationId));
            if (situation != null) {
                situation.setApproved(!situation.isApproved());
                situationService.updateSituation(Integer.parseInt(situationId), situation);
            }
        }
        resp.sendRedirect("/admin_manage_situations");
    }
}