package ru.kpfu.itis.group400.amirova.servlet.account;

import ru.kpfu.itis.group400.amirova.service.TrackService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserTrackDelete", urlPatterns = "/user_track_delete")
public class UserTrackDeleteServlet extends HttpServlet {

    private TrackService trackService;

    @Override
    public void init() throws ServletException {
        super.init();
        trackService = (TrackService) getServletContext().getAttribute("trackService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String trackId = req.getParameter("trackId");
        if (trackId != null && !trackId.isEmpty()) {
            trackService.deleteTrack(Integer.parseInt(trackId));
        }
        resp.sendRedirect("/my_tracks");
    }
}