package com.epam.library.controller.command.commoncommand;

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
import java.util.Comparator;
import java.util.List;

@Component(CommandName.ADMINISTRATION_SORT_ORDER)
public class AdministrationSortOrderCommand implements Command {

    private static final String SORT_CRITERIA = "type";
    private final static String BOOK_NAME = "bookName";
    private final static String USER_NAME = "userName";
    private final static String USER_EMAIL = "email";
    private static final String ORDER_DATE = "orderDate";
    private final static String RETURNING_DATE = "returningDate";
    private final static String READING_PLACE = "readingPlace";

    @Autowired
    private OrderIService orderService;

    public AdministrationSortOrderCommand(OrderIService orderService) {
        this.orderService = orderService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String sortCriteria = request.getParameter(SORT_CRITERIA);
        List<AdministrationOrderDisplay> orders = orderService.administrationAllOrder();

        String page;
        if (orders == null || orders.isEmpty()) {
            request.setAttribute(DiffConstant.ADMINISTRATION_ORDER_LIST_ERROR, DiffConstant.ADMINISTRATION_ORDER_LIST_ERROR);
            page = PageLocation.SERVER_ERROR_PAGE;
        } else {
            sort(sortCriteria, orders);
            request.setAttribute(OrderConstant.ORDER_LIST, orders);
            page = PageLocation.ADMINISTRATION_ORDER_LIST;
        }


        return new CommandResult(page);
    }


    private void sort(String criteria, List<AdministrationOrderDisplay> orderDisplayList) {
        switch (criteria) {
            case BOOK_NAME:
                orderDisplayList.sort(Comparator.comparing(AdministrationOrderDisplay::getBookName));
                break;
            case USER_NAME:
                orderDisplayList.sort(Comparator.comparing(AdministrationOrderDisplay::getUserName));
                break;
            case USER_EMAIL:
                orderDisplayList.sort(Comparator.comparing(AdministrationOrderDisplay::getUserEmail));
                break;
            case ORDER_DATE:
                orderDisplayList.sort(Comparator.comparing(AdministrationOrderDisplay::getOrderDate));
                break;
            case RETURNING_DATE:
                orderDisplayList.sort(Comparator.comparing(AdministrationOrderDisplay::getReturningDate));
                break;
            case READING_PLACE:
                orderDisplayList.sort(Comparator.comparing(AdministrationOrderDisplay::getReadingPlace));
                break;
        }
    }
}
