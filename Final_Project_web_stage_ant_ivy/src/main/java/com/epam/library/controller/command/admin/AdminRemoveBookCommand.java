package com.epam.library.controller.command.admin;

import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.CommandResult;
import com.epam.library.entity.Book;
import com.epam.library.model.service.BookIService;
import com.epam.library.model.service.ServiceException;
import com.epam.library.util.constant.CommandName;
import com.epam.library.util.constant.Operation;
import com.epam.library.util.constant.RedirectTo;
import com.epam.library.util.constant.entityconstant.BookConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Component(CommandName.ADMIN_REMOVE_BOOK)
@Slf4j
public class AdminRemoveBookCommand implements Command {

    @Autowired
    private BookIService bookService;


    public AdminRemoveBookCommand(BookIService bookService) {
        this.bookService = bookService;

    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        CommandResult commandResult = new CommandResult();
        String operation;
        String bookId = request.getParameter(BookConstant.BOOK_ID);
        long idBook = Long.parseLong(bookId);
        List<Book> books = bookService.getAll();
        Optional<Book> optionalBook = bookService.getById(idBook);
        try {
            optionalBook.orElseThrow(ServiceException::new);
            bookService.removeById(idBook);
            operation = Operation.REMOVED;
        } catch (ServiceException e) {
            operation = Operation.REMOVE_FAIL;
            log.error("Exception at execute method inside AdminRemoveBookCommand class: ", e);
        }
        request.setAttribute(BookConstant.BOOK_LIST, books);
        commandResult.redirect(RedirectTo.ADMINISTRATION_BOOK_STORE_PAGE + Operation.OPERATION_STATUS + operation);
        return commandResult;
    }
}
