package com.epam.library.controller.command.book;

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

@Component(CommandName.VIEW_BOOK)
public class ViewBookCommand implements Command {

    @Autowired
    private BookIService bookService;

    public ViewBookCommand(BookIService bookService) {
        this.bookService = bookService;
    }

    /**
     * @param request  from the jsp
     * @param response to the jsp
     * @return page which hold information about a specific book to display it on a separate page
     * @throws ServiceException is something wrong during the connection with database
     */
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String page;
        String bookId = request.getParameter(BookConstant.BOOK_ID);
        Optional<Book> optionalBook = bookService.getById(Long.parseLong(bookId));
        if (optionalBook.isPresent()) {
            request.setAttribute(BookConstant.BOOK_ATTRIBUTE, optionalBook.get());
            page = PageLocation.VIEW_BOOK;
        } else {
            request.setAttribute(BookConstant.BOOK_NOT_EXIST, DiffConstant.READ_FROM_PROPERTIES);
            page = PageLocation.BOOK_STORE;
        }
        return new CommandResult(page);
    }
}
