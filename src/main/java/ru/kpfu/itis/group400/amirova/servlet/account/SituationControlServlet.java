package ru.kpfu.itis.group400.amirova.servlet.account;

import ru.kpfu.itis.group400.amirova.dao.impl.database.TracksSituationsDaoDB;
import ru.kpfu.itis.group400.amirova.dao.interfaces.TracksSituationsDao;
import ru.kpfu.itis.group400.amirova.dto.SituationDto;
import ru.kpfu.itis.group400.amirova.dto.TrackDto;
import ru.kpfu.itis.group400.amirova.service.SituationService;
import ru.kpfu.itis.group400.amirova.service.TrackService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/situation_control")
public class SituationControlServlet extends HttpServlet {

    private SituationService situationService;
    private TrackService trackService;
    private TracksSituationsDao tracksSituationsDao;

    @Override
    public void init() throws ServletException {
        super.init();
        situationService = (SituationService) getServletContext().getAttribute("situationService");
        trackService = (TrackService) getServletContext().getAttribute("trackService");
        tracksSituationsDao = new TracksSituationsDaoDB();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String situationIdParam = req.getParameter("situationId");
        if (situationIdParam == null) {
            resp.sendRedirect("/my_situation");
            return;
        }

        int situationId = Integer.parseInt(situationIdParam);
        SituationDto situation = situationService.getSituationById(situationId);

        if (situation == null) {
            resp.sendRedirect("/my_situation");
            return;
        }

        List<Integer> trackIds = tracksSituationsDao.findTrackIdsBySituationId(situationId);
        List<TrackDto> tracks = trackService.getTracksByIds(trackIds);

        for (TrackDto track : tracks) {
            boolean isApproved = tracksSituationsDao.exists(situationId, track.getId(), true);
            track.setApproved(isApproved);
        }

        situation.setTracks(tracks);

        req.setAttribute("situation", situation);
        req.getRequestDispatcher("situation_control.ftl").forward(req, resp);
    }
}