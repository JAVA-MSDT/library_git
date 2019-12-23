package com.epam.library.util.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataMatcher {

	public static Matcher matches(String regex, String toBeMatched) {
		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(toBeMatched);
	}

	public static boolean isValid(String regex, String data) {
		Matcher matcher = matches(regex, data);
		return matcher.matches();
	}

}
