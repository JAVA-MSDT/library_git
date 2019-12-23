package com.epam.library.util;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

@Slf4j
public class PropertiesExtractor {

    /**
     * @param key                of the value that we need to read
     * @param propertiesFileName name of the properties file name
     * @return value of the key which located in the specified properties file
     */
    public static String getValueFromProperties(String key, String propertiesFileName) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(propertiesFileName);
        return resourceBundle.getString(key);
    }


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
