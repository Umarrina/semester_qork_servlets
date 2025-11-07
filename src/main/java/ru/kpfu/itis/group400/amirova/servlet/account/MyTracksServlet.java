//package ru.kpfu.itis.group400.amirova.servlet.account;
//
//import ru.kpfu.itis.group400.amirova.dto.TrackDto;
//import ru.kpfu.itis.group400.amirova.dto.UserDto;
//import ru.kpfu.itis.group400.amirova.service.TrackService;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@WebServlet(name = "MyTracksServlet", urlPatterns = "/my_tracks")
//public class MyTracksServlet extends HttpServlet {
//
//    private TrackService trackService;
//
//    @Override
//    public void init() throws ServletException {
//        super.init();
//        trackService = (TrackService) getServletContext().getAttribute("trackService");
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        HttpSession session = req.getSession();
//        UserDto user = (UserDto) session.getAttribute("user");
//
//        if (user == null) {
//            resp.sendRedirect("/login");
//            return;
//        }
//
//        List<TrackDto> userTracks = trackService.getAllTracks().stream()
//                .filter(track -> user.getLogin().equals(track.getAuthor()))
//                .collect(Collectors.toList());
//
//        req.setAttribute("tracks", userTracks);
//        req.getRequestDispatcher("my_tracks.ftl").forward(req, resp);
//    }
//}