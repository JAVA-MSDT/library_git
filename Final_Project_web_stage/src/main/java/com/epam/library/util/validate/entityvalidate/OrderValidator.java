package com.epam.library.util.validate.entityvalidate;

import com.epam.library.util.constant.DiffConstant;
import com.epam.library.util.constant.entityconstant.OrderConstant;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class OrderValidator {

    public static List<String> validateOrderParameter(HttpServletRequest request) {
        String orderDate = request.getParameter(OrderConstant.ORDER_DATE);
        String returningDate = request.getParameter(OrderConstant.RETURNING_DATE);
        List<String> validationList = new ArrayList<>();
        if (orderDate.compareToIgnoreCase(returningDate) > 0) {
            validationList.add(DiffConstant.RETURNING_DATE_OLDER);
        }

        return validationList;
    }
}
