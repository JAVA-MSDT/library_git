package com.epam.library.controller.command;

import com.epam.library.entity.User;
import com.epam.library.model.service.ServiceException;
import com.epam.library.model.service.UserIService;
import com.epam.library.util.PropertiesExtractor;
import com.epam.library.util.constant.CommandName;
import com.epam.library.util.constant.DiffConstant;
import com.epam.library.util.constant.PageLocation;
import com.epam.library.util.constant.entityconstant.UserConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

@Component(CommandName.LOGIN)
public class LoginCommand implements Command {

    @Autowired
    private UserIService userService;

    public LoginCommand(UserIService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String page;
        CommandResult commandResult = new CommandResult();
        HttpSession session = request.getSession();
        setBuildAndTimeStamp(session);

        String login = request.getParameter(UserConstant.LOGIN);
        String password = request.getParameter(UserConstant.PASSWORD);
        Optional<User> optionalUser = userService.findByLoginPassword(login, password);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            session.setAttribute(UserConstant.USER_ATTRIBUTE, user);
            if (!user.isBlocked()) {
                page = PageLocation.PROFILE;
            } else {
                request.setAttribute(UserConstant.BLOCK_MESSAGE, DiffConstant.READ_FROM_PROPERTIES);
                page = PageLocation.LOGIN_PAGE;
            }
        } else {
            request.setAttribute(UserConstant.INVALID_LOGIN, DiffConstant.READ_FROM_PROPERTIES);
            page = PageLocation.LOGIN_PAGE;

        }
        commandResult.forward(page);
        return commandResult;
    }

    private void setBuildAndTimeStamp(HttpSession session) {
        InputStream in = getClass().getClassLoader().getResourceAsStream("properties/buildInfo.properties");
        if (in == null)
            return;

        Properties props = PropertiesExtractor.getProperties("src/main/resources/properties/buildInfo.properties");
        String version = props.getProperty("build.version");
        String timeStamp = props.getProperty("build.timestamp");

        session.setAttribute("version", version);
        session.setAttribute("timeStamp", timeStamp);

      /*  try {
            props.load(in);
            String version = props.getProperty("build.version");
            String timeStamp = props.getProperty("build.timestamp");

            session.setAttribute("version", version);
            session.setAttribute("timeStamp", timeStamp);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }
}
