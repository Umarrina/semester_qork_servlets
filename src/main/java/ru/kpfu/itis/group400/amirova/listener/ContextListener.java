package ru.kpfu.itis.group400.amirova.listener;

import com.cloudinary.Cloudinary;
import ru.kpfu.itis.group400.amirova.dao.impl.database.SituationDaoDB;
import ru.kpfu.itis.group400.amirova.dao.impl.database.TrackDaoDB;
import ru.kpfu.itis.group400.amirova.dao.impl.database.TracksSituationsDaoDB;
import ru.kpfu.itis.group400.amirova.dao.impl.database.UserDaoDB;
import ru.kpfu.itis.group400.amirova.dao.interfaces.SituationDao;
import ru.kpfu.itis.group400.amirova.dao.interfaces.TrackDao;
import ru.kpfu.itis.group400.amirova.dao.interfaces.TracksSituationsDao;
import ru.kpfu.itis.group400.amirova.dao.interfaces.UserDao;
import ru.kpfu.itis.group400.amirova.service.SituationService;
import ru.kpfu.itis.group400.amirova.service.TrackService;
import ru.kpfu.itis.group400.amirova.service.UserService;
import ru.kpfu.itis.group400.amirova.service.impl.SituationServiceImpl;
import ru.kpfu.itis.group400.amirova.service.impl.TrackServiceImpl;
import ru.kpfu.itis.group400.amirova.service.impl.UserServiceImpl;
import ru.kpfu.itis.group400.amirova.util.CloudinaryUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        UserDao userDao = new UserDaoDB();
        TrackDao trackDao = new TrackDaoDB();
        SituationDao situationDao = new SituationDaoDB();
        TracksSituationsDao tracksSituationsDao = new TracksSituationsDaoDB();

        Cloudinary cloudinary = CloudinaryUtil.getInstance();

        UserService userService = new UserServiceImpl(userDao);
        TrackService trackService = new TrackServiceImpl(trackDao);
        SituationService situationService = new SituationServiceImpl(situationDao);

        context.setAttribute("userService", userService);
        context.setAttribute("trackService", trackService);
        context.setAttribute("situationService", situationService);
        context.setAttribute("cloudinary", cloudinary);

    }


}