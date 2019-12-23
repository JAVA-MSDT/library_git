package com.epam.library.controller.command.administration.book;

import com.epam.library.controller.builder.BookBuilderFromRequest;
import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.CommandResult;
import com.epam.library.entity.Book;
import com.epam.library.model.service.BookIService;
import com.epam.library.model.service.ServiceException;
import com.epam.library.util.constant.CommandName;
import com.epam.library.util.constant.Operation;
import com.epam.library.util.constant.PageLocation;
import com.epam.library.util.constant.RedirectTo;
import com.epam.library.util.constant.entityconstant.BookConstant;
import com.epam.library.util.validate.entityvalidate.BookValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component(CommandName.ADMINISTRATION_UPDATE_BOOK)
@Slf4j
public class AdministrationUpdateBookCommand implements Command {

    @Autowired
    private BookIService bookService;
    private BookBuilderFromRequest builderFromRequest = new BookBuilderFromRequest();

    public AdministrationUpdateBookCommand(BookIService bookService) {
        this.bookService = bookService;
    }

    /**
     * @param request  from the jsp
     * @param response to the jsp
     * @return page depends on the bookId, if it is null that is mean the request coming from a
     * Add Book page so we will save the new book in the database.
     * if the bookId is not null that is mean the request coming from edit book page so we will
     * update the existing book.
     */
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {

        CommandResult commandResult;
        String bookId = request.getParameter(BookConstant.BOOK_ID);
        if (bookId != null && !bookId.isEmpty()) {
            commandResult = updateBook(request);

        } else {
            commandResult = insertBook(request);
        }
        return commandResult;
    }

    /**
     * In case the book parameter validation fail we will forward the request with a message to the edit
     * book page,
     * In case the parameter validation pass we will update the specified book then send redirect to
     * edit book page.
     *
     * @param request extract the book parameter for validation then building the book object to update it
     * @return commandResult
     */
    private CommandResult updateBook(HttpServletRequest request) {
        String operation = null;
        CommandResult commandResult = new CommandResult();
        List<String> bookValidation = BookValidator.validateBookParameter(request);
        if (bookValidation.size() == 0) {
            Book book = builderFromRequest.buildBookToUpdate(request);
            try {
                bookService.update(book);
                operation = Operation.UPDATED;
            } catch (ServiceException e) {
                operation = Operation.UPDATE_FAIL;
                log.error("Exception at updateBook method inside AdministrationUpdateBookCommand class: ", e);
            } finally {
                commandResult.redirect(RedirectTo.ADMINISTRATION_EDIT_BOOK_PAGE + Operation.OPERATION_STATUS + operation);
            }
        } else {
            request.setAttribute(Operation.VALIDATION_LIST, bookValidation);
            commandResult.forward(PageLocation.ADMINISTRATION_EDIT_BOOK);
        }

        return commandResult;
    }

    /**
     * In case the book parameter validation fail we will forward the request with a message to the edit
     * book page,
     * In case the parameter validation pass we will insert the new book into the database then send redirect to
     * edit book page.
     *
     * @param request extract the book parameter for validation then building the book object to update it
     * @return commandResult
     */
    private CommandResult insertBook(HttpServletRequest request) {
        String operation = null;
        CommandResult commandResult = new CommandResult();
        List<String> bookValidation = BookValidator.validateBookParameter(request);
        if (bookValidation.size() == 0) {
            Book book = builderFromRequest.buildBookToAdd(request);
            try {
                bookService.save(book);
                operation = Operation.INSERTED;
            } catch (ServiceException e) {
                operation = Operation.INSERT_FAIL;
                log.error("Exception at insertBook method inside AdministrationUpdateBookCommand class: ", e);
            } finally {
                commandResult.redirect(RedirectTo.ADMINISTRATION_EDIT_BOOK_PAGE + Operation.OPERATION_STATUS + operation);
            }
        } else {
            request.setAttribute(Operation.VALIDATION_LIST, bookValidation);
            commandResult.forward(PageLocation.ADMINISTRATION_EDIT_BOOK);
        }

        return commandResult;
    }
}
