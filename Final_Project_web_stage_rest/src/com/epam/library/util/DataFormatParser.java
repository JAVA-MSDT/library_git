package com.epam.library.util;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class DataFormatParser {
	private static final String DATA_FORMAT = "yyyy-MM-dd";
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat(DATA_FORMAT);

	public static Date parsingDate(String dateToParse, String className, String methodName) {
		Date date = null;
		try {
			date = DATE_FORMAT.parse(dateToParse);
		} catch (ParseException e) {
			log.error("Exception During parsing the date inside the Method: " + methodName + " inside Class: "
					+ className);
			e.printStackTrace();
		}
		return date;
	}
}
