package ru.kpfu.itis.group400.amirova.servlet.account;

import com.cloudinary.Cloudinary;
import ru.kpfu.itis.group400.amirova.dto.TrackDto;
import ru.kpfu.itis.group400.amirova.dto.UserDto;
import ru.kpfu.itis.group400.amirova.service.TrackService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "TrackUpload", urlPatterns = "/track_upload")
@MultipartConfig(
        maxFileSize = 10 * 1024 * 1024,
        maxRequestSize = 20 * 1024 * 1024
)
public class TrackUploadServlet extends HttpServlet {

    private TrackService trackService;
    private Cloudinary cloudinary;

    @Override
    public void init() throws ServletException {
        super.init();
        trackService = (TrackService) getServletContext().getAttribute("trackService");
        cloudinary = (Cloudinary) getServletContext().getAttribute("cloudinary");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("track_upload.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserDto user = (UserDto) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect("/login");
            return;
        }

        String title = req.getParameter("title");
        Part audioFile = req.getPart("audioFile");

        if (title == null || title.isEmpty() || audioFile == null) {
            req.setAttribute("error", "Все поля должны быть заполнены");
            req.getRequestDispatcher("track_upload.ftl").forward(req, resp);
            return;
        }

        try {
            InputStream fileContent = audioFile.getInputStream();
            byte[] fileBytes = fileContent.readAllBytes();
            fileContent.close();

            Map<String, Object> uploadParams = new HashMap<>();
            uploadParams.put("folder", "music_system/tracks");
            uploadParams.put("resource_type", "auto");

            Map<String, Object> uploadResult = cloudinary.uploader().upload(fileBytes, uploadParams);
            String filePath = uploadResult.get("secure_url").toString();

            TrackDto trackDto = new TrackDto();
            trackDto.setTitle(title);
            trackDto.setAuthor(user.getId().toString());
            trackDto.setFilePath(filePath);
            trackDto.setApproved(false);

            trackService.createTrack(trackDto);
            resp.sendRedirect("/main");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}