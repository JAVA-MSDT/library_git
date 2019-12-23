package com.epam.library.entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

@Slf4j
public class BookTest {
    /**
     * Just to test the Validation annotation
     */
    private final Book book = new Book();

    @Test
    public void setId() {
        book.setId(-1);
        boolean expectedFalse = book.getId() >= 1;

        assertFalse(expectedFalse);

    }

    @Test
    public void setName() {
        book.setName("nam");
        boolean expectedFalse = book.getName().length() >= 4;

        assertFalse(expectedFalse);
    }

    @Test
    public void setQuantity() {
        book.setQuantity(-1);
        boolean expectedFalse = book.getQuantity() >= 1;

        assertFalse(expectedFalse);
    }
}