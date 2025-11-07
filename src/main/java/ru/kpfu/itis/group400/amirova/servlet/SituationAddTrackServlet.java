package ru.kpfu.itis.group400.amirova.servlet;

import ru.kpfu.itis.group400.amirova.dao.impl.database.TrackDaoDB;
import ru.kpfu.itis.group400.amirova.dao.impl.database.TracksSituationsDaoDB;
import ru.kpfu.itis.group400.amirova.dao.interfaces.TrackDao;
import ru.kpfu.itis.group400.amirova.dao.interfaces.TracksSituationsDao;
import ru.kpfu.itis.group400.amirova.dto.UserDto;
import ru.kpfu.itis.group400.amirova.entity.Track;
import ru.kpfu.itis.group400.amirova.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "SituationAddTrack", urlPatterns = "/situation_add_track")
public class SituationAddTrackServlet extends HttpServlet {

    private TrackDao trackDao = new TrackDaoDB();
    private TracksSituationsDao tracksSituationsDao = new TracksSituationsDaoDB();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String situationId = req.getParameter("situationId");

        if (situationId == null || situationId.isEmpty()) {
            situationId = (String) session.getAttribute("situationId");
        }

        if (situationId == null || situationId.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/main");
            return;
        }

        session.setAttribute("situationId", situationId);

        req.setAttribute("situationId", situationId);
        req.getRequestDispatcher("situation_add_track.ftl").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        UserDto user = (UserDto) session.getAttribute("user");

        String trackIdStr = req.getParameter("trackId");
        String situationIdStr = req.getParameter("situationId");


        if (situationIdStr == null || situationIdStr.isEmpty()) {
            situationIdStr = (String) session.getAttribute("situationId");
        }

        if (situationIdStr == null || situationIdStr.isEmpty()) {
            resp.sendRedirect( "/my_situation");
            return;
        }

        session.setAttribute("situationId", situationIdStr);

        if (trackIdStr == null || trackIdStr.isEmpty()) {
            resp.sendRedirect("/situation?situationId=" + situationIdStr);
            return;
        }

        try {
            int trackId = Integer.parseInt(trackIdStr);
            int situationId = Integer.parseInt(situationIdStr);

            boolean trackExist = trackDao.getAll().stream()
                    .anyMatch(track -> trackId == track.getId());

            if (trackExist) {
                tracksSituationsDao.save(situationId, trackId, user.getId());
            }

            String redirectUrl =  "/situation?situationId=" + situationId;
            resp.sendRedirect(redirectUrl);

        } catch (NumberFormatException e) {
            resp.sendRedirect("/situation?situationId=" + situationIdStr);
        }
    }

}
