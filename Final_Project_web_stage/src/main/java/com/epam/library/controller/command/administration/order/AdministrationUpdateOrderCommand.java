package com.epam.library.controller.command.administration.order;

import com.epam.library.controller.builder.OrderBuilderFromRequest;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.CommandResult;
import com.epam.library.entity.Book;
import com.epam.library.entity.Order;
import com.epam.library.entity.User;
import com.epam.library.model.service.BookIService;
import com.epam.library.model.service.OrderIService;
import com.epam.library.model.service.ServiceException;
import com.epam.library.model.service.UserIService;
import com.epam.library.util.constant.CommandName;
import com.epam.library.util.constant.Operation;
import com.epam.library.util.constant.PageLocation;
import com.epam.library.util.constant.RedirectTo;
import com.epam.library.util.constant.entityconstant.OrderConstant;
import com.epam.library.util.constant.entityconstant.UserConstant;
import com.epam.library.util.validate.entityvalidate.OrderValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component(CommandName.ADMINISTRATION_UPDATE_ORDER)
@Slf4j
public class AdministrationUpdateOrderCommand implements Command {

    private final static String TRUE = "true";
    private final static String FALSE = "false";

    @Autowired
    private OrderIService orderService;
    @Autowired
    private BookIService bookService;
    @Autowired
    private UserIService userService;
    private OrderBuilderFromRequest builderFromRequest = new OrderBuilderFromRequest();

    public AdministrationUpdateOrderCommand(OrderIService orderService, BookIService bookService, UserIService userService) {
        this.orderService = orderService;
        this.bookService = bookService;
        this.userService = userService;
    }

    /**
     * @param response to jsp
     * @param request  from jsp
     * @return page depends on if the order is it for editing or for saving, checking if the
     * bookReturned is false we will update the existing order,
     * if the bookReturned is true we will update the quantity of the book which returned by 1
     * then removing the order from the list.
     */
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        CommandResult commandResult = null;
        String orderId = request.getParameter(OrderConstant.ORDER_ID);
        String bookReturned = request.getParameter(OrderConstant.BOOK_RETURNED);
        String bookId = request.getParameter(OrderConstant.BOOK_ID);
        String userId = request.getParameter(UserConstant.ID);
        System.out.println("Book Id: " + bookId + " User Id: " + userId + " Order Id: " + orderId);
        try {
            Optional<Book> book = bookService.getById(Long.parseLong(bookId));
            Set<Book> books = new HashSet<>();
            Optional<User> user = userService.getById(Long.parseLong(userId));

            if (book.isPresent() && user.isPresent()) {
                books.add(book.get());
                if (orderId != null && bookReturned.trim().equalsIgnoreCase(FALSE) && !orderId.isEmpty()) {
                    commandResult = updateOrder(request, books, user.get());
                } else if (orderId != null && bookReturned.trim().equalsIgnoreCase(TRUE) && !orderId.isEmpty()) {
                    commandResult = removeOrder(orderId, bookId);
                } else {
                    commandResult = insertOrder(request, books, user.get());
                }
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        return commandResult;
    }


    /**
     * In case the order parameter validation fail we will forward the request with a message to the edit
     * order page,
     * In case of the parameter validation pass we will update the specified order then send redirect to
     * edit order page.
     *
     * @param request extract the order parameter for validation then building the order object to update it
     * @return commandResult
     */
    private CommandResult updateOrder(HttpServletRequest request, Set<Book> book, User user) {

        String operation = null;
        CommandResult commandResult = new CommandResult();
        List<String> orderValidation = OrderValidator.validateOrderParameter(request);
        if (orderValidation.size() == 0) {
            Order order = builderFromRequest.buildToUpdate(request, book, user);

            try {
                orderService.update(order);
                operation = Operation.UPDATED;
            } catch (ServiceException e) {
                operation = Operation.UPDATE_FAIL;
                log.error("Exception at updateOrder method inside AdministrationUpdateOrderCommand class: ", e);
            } finally {
                commandResult.redirect(RedirectTo.ADMINISTRATION_EDIT_ORDER_PAGE + Operation.OPERATION_STATUS + operation);
            }
        } else {
            request.setAttribute(Operation.VALIDATION_LIST, orderValidation);
            commandResult.forward(PageLocation.ADMINISTRATION_EDIT_ORDER);
        }
        return commandResult;
    }

    /**
     * In case the order parameter validation fail we will forward the request with a message to the edit
     * order page,
     * In case of the parameter validation pass we will insert the new order into the database then send redirect to
     * edit order page.
     *
     * @param request extract the book parameter for validation then building the book object to update it
     * @return commandResult
     */
    private CommandResult insertOrder(HttpServletRequest request, Set<Book> book, User user) {

        String operation = null;
        CommandResult commandResult = new CommandResult();
        List<String> orderValidation = OrderValidator.validateOrderParameter(request);
        if (orderValidation.size() == 0) {
            Order order = builderFromRequest.buildToAdd(request, book, user);
            try {
                orderService.confirmUserOrder(order, book, bookService);
                operation = Operation.INSERTED;
            } catch (ServiceException e) {
                operation = Operation.INSERT_FAIL;
                log.error("Exception at insertOrder method inside AdministrationUpdateOrderCommand class: ", e);
            } finally {
                commandResult.redirect(RedirectTo.ADMINISTRATION_EDIT_ORDER_PAGE + Operation.OPERATION_STATUS + operation);
            }
        } else {
            request.setAttribute(Operation.VALIDATION_LIST, orderValidation);
            commandResult.forward(PageLocation.ADMINISTRATION_EDIT_ORDER);
        }
        return commandResult;
    }

    /**
     * @param orderId to be removed
     * @param bookId  to get the returning book and updated it by 1 after removing the order
     * @return commandResult
     */
    private CommandResult removeOrder(String orderId, String bookId) {
        String operation = null;
        CommandResult commandResult = new CommandResult();
        try {
            orderService.administrationOrderRemoval(orderId, bookId, bookService);
            operation = Operation.REMOVED;
        } catch (ServiceException e) {
            operation = Operation.REMOVE_FAIL;
            log.error("Exception at removeOrder method inside AdministrationUpdateOrderCommand class: ", e);
        } finally {
            commandResult.redirect(RedirectTo.ADMINISTRATION_EDIT_ORDER_PAGE + Operation.OPERATION_STATUS + operation);
        }
        return commandResult;
    }

}
