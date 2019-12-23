package com.epam.library.controller.command.user.order;

import com.epam.library.controller.builder.OrderBuilderFromRequest;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.CommandResult;
import com.epam.library.entity.Book;
import com.epam.library.entity.Order;
import com.epam.library.entity.User;
import com.epam.library.model.service.BookIService;
import com.epam.library.model.service.OrderIService;
import com.epam.library.model.service.ServiceException;
import com.epam.library.util.constant.*;
import com.epam.library.util.constant.entityconstant.BookConstant;
import com.epam.library.util.constant.entityconstant.UserConstant;
import com.epam.library.util.validate.entityvalidate.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component(CommandName.CONFIRM_ORDER)
public class ConfirmOrderCommand implements Command {

    @Autowired
    private BookIService bookService;
    @Autowired
    private OrderIService orderService;
    private OrderBuilderFromRequest builderFromRequest = new OrderBuilderFromRequest();

    public ConfirmOrderCommand(BookIService bookService, OrderIService orderService) {
        this.bookService = bookService;
        this.orderService = orderService;

    }

    /**
     * @param request  from the jsp
     * @param response to the jsp
     * @return page which confirms that order is done depends on the userSession in the library
     * using the book and user info to build order from the request.
     * @throws ServiceException if something wrong during the connection with database
     */
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        CommandResult commandResult = new CommandResult();
        User user = (User) request.getSession(false).getAttribute(UserConstant.USER_ATTRIBUTE);

        if (user != null) {
            List<String> orderValidation = OrderValidator.validateOrderParameter(request);
            if (orderValidation.size() == 0) {

                commandResult = confirmOrder(request, user);
            } else {

                request.setAttribute(Operation.VALIDATION_LIST, orderValidation);
                commandResult.forward(PageLocation.VIEW_BOOK);
            }
        }
        return commandResult;
    }

    private CommandResult confirmOrder(HttpServletRequest request, User user) throws ServiceException {
        String operation;
        CommandResult commandResult = new CommandResult();
        String bookId = request.getParameter(BookConstant.BOOK_ID);
        Optional<Book> optionalBook = bookService.getById(Long.parseLong(bookId));

        if (optionalBook.isPresent()) {
            Set<Book> books = new HashSet<>();
            books.add(optionalBook.get());

            Order order = builderFromRequest.userOrder(request, books, user);
            orderService.confirmUserOrder(order, books, bookService);
            operation = Operation.ORDER_CONFIRMED;
            commandResult.redirect(RedirectTo.BOOK_LIST_PAGE + Operation.OPERATION_STATUS + operation);
        } else {

            request.setAttribute(Operation.CONFIRM_FAIL, DiffConstant.READ_FROM_PROPERTIES);
            commandResult.forward(PageLocation.VIEW_BOOK);
        }
        return commandResult;
    }
}
