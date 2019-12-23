package com.epam.library.controller.command.user;

import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.CommandResult;
import com.epam.library.entity.User;
import com.epam.library.model.dto.orderservice.UserOrderDisplay;
import com.epam.library.model.service.ServiceException;
import com.epam.library.model.service.UserIService;
import com.epam.library.util.constant.CommandName;
import com.epam.library.util.constant.DiffConstant;
import com.epam.library.util.constant.PageLocation;
import com.epam.library.util.constant.entityconstant.OrderConstant;
import com.epam.library.util.constant.entityconstant.UserConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component(CommandName.USER_ORDER)
public class UserOrderCommand implements Command {

    @Autowired
    private UserIService userIService;


    public UserOrderCommand(UserIService userIService) {
        this.userIService = userIService;
    }

    /**
     * @param request  from the jsp
     * @param response to the jsp
     * @return page which holds the orders of that user
     * @throws ServiceException if something wrong during the connection with database
     */
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        User user = (User) request.getSession(false).getAttribute(UserConstant.USER_ATTRIBUTE);
        List<UserOrderDisplay> userOrderDisplays = userIService.userOrders(user);

        String page;
        if (user == null) {
            request.setAttribute(DiffConstant.ADMINISTRATION_ORDER_LIST_ERROR, DiffConstant.ADMINISTRATION_ORDER_LIST_ERROR);
            page = PageLocation.SERVER_ERROR_PAGE;
        } else {

            request.setAttribute(OrderConstant.ORDER_LIST, userOrderDisplays);
            page = PageLocation.USER_ORDER;
        }


        return new CommandResult(page);
    }

}
