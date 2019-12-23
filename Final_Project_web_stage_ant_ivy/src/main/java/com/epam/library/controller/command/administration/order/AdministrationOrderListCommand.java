package com.epam.library.controller.command.administration.order;

import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.CommandResult;
import com.epam.library.model.dto.orderservice.AdministrationOrderDisplay;
import com.epam.library.model.service.OrderIService;
import com.epam.library.model.service.ServiceException;
import com.epam.library.util.constant.CommandName;
import com.epam.library.util.constant.DiffConstant;
import com.epam.library.util.constant.PageLocation;
import com.epam.library.util.constant.entityconstant.OrderConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component(CommandName.ADMINISTRATION_ORDER_LIST)
public class AdministrationOrderListCommand implements Command {

    @Autowired
    private OrderIService orderService;

    public AdministrationOrderListCommand(OrderIService orderService) {
        this.orderService = orderService;
    }

    /**
     * @param request  from the jsp
     * @param response to the jsp
     * @return page which holds the information about the orders to display them on the page, for the
     * administration to control them, adding or editing
     * @throws ServiceException if something wrong during the connection with database
     */
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        List<AdministrationOrderDisplay> orders = orderService.administrationAllOrder();
        String page;
        if (orders == null || orders.isEmpty()) {
            request.setAttribute(DiffConstant.ADMINISTRATION_ORDER_LIST_ERROR, DiffConstant.ADMINISTRATION_ORDER_LIST_ERROR);
            page = PageLocation.SERVER_ERROR_PAGE;
        } else {
            request.setAttribute(OrderConstant.ORDER_LIST, orders);
            page = PageLocation.ADMINISTRATION_ORDER_LIST;
        }
        return new CommandResult(page);

    }
}
