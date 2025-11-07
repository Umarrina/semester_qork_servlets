package ru.kpfu.itis.group400.amirova.servlet.admin;

import ru.kpfu.itis.group400.amirova.dto.TrackDto;
import ru.kpfu.itis.group400.amirova.dto.UserDto;
import ru.kpfu.itis.group400.amirova.entity.Role;
import ru.kpfu.itis.group400.amirova.service.TrackService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminManageTracks", urlPatterns = "/admin_manage_tracks")
public class AdminManageTracksServlet extends HttpServlet {

    private TrackService trackService;

    @Override
    public void init() throws ServletException {
        super.init();
        trackService = (TrackService) getServletContext().getAttribute("trackService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserDto user = (UserDto) session.getAttribute("user");

        if (user == null || user.getRole() != Role.ADMIN) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Доступ запрещен");
            return;
        }

        List<TrackDto> tracks = trackService.getAllTracks();
        req.setAttribute("tracks", tracks);
        req.getRequestDispatcher("admin_manage_tracks.ftl").forward(req, resp);
    }
}