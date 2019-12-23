package com.epam.library.model.builder;

import com.epam.library.entity.enumeration.ReadingPlace;
import com.epam.library.model.dto.orderservice.AdministrationOrderDisplay;
import com.epam.library.util.constant.entityconstant.OrderConstant;

import java.sql.Array;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdministrationOrderBuilder implements Builder<AdministrationOrderDisplay> {
    private final static String BOOK_NAME = "book_title";
    private final static String USER_NAME = "first_name";
    private final static String USER_EMAIL = "user_email";

    @Override
    public AdministrationOrderDisplay build(ResultSet resultSet) throws SQLException {
        long orderId = resultSet.getLong(OrderConstant.ORDER_ID);
        String bookName = resultSet.getString(BOOK_NAME);
        String userName = resultSet.getString(USER_NAME);
        String userEmail = resultSet.getString(USER_EMAIL);
        Date orderDate = resultSet.getDate(OrderConstant.ORDER_DATE);
        Date returningDate = resultSet.getDate(OrderConstant.RETURNING_DATE);
        Array readingPlaceFromResult = resultSet.getArray(OrderConstant.READING_PLACE);
        String[] readingPlaceFromArray = (String[]) readingPlaceFromResult.getArray();
        ReadingPlace readingPlace = ReadingPlace.valueOf(readingPlaceFromArray[0].toUpperCase());
        boolean bookReturned = resultSet.getBoolean(OrderConstant.BOOK_RETURNED);

        return new AdministrationOrderDisplay.AdministrationOrderDisplayBuilder()
                .setId(orderId)
                .setBookName(bookName)
                .setUserName(userName)
                .setUserEmail(userEmail)
                .setOrderDate(orderDate)
                .setReturningDate(returningDate)
                .setReadingPlace(readingPlace)
                .setBookReturned(bookReturned)
                .build();
    }
}
