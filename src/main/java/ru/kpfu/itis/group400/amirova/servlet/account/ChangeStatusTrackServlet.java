package ru.kpfu.itis.group400.amirova.servlet.account;

import ru.kpfu.itis.group400.amirova.dao.impl.database.TracksSituationsDaoDB;
import ru.kpfu.itis.group400.amirova.dao.interfaces.TracksSituationsDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/change_status_track")
public class ChangeStatusTrackServlet extends HttpServlet {

    private TracksSituationsDao tracksSituationsDao;

    @Override
    public void init() throws ServletException {
        super.init();
        tracksSituationsDao = new TracksSituationsDaoDB();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int situationId = Integer.parseInt(req.getParameter("situationId"));
        int trackId = Integer.parseInt(req.getParameter("trackId"));
        boolean approved = Boolean.parseBoolean(req.getParameter("approved"));

        tracksSituationsDao.updateApproval(situationId, trackId, approved);
        resp.sendRedirect("/situation_control?situationId=" + situationId);
    }
}