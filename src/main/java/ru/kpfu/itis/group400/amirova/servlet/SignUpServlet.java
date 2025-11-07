package ru.kpfu.itis.group400.amirova.servlet;

import com.cloudinary.Cloudinary;
import ru.kpfu.itis.group400.amirova.dao.impl.database.UserDaoDB;
import ru.kpfu.itis.group400.amirova.dto.UserDto;
import ru.kpfu.itis.group400.amirova.entity.Role;
import ru.kpfu.itis.group400.amirova.entity.User;
import ru.kpfu.itis.group400.amirova.dao.interfaces.UserDao;
import ru.kpfu.itis.group400.amirova.service.UserService;
import ru.kpfu.itis.group400.amirova.util.EmailValidator;
import ru.kpfu.itis.group400.amirova.util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@WebServlet(name = "SignUpServlet", urlPatterns = "/sign_up")
@MultipartConfig(
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 10 * 1024 * 1024
)
public class SignUpServlet extends HttpServlet {

    private UserService userService;
    private Cloudinary cloudinary;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = (UserService) getServletContext().getAttribute("userService");
        cloudinary = (Cloudinary) getServletContext().getAttribute("cloudinary");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("sign_up.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //registration
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String firstName = request.getParameter("name");
        String lastName = request.getParameter("lastName");
        String bio = request.getParameter("bio");
        Part part = request.getPart("profilePhoto");

        boolean userExists = userService.getUserByLogin(login).isPresent();

        if (checkType(part, request, response)) {
            if (!userExists) {

                InputStream fileContent = part.getInputStream();
                byte[] fileBytes = fileContent.readAllBytes();
                fileContent.close();

                Map<String, Object> uploadParams = new HashMap<>();
                uploadParams.put("folder", "music_system/profile_photos");

                Map<String, Object> uploadResult = cloudinary.uploader().upload(fileBytes, uploadParams);
                String profilePhotoUrl = uploadResult.get("secure_url").toString();

                UserDto userDto = new UserDto();
                userDto.setLogin(login);
                userDto.setPassword(PasswordUtil.encrypt(password));
                userDto.setEmail(email);
                userDto.setUsername(username);
                userDto.setFirstName(firstName);
                userDto.setLastName(lastName);
                userDto.setBio(bio);
                userDto.setProfilePhoto(profilePhotoUrl);
                userDto.setRole(Role.USER);
                if (!EmailValidator.isValid(email)) {
                    request.setAttribute("error", "Неверный формат email");
                    request.getRequestDispatcher("sign_up.ftl").forward(request, response);
                    return;
                }

                boolean emailExists = userService.getAllUsers().stream()
                        .anyMatch(u -> email.equals(u.getEmail()));
                if (emailExists) {
                    request.setAttribute("error", "Пользователь с таким email уже существует");
                    request.getRequestDispatcher("sign_up.ftl").forward(request, response);
                    return;
                }

                userService.createUser(userDto);
                response.sendRedirect("/login");
            } else {
                request.setAttribute("error", "Пользователь с таким логином уже существует");
                request.getRequestDispatcher("sign_up.ftl").forward(request, response);
            }
        }
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
