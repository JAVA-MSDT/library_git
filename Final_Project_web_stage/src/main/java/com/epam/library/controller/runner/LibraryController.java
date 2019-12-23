package com.epam.library.controller.runner;

import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.CommandResult;
import com.epam.library.model.configuration.RootConfigurator;
import com.epam.library.model.service.ServiceException;
import com.epam.library.util.constant.BeanNameHolder;
import com.epam.library.util.constant.PageLocation;
import com.epam.library.util.constant.RedirectTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(BeanNameHolder.WEB_SERVLET_BEAN)
@Slf4j
public class LibraryController extends HttpServlet {
    private final static String COMMAND_NAME = "command";

    private AnnotationConfigApplicationContext context;

    @Override
    public void init() throws ServletException {
        context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles(BeanNameHolder.DEVELOPING_PROFILE);
        context.register(RootConfigurator.class);
        context.refresh();
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        processRequest(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        processRequest(httpServletRequest, httpServletResponse);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String command = request.getParameter(COMMAND_NAME);

        try {
            Command action = (Command) context.getBean(command);
            CommandResult commandResult = action.execute(request, response);
            dispatch(request, response, commandResult);

        } catch (ServiceException e) {
            log.error("Exception in Library Controller", e);
            response.sendRedirect(PageLocation.ERROR_PAGE);
            e.printStackTrace();
        } catch (NoSuchBeanDefinitionException ns) {
            log.error("Exception in Library Controller", ns);
            response.sendRedirect(PageLocation.ERROR_PAGE);
        }


    }

    private void dispatch(HttpServletRequest request, HttpServletResponse response, CommandResult commandResult) throws ServletException, IOException {
        String page = commandResult.getPage();
        switch (commandResult.getCommandAction()) {
            case FORWARD:
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
                dispatcher.forward(request, response);
                break;
            case REDIRECT:
                response.sendRedirect(page);
                break;
            default:
                response.sendRedirect(RedirectTo.LOGIN_PAGE);
        }
    }

    @Override
    public void destroy() {
        context.close();
    }
}
