package com.epam.library.controller.command.book;

import com.epam.library.controller.command.Command;
import com.epam.library.controller.command.CommandResult;
import com.epam.library.entity.Book;
import com.epam.library.model.service.BookIService;
import com.epam.library.model.service.ServiceException;
import com.epam.library.util.constant.CommandName;
import com.epam.library.util.constant.PageLocation;
import com.epam.library.util.constant.entityconstant.BookConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@Component(CommandName.DISPLAY_BOOK)
public class BookStoreCommand implements Command {

    @Autowired
    private BookIService bookService;

    public BookStoreCommand(BookIService bookService) {
        this.bookService = bookService;
    }

    /**
     * @param request  from the jsp
     * @param response to the jsp
     * @return page which holds the information about the books to display them on the page for users
     * or visitors
     * @throws ServiceException is something wrong during the connection with database
     */
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        List<Book> books = bookService.getAll();

        String backward = request.getParameter("backward");
        String forward = request.getParameter("forward");
        int startingCount = 0;
        int nextTen = 10;
        List<Book> bookList;
        String page = PageLocation.BOOK_STORE;

        if (Objects.equals(forward, "null") && Objects.equals(backward, "null")) {
            bookList = bookService.getAllByPage(startingCount, nextTen);
            request.setAttribute(BookConstant.BOOK_LIST, bookList);
            page = PageLocation.BOOK_STORE;
        }

        if (Objects.equals(forward, "forward")) {
            bookList = bookService.getAllByPage(startingCount, nextTen);
            startingCount = startingCount + 10;
            nextTen = nextTen + 10;
            request.setAttribute(BookConstant.BOOK_LIST, bookList);
            page = PageLocation.BOOK_STORE;
        }

        if (Objects.equals(backward, "backward")) {
            startingCount = startingCount - 10;
            nextTen = nextTen - 10;
            if (startingCount == 0) {
                bookList = bookService.getAllByPage(startingCount, nextTen);
                request.setAttribute(BookConstant.BOOK_LIST, bookList);
                page = PageLocation.BOOK_STORE;
            }
            bookList = bookService.getAllByPage(startingCount, nextTen);
            request.setAttribute(BookConstant.BOOK_LIST, bookList);
            page = PageLocation.BOOK_STORE;
        }

/*        if (books == null || books.isEmpty()) {
            request.setAttribute(DiffConstant.BOOK_LIST_ERROR, DiffConstant.BOOK_LIST_ERROR);
            page = PageLocation.SERVER_ERROR_PAGE;
        } else {
            request.setAttribute(BookConstant.BOOK_LIST, books);
            page = PageLocation.BOOK_STORE;
        }*/

        return new CommandResult(page);
    }
}
