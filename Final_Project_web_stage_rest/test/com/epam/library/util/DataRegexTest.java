package com.epam.library.util;

import com.epam.library.util.validate.DataMatcher;
import com.epam.library.util.validate.DataRegex;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;

/**
 * To check our regex syntax
 */
public class DataRegexTest {

    @Test
    public void e_mailRegex() {
        String email = "serenitydiver@hotmail.com";
        Matcher matcher = DataMatcher.matches(DataRegex.E_MAIL, email);

        Assert.assertTrue(matcher.matches());

    }

    @Test
    public void loginRegex() {
        String login = "finalfin";
        Matcher matcher = DataMatcher.matches(DataRegex.LOGIN, login);
        Assert.assertTrue(matcher.matches());
    }

    @Test
    public void positiveNumberRegex() {
        String positiveNumber = "20";
        Matcher matcher = DataMatcher.matches(DataRegex.POSITIVE_NUMBER_ONLY_EXCLUDE_ZERO, positiveNumber);
        Assert.assertTrue(matcher.matches());
    }

    @Test
    public void anyNumberRegex() {
        String number = "0";
        Matcher matcher = DataMatcher.matches(DataRegex.ANY_NUMBER, number);

        Assert.assertTrue(matcher.matches());
    }

    @Test
    public void passwordRegex() {
        String password = "4400@jCkmf";
        Matcher matcher = DataMatcher.matches(DataRegex.PASSWORD, password);

        Assert.assertTrue(matcher.matches());
    }

    @Test
    public void wordAnyLanguageRegex() {
        String word = "سامى";
        Matcher matcher = DataMatcher.matches(DataRegex.WORDS_ANY_LANGUAGE, word);

        Assert.assertTrue(matcher.matches());
    }

    @Test
    public void limitedWordRegex() {
        String word = "final Test 20";
        Matcher matcher = DataMatcher.matches(DataRegex.LIMITED_WORD, word);
        Assert.assertTrue(matcher.matches());
    }
}
