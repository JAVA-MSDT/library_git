package com.epam.library.controller.command.commoncommand;

import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.CommandResult;
import com.epam.library.model.service.ServiceException;
import com.epam.library.util.constant.CommandName;
import com.epam.library.util.constant.PageLocation;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component(CommandName.CHANGE_LANGUAGE)
public class LanguageCommand implements Command {
    private final static String LANGUAGE_PARAMETER = "language";
    private final static String LANGUAGE_ATTRIBUTE = "language";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        String local = request.getParameter(LANGUAGE_PARAMETER);
        session.setAttribute(LANGUAGE_ATTRIBUTE, local);
        return new CommandResult(PageLocation.MAIN_PAGE);
    }
}
