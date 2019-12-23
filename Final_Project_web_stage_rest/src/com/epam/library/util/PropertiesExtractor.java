package com.epam.library.util;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class PropertiesExtractor {

	public static Properties getProperties(String propertiesFileLOCATION) {
		Properties properties = new Properties();
		try (InputStream inputStream = new FileInputStream(propertiesFileLOCATION)) {
			properties.load(inputStream);
		} catch (IOException e) {
			log.error("Unable to read Property file: " + propertiesFileLOCATION, e);
		}
		return properties;
	}
}
