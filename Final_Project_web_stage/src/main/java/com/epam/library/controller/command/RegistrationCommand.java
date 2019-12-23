package com.epam.library.controller.command;

import com.epam.library.controller.builder.UserBuilderFromRequest;
import com.epam.library.entity.User;
import com.epam.library.model.service.ServiceException;
import com.epam.library.model.service.UserIService;
import com.epam.library.util.constant.CommandName;
import com.epam.library.util.constant.Operation;
import com.epam.library.util.constant.PageLocation;
import com.epam.library.util.validate.entityvalidate.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component(CommandName.REGISTRATION)
@Slf4j
public class RegistrationCommand implements Command {

    @Autowired
    private UserIService userService;

    private UserBuilderFromRequest builderFromRequest = new UserBuilderFromRequest();

    public RegistrationCommand(UserIService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String operation = null;
        CommandResult commandResult = new CommandResult();
        List<String> userValidation = UserValidator.validateUserParameter(request);
        if (userValidation.size() == 0) {
            User user = builderFromRequest.buildUserForInserting(request);
            try {
                userService.save(user);
                operation = Operation.INSERTED;
            } catch (ServiceException e) {
                operation = Operation.INSERT_FAIL;
                log.error("Exception While Creating a user", e);

            } finally {
                commandResult.redirect(PageLocation.LOGIN_PAGE);
            }
        } else {
            request.setAttribute(Operation.VALIDATION_LIST, userValidation);
            commandResult.forward(PageLocation.REGISTRATION_PAGE);
        }
        return commandResult;
    }
}
