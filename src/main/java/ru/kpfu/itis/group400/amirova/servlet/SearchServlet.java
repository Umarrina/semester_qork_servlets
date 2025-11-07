package ru.kpfu.itis.group400.amirova.servlet;

import ru.kpfu.itis.group400.amirova.dao.impl.database.TrackDaoDB;
import ru.kpfu.itis.group400.amirova.dao.interfaces.TrackDao;
import ru.kpfu.itis.group400.amirova.entity.Track;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/search")
public class SearchServlet extends HttpServlet {

    TrackDao trackDao = new TrackDaoDB();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String search = req.getParameter("search");
        List<Track> result = new ArrayList<>();
        if (search != null && !search.isBlank()) {
            String query = search.toLowerCase();
            for (Track track : trackDao.getAll()) {
                if ((track.getAuthor().toLowerCase().contains(query)
                        | track.getTitle().toLowerCase().contains(query))
                        & track.getApproved() == true) {
                    result.add(track);
                }
            }
        }
        resp.setContentType("application/json;charset=UTF-8");
        String json = result.stream()
                .map(t -> String.format("{\"id\":%d,\"author\":\"%s\",\"title\":\"%s\"}",
                        t.getId(), escape(t.getAuthor()), escape(t.getTitle())))
                .reduce((a,b)->a + "," + b)
                .map(s -> "[" + s + "]").orElse("[]");
        resp.getWriter().write(json);
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\\","\\\\").replace("\"","\\\"").replace("\n","\\n").replace("\r","\\r");
    }
}
