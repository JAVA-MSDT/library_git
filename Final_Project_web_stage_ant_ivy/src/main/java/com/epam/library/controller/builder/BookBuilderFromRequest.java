package com.epam.library.controller.builder;

import com.epam.library.entity.Book;
import com.epam.library.util.constant.entityconstant.BookConstant;

import javax.servlet.http.HttpServletRequest;

public class BookBuilderFromRequest {
    /**
     * To help build the book for the update in the AdministrationUpdateBookCommand class
     *
     * @param request to extract the book info from a form
     * @return book after extracting it's information
     */
    public Book buildBookToUpdate(HttpServletRequest request) {
        String id = request.getParameter(BookConstant.BOOK_ID);
        String name = request.getParameter(BookConstant.BOOK_NAME);
        String quantity = request.getParameter(BookConstant.BOOK_QUANTITY);

        return new Book(Long.parseLong(id), name, Integer.parseInt(quantity));
    }

    /**
     * To help building the book for the update in the AdministrationUpdateBookCommand class
     *
     * @param request to extract the book info from a form to add it in the database, because of
     *                we have the id in the database auto increment we will not receive it from the request
     * @return book after extracting it's information
     */
    public Book buildBookToAdd(HttpServletRequest request) {

        String name = request.getParameter(BookConstant.BOOK_NAME);
        String quantity = request.getParameter(BookConstant.BOOK_QUANTITY);

        return new Book(name, Integer.parseInt(quantity));
    }
}
