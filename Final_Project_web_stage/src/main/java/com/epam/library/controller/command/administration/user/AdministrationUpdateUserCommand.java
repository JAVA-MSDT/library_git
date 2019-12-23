package com.epam.library.controller.command.administration.user;

import com.epam.library.controller.builder.UserBuilderFromRequest;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.CommandResult;
import com.epam.library.entity.User;
import com.epam.library.model.service.ServiceException;
import com.epam.library.model.service.UserIService;
import com.epam.library.util.constant.CommandName;
import com.epam.library.util.constant.Operation;
import com.epam.library.util.constant.PageLocation;
import com.epam.library.util.constant.RedirectTo;
import com.epam.library.util.constant.entityconstant.UserConstant;
import com.epam.library.util.validate.entityvalidate.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component(CommandName.ADMINISTRATION_UPDATE_USER)
@Slf4j
public class AdministrationUpdateUserCommand implements Command {

    @Autowired
    private UserIService userService;
    private UserBuilderFromRequest builderFromRequest = new UserBuilderFromRequest();

    public AdministrationUpdateUserCommand(UserIService userService) {
        this.userService = userService;
    }

    /**
     * @param response to jsp
     * @param request  from jsp
     * @return page depends on if the userId is null that is mean that the request is coming from add user
     * page so we will save the user to the database.
     * if the usrId is not null that is mean that the request coming from editing user page so we will
     * update the existing user
     */
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {

        CommandResult commandResult;
        String userId = request.getParameter(UserConstant.ID);

        if (userId != null && !userId.isEmpty()) {

            commandResult = updateUser(request);
        } else {

            commandResult = insertUser(request);
        }
        return commandResult;
    }

    /**
     * In case the user parameter validation fail we will forward the request with a message to the edit
     * user page,
     * In case the parameter validation pass we will update the specified user then send redirect to
     * edit user page.
     *
     * @param request extract the user parameter for validation then building the user object to update it
     * @return commandResult
     */
    private CommandResult updateUser(HttpServletRequest request) {
        String operation = null;
        CommandResult commandResult = new CommandResult();
        List<String> userValidation = UserValidator.validateUserParameter(request);
        if (userValidation.size() == 0) {
            User updateUser = builderFromRequest.buildUserForUpdate(request);
            try {
                userService.update(updateUser);
                operation = Operation.UPDATED;
            } catch (ServiceException e) {
                operation = Operation.UPDATE_FAIL;
                log.error("Exception at updateUser method inside AdministrationUpdateUserCommand class: ", e);
            } finally {
                commandResult.redirect(RedirectTo.ADMINISTRATION_EDIT_USER_PAGE + Operation.OPERATION_STATUS + operation);
            }
        } else {
            request.setAttribute(Operation.VALIDATION_LIST, userValidation);
            commandResult.forward(PageLocation.ADMINISTRATION_EDIT_USER);
        }
        return commandResult;
    }


    /**
     * In case the user parameter validation fail we will forward the request with a message to the edit
     * user page,
     * In case the parameter validation pass we will insert the new user into the database then send redirect to
     * edit user page.
     *
     * @param request extract the user parameter for validation then building the book object to update it
     * @return commandResult
     */
    private CommandResult insertUser(HttpServletRequest request) {
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
                log.error("Exception at insertUser method inside AdministrationUpdateUserCommand class: ", e);

            } finally {
                commandResult.redirect(RedirectTo.ADMINISTRATION_EDIT_USER_PAGE + Operation.OPERATION_STATUS + operation);
            }
        } else {
            request.setAttribute(Operation.VALIDATION_LIST, userValidation);
            commandResult.forward(PageLocation.ADMINISTRATION_EDIT_USER);
        }
        return commandResult;
    }

}
