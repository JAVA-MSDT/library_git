package com.epam.library.controller.command.commoncommand;

import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.CommandResult;
import com.epam.library.entity.User;
import com.epam.library.entity.enumeration.Role;
import com.epam.library.model.service.ServiceException;
import com.epam.library.model.service.UserIService;
import com.epam.library.util.constant.CommandName;
import com.epam.library.util.constant.DiffConstant;
import com.epam.library.util.constant.PageLocation;
import com.epam.library.util.constant.entityconstant.UserConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component(CommandName.ADMINISTRATION_SORT_USER)
public class AdministrationSortUserCommand implements Command {
    private static final String SORT_CRITERIA = "type";
    @Autowired
    private UserIService userService;

    public AdministrationSortUserCommand(UserIService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        User user = (User) request.getSession(false).getAttribute(UserConstant.USER_ATTRIBUTE);
        String sortCriteria = request.getParameter(SORT_CRITERIA);

        List<User> userList = sortResult(sortCriteria, user);

        String page;
        if (userList == null || userList.isEmpty()) {
            request.setAttribute(DiffConstant.ADMINISTRATION_SORT_USER_LIST_ERROR, DiffConstant.ADMINISTRATION_SORT_USER_LIST_ERROR);
            page = PageLocation.SERVER_ERROR_PAGE;
        } else {

            request.setAttribute(UserConstant.USER_LIST, userList);
            page = PageLocation.ADMINISTRATION_USER_LIST;
        }

        return new CommandResult(page);
    }

    /**
     * @param sortCriteria depends on it the user list will be sorted
     * @param user         to check if it is role admin or librarian, each one will return different
     *                     user list, because of librarian is not allowed to see admins or other librarian
     * @return return user list depends on the user role, because of librarian is not allowed to
     * see admins or other librarian
     * @throws ServiceException is something wrong happens during the query executing
     */
    private List<User> sortResult(String sortCriteria, User user) throws ServiceException {
        List<User> userList = null;

        if (sortCriteria.equalsIgnoreCase(UserConstant.NAME)) {
            if (user.getRole().equals(Role.ADMIN)) {
                userList = userService.adminSortUsersByName();
            } else {
                userList = userService.sortUsersByName();
            }
        } else if (sortCriteria.equalsIgnoreCase(UserConstant.EMAIL)) {
            if (user.getRole().equals(Role.ADMIN)) {
                userList = userService.adminSortUsersByEmail();
            } else {
                userList = userService.sortUsersByEmail();
            }
        }

        return userList;
    }
}
