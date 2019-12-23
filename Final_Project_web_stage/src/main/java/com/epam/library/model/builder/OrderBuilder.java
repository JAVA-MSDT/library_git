package com.epam.library.model.builder;

import com.epam.library.entity.Order;
import com.epam.library.entity.enumeration.ReadingPlace;
import com.epam.library.util.constant.entityconstant.OrderConstant;
import com.epam.library.util.validate.ArgumentValidator;

import java.sql.Array;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderBuilder implements Builder<Order> {

    /**
     * @param resultSet which has the info of the order
     * @return order after extracting the date from the data base.
     * @throws SQLException if something wrong happens during the query
     */
    @Override
    public Order build(ResultSet resultSet) throws SQLException {
        ArgumentValidator.checkForNull(resultSet, "Not allow for null Result set in OrderBuilder");

        int orderId = resultSet.getInt(OrderConstant.ORDER_ID);
        int bookId = resultSet.getInt(OrderConstant.BOOK_ID);
        int userId = resultSet.getInt(OrderConstant.USER_ID);
        Date orderDate = resultSet.getDate(OrderConstant.ORDER_DATE);
        Date returningDate = resultSet.getDate(OrderConstant.RETURNING_DATE);
        Array placeFromResult = resultSet.getArray(OrderConstant.READING_PLACE);
        String[] readingPlaceFromArray = (String[]) placeFromResult.getArray();
        ReadingPlace readingPlace = ReadingPlace.valueOf(readingPlaceFromArray[0].toUpperCase());
        boolean bookReturned = resultSet.getBoolean(OrderConstant.BOOK_RETURNED);

        return null;
    }

}
