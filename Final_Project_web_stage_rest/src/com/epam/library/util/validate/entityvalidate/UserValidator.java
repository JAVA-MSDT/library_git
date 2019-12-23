package com.epam.library.util.validate.entityvalidate;

import com.epam.library.util.constant.DiffConstant;
import com.epam.library.util.constant.entityconstant.UserConstant;
import com.epam.library.util.validate.DataMatcher;
import com.epam.library.util.validate.DataRegex;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class UserValidator {

	public static List<String> validateUserParameter(HttpServletRequest request) {

		List<String> validationList = new ArrayList<>();
		String name = request.getParameter(UserConstant.NAME);
		String lastName = request.getParameter(UserConstant.LAST_NAME);
		String email = request.getParameter(UserConstant.EMAIL);
		String login = request.getParameter(UserConstant.LOGIN);
		String password = request.getParameter(UserConstant.PASSWORD);

		if (name.isEmpty() || !isValidName(name)) {
			validationList.add(DiffConstant.NAME_ERROR);
		}

		if (lastName.isEmpty() || !isValidName(lastName)) {
			validationList.add(DiffConstant.LAST_NAME_ERROR);
		}

		if (email.isEmpty() || !isValidEmail(email)) {
			validationList.add(DiffConstant.EMAIL_ERROR);
		}

		if (login.isEmpty() || !isValidLogin(login)) {
			validationList.add(DiffConstant.LOGIN_ERROR);
		}

		if (password != null) { // Not null means new user, null means old user just we update his is data.
			if (password.isEmpty() || !isValidPassword(password)) {
				validationList.add(DiffConstant.PASSWORD_ERROR);
			}
		}

		return validationList;
	}

	public static boolean isValidLogin(String login) {
		return DataMatcher.isValid(DataRegex.LOGIN, login);
	}

	public static boolean isValidPassword(String password) {
		return DataMatcher.isValid(DataRegex.PASSWORD, password);
	}

	public static boolean isValidEmail(String email) {
		return DataMatcher.isValid(DataRegex.E_MAIL, email);
	}

	public static boolean isValidName(String name) {
		return DataMatcher.isValid(DataRegex.LIMITED_WORD, name);
	}
}
