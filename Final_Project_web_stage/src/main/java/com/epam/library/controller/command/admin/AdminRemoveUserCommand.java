package com.epam.library.controller.command.admin;

import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.CommandResult;
import com.epam.library.entity.User;
import com.epam.library.model.service.ServiceException;
import com.epam.library.model.service.UserIService;
import com.epam.library.util.constant.CommandName;
import com.epam.library.util.constant.Operation;
import com.epam.library.util.constant.RedirectTo;
import com.epam.library.util.constant.entityconstant.UserConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Component(CommandName.ADMIN_REMOVE_USER)
@Slf4j
public class AdminRemoveUserCommand implements Command {

    @Autowired
    private UserIService userService;

    public AdminRemoveUserCommand(UserIService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String operation;
        CommandResult commandResult = new CommandResult();
        String userId = request.getParameter(UserConstant.ID);
        long idUser = Long.parseLong(userId);
        List<User> users = userService.getAll();
        Optional<User> optionalUser = userService.getById(idUser);
        try {
            optionalUser.orElseThrow(ServiceException::new);
            userService.removeById(idUser);
            operation = Operation.REMOVED;
        } catch (ServiceException e) {
            operation = Operation.REMOVE_FAIL;
            log.error("Exception at execute method inside AdminRemoveUserCommand class: ", e);
        }

        request.setAttribute(UserConstant.USER_LIST, users);
        commandResult.redirect(RedirectTo.ADMINISTRATION_USER_LIST_PAGE + Operation.OPERATION_STATUS + operation);
        return commandResult;
    }
}
