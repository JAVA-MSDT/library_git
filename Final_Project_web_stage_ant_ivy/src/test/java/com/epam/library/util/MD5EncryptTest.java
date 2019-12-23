package com.epam.library.util;

import org.junit.Assert;
import org.junit.Test;

public class MD5EncryptTest {

    private final static String TEXT = "test";

    @Test
    public void encryptPass() {
        String actual = MD5Encrypt.encrypt(TEXT);
        String expected = MD5Encrypt.encrypt(TEXT);
        Assert.assertEquals(actual, expected);
    }
}
