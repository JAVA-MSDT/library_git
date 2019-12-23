package com.epam.library.controller.builder;

import com.epam.library.entity.Book;
import com.epam.library.entity.Order;
import com.epam.library.entity.User;
import com.epam.library.entity.enumeration.ReadingPlace;
import com.epam.library.util.DataFormatParser;
import com.epam.library.util.constant.entityconstant.OrderConstant;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Set;

public class OrderBuilderFromRequest {

    /**
     * To help build the order for the update in the AdministrationUpdateOrderCommand class
     *
     * @param request to extract the order info from the form
     * @return order after extracting it's information
     */
    public Order buildToUpdate(HttpServletRequest request, Set<Book> book, User user) {
        String orderId = request.getParameter(OrderConstant.ORDER_ID);
        String bookReturned = request.getParameter(OrderConstant.BOOK_RETURNED);

        Order order = buildToAdd(request, book, user);
        order.setOrderId(Long.parseLong(orderId));
        order.setBookReturned(Boolean.parseBoolean(bookReturned.trim()));

        return order;
    }

    /**
     * To help building the order for the update in the AdministrationUpdateOrderCommand class
     *
     * @param request to extract the order info from the form
     * @return order after extracting it's information
     */
    public Order buildToAdd(HttpServletRequest request, Set<Book> book, User user) {

        String orderDate = request.getParameter(OrderConstant.ORDER_DATE);
        String returningDate = request.getParameter(OrderConstant.RETURNING_DATE);
        String readingPlace = request.getParameter(OrderConstant.READING_PLACE);

        Date orderDateFromRequest = DataFormatParser.parsingDate(orderDate, this.getClass().getSimpleName(), "buildToAdd");
        Date returningDateFromRequest = DataFormatParser.parsingDate(returningDate, this.getClass().getSimpleName(), "buildToAdd");

        return new Order(book, user, orderDateFromRequest, returningDateFromRequest, ReadingPlace.valueOf(readingPlace));
    }

    /**
     * @param request from ConfirmOrderCommand
     * @param book    which the user chose it
     * @param user    of the user that has ordered the book
     * @return order to save it
     */
    public Order userOrder(HttpServletRequest request, Set<Book> book, User user) {

        String orderDate = request.getParameter(OrderConstant.ORDER_DATE);
        String returningDate = request.getParameter(OrderConstant.RETURNING_DATE);
        String readingPlace = request.getParameter(OrderConstant.READING_PLACE);

        Date orderDateFromRequest = DataFormatParser.parsingDate(orderDate, this.getClass().getSimpleName(), "userOrder");
        Date returningDateFromRequest = DataFormatParser.parsingDate(returningDate, this.getClass().getSimpleName(), "userOrder");


        return new Order(book, user, orderDateFromRequest, returningDateFromRequest, ReadingPlace.valueOf(readingPlace));
    }

}
