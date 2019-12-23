package com.epam.library.util.validate.entityvalidate;

import com.epam.library.util.constant.DiffConstant;
import com.epam.library.util.constant.entityconstant.BookConstant;
import com.epam.library.util.validate.DataMatcher;
import com.epam.library.util.validate.DataRegex;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class BookValidator {

	public static List<String> validateBookParameter(HttpServletRequest request) {

		String name = request.getParameter(BookConstant.BOOK_NAME);
		String quantity = request.getParameter(BookConstant.BOOK_QUANTITY);

		List<String> validationList = new ArrayList<>();

		if (name.isEmpty() || !DataMatcher.isValid(DataRegex.LIMITED_WORD, name)) {
			validationList.add(DiffConstant.NAME_ERROR);
		}
		if (quantity.isEmpty() || !DataMatcher.isValid(DataRegex.POSITIVE_NUMBER_ONLY_EXCLUDE_ZERO, quantity)) {
			validationList.add(DiffConstant.QUANTITY_ERROR);
		}

		return validationList;
	}
}
