package com.epam.library.controller.command.administration.book;

import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.CommandResult;
import com.epam.library.entity.Book;
import com.epam.library.model.service.BookIService;
import com.epam.library.model.service.ServiceException;
import com.epam.library.util.constant.CommandName;
import com.epam.library.util.constant.DiffConstant;
import com.epam.library.util.constant.PageLocation;
import com.epam.library.util.constant.entityconstant.BookConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * EditBook 1.
 * 1 - This class will be triggered when you press the Edit button in the book table,
 */
@Component(CommandName.ADMINISTRATION_EDIT_BOOK)
public class AdministrationEditBookCommand implements Command {

    @Autowired
    private BookIService bookService;

    public AdministrationEditBookCommand(BookIService bookService) {
        this.bookService = bookService;
    }

    /**
     * @param request  from the jsp
     * @param response to the jsp
     * @return page which holds a form that has the information about a specific book to be edited
     * @throws ServiceException is something wrong during the connection with database
     */
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String page;
        String bookId = request.getParameter(BookConstant.BOOK_ID);
        if (bookId != null) {
            Optional<Book> optionalBook = bookService.getById(Long.parseLong(bookId));
            if (optionalBook.isPresent()) {
                Book book = optionalBook.get();
                request.setAttribute(BookConstant.EDIT_BOOK, book);
                page = PageLocation.ADMINISTRATION_EDIT_BOOK;

            } else {
                request.setAttribute(BookConstant.BOOK_NOT_EXIST, DiffConstant.READ_FROM_PROPERTIES);
                page = PageLocation.ADMINISTRATION_BOOK_STORE;
            }
        } else {
            page = PageLocation.ADMINISTRATION_EDIT_BOOK;
        }

        return new CommandResult(page);
    }

}
