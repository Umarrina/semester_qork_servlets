package ru.kpfu.itis.group400.amirova.servlet.account;

import com.cloudinary.Cloudinary;
import ru.kpfu.itis.group400.amirova.dto.UserDto;
import ru.kpfu.itis.group400.amirova.service.UserService;
import ru.kpfu.itis.group400.amirova.util.EmailValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@WebServlet(name = "MyAccountChange", urlPatterns = "/my_account_change")
@MultipartConfig(
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 10 * 1024 * 1024
)
public class MyAccountChangeServlet extends HttpServlet {

    private UserService userService;
    private Cloudinary cloudinary;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = (UserService) getServletContext().getAttribute("userService");
        cloudinary = (Cloudinary) getServletContext().getAttribute("cloudinary");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        UserDto user = (UserDto) session.getAttribute("user");

        req.setAttribute("currentUsername", user.getUsername());
        req.setAttribute("currentFirstName", user.getFirstName());
        req.setAttribute("currentLastName", user.getLastName());
        req.setAttribute("currentBio", user.getBio());

        req.getRequestDispatcher("my_account_change.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserDto user = (UserDto) session.getAttribute("user");

        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String firstName = request.getParameter("name");
        String lastName = request.getParameter("lastName");
        String bio = request.getParameter("bio");
        Part part = request.getPart("profilePhoto");

        if (!EmailValidator.isValid(email)) {
            request.setAttribute("error", "Неверный формат email");
            request.getRequestDispatcher("my_account_change.ftl").forward(request, response);
            return;
        }

        boolean emailExists = userService.getAllUsers().stream()
                .anyMatch(u -> email.equals(u.getEmail()) && !u.getId().equals(user.getId()));
        if (emailExists) {
            request.setAttribute("error", "Пользователь с таким email уже существует");
            request.getRequestDispatcher("my_account_change.ftl").forward(request, response);
            return;
        }

        UserDto userToUpdate = userService.getUserByLogin(user.getLogin()).get();
        userToUpdate.setEmail(email);
        userToUpdate.setUsername(username);
        userToUpdate.setFirstName(firstName);
        userToUpdate.setLastName(lastName);
        userToUpdate.setBio(bio);

        if (part != null && part.getSize() > 0) {
            if (checkType(part, request, response)) {
                InputStream fileContent = part.getInputStream();
                byte[] fileBytes = fileContent.readAllBytes();
                fileContent.close();

                Map<String, Object> uploadParams = new HashMap<>();
                uploadParams.put("folder", "music_system/profile_photos");

                Map<String, Object> uploadResult = cloudinary.uploader().upload(fileBytes, uploadParams);
                String profilePhotoUrl = uploadResult.get("secure_url").toString();
                userToUpdate.setProfilePhoto(profilePhotoUrl);
            } else {
                return;
            }
        }

        userService.updateUser(user.getId().toString(), userToUpdate);

        session.setAttribute("user", userToUpdate);

        response.sendRedirect("/my_account");
    }

    public boolean checkType(Part part, HttpServletRequest req,  HttpServletResponse resp) throws ServletException, IOException {
        String contentType = part.getContentType();
        Set<String> allowedTypes = Set.of("image/jpeg", "image/png", "image/gif");
        if (!allowedTypes.contains(contentType)) {
            req.setAttribute("error", "Разрешены только изображения (JPEG, PNG, GIF)");
            req.getRequestDispatcher("sign_up.ftl").forward(req, resp);
            return false;
        }
        return true;
    }
}
