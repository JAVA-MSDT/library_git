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
import java.util.List;

/**
 * Book Store in jsp page which holds a table with all the books inside the database.
 */
@Component(CommandName.ADMINISTRATION_BOOK_STORE)
public class AdministrationBookStoreCommand implements Command {
    @Autowired
    private BookIService bookService;

    public AdministrationBookStoreCommand(BookIService bookService) {
        this.bookService = bookService;
    }


    /**
     * @param request  from the jsp
     * @param response to the jsp
     * @return page which holds the information about the books to display them on the page, for the
     * administration to control them, adding or editing
     * @throws ServiceException is something wrong during the connection with database
     */
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        List<Book> books = bookService.getAll();
        String page;
        if (books == null || books.isEmpty()) {
            request.setAttribute(DiffConstant.BOOK_LIST_ERROR, DiffConstant.BOOK_LIST_ERROR);
            page = PageLocation.SERVER_ERROR_PAGE;
        } else {
            request.setAttribute(BookConstant.BOOK_LIST, books);
            page = PageLocation.ADMINISTRATION_BOOK_STORE;
        }

        return new CommandResult(page);
    }


}
