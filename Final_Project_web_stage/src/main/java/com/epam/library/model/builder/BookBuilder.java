package com.epam.library.model.builder;

import com.epam.library.entity.Book;
import com.epam.library.util.constant.entityconstant.BookConstant;
import com.epam.library.util.validate.ArgumentValidator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookBuilder implements RowMapper<Book> {

    /**
     * @param resultSet which has the info of the book
     * @return book after extracting the date from the data base.
     * @throws SQLException if something wrong happens during the building
     */
    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        ArgumentValidator.checkForNull(resultSet, "Not allow for null Result set in BookBuilder");

        long id = resultSet.getLong(BookConstant.BOOK_ID);
        String bookName = resultSet.getString(BookConstant.BOOK_NAME);
        int quantity = resultSet.getInt(BookConstant.BOOK_QUANTITY);

        return new Book(id, bookName, quantity);
    }
}
