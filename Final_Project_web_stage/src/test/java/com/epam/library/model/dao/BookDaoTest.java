package com.epam.library.model.dao;

import com.epam.library.entity.Book;
import com.epam.library.model.configuration.RootConfigurator;
import com.epam.library.util.constant.BeanNameHolder;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigInteger;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfigurator.class})
@ActiveProfiles(value = BeanNameHolder.TESTING_PROFILE)
@Slf4j
public class BookDaoTest {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private Flyway flyway;

    @Before
    public void setUp() throws Exception {
        flyway.migrate();
    }

    @After
    public void tearDown() throws Exception {
        flyway.clean();
    }

    @Test
    public void getById() throws DaoException {
        long bookId = 2;
        Book actualBook = new Book(2, "White Squall", 40, 1);
        Optional<Book> expectedBook = bookDao.getById(bookId);

        expectedBook.ifPresent(expected -> log.info(expected.toString()));
        expectedBook.ifPresent(expected -> assertEquals(expected.getName(), actualBook.getName()));
    }

    @Test
    public void getAll() throws DaoException {
        int actualBooksSize = 50;
        int expectedBooksSize = bookDao.getAll().size();
        assertEquals(expectedBooksSize, actualBooksSize);


    }


    @Test
    public void getAllByPage() throws DaoException {
        int actualBooksSize = 10;
        int expectedBooksSize = bookDao.getAllByPage(0, 10).size();

        assertEquals(expectedBooksSize, actualBooksSize);

    }

    @Test
    public void save() throws DaoException {
        Book actualBook = new Book("White Book", 400, 1);
        bookDao.save(actualBook);

        Optional<Book> expectedBook = bookDao.getById(51);
        expectedBook.ifPresent(bookExpected -> {
            assertEquals(bookExpected, actualBook);

            // Empty List because new Book not in any orders yet
            int expectedOrderListSize = 0;
            assertEquals(expectedOrderListSize, bookExpected.getOrders().size());
        });

    }

    @Test
    public void removeById() throws DaoException {
        bookDao.removeById(14); // 44 is the only bok that has no relation with the order table ;)

        int actualSize = 49;
        int expectedSize = bookDao.getAll().size();

        assertEquals(expectedSize, actualSize);

        //============================== Testing that WHEN Book removed also will be removed the orders which related to it from the ORDER_BOOKS table ==============================//

        // Before Removal We have a 1000 records inside order_books table
        BigInteger expectedOrdersBooksSize = new BigInteger(String.valueOf(983));
        BigInteger actualOrderBooksSize = orderDao.quantityOfOrders();

        assertEquals(expectedOrdersBooksSize, actualOrderBooksSize);
        log.info(expectedOrdersBooksSize + " : " + actualOrderBooksSize);
    }

    @Test
    public void update() throws DaoException {
        Book actualBook = new Book(2, "Black Hawk Down", 404, 1);
        bookDao.update(actualBook);

        Optional<Book> expectedBook = bookDao.getById(2);
        expectedBook.ifPresent(expected -> assertEquals(expected.getName(), actualBook.getName()));
    }

    @Test
    public void updateQuantity() throws DaoException {
        int actualQuantity = 420;
        long bookId = 14;

        bookDao.updateQuantity(bookId, actualQuantity);
        Optional<Book> optionalBook = bookDao.getById(bookId);

        optionalBook.ifPresent(book1 -> assertEquals(book1.getQuantity(), actualQuantity));
    }

    @Test
    public void findByName() throws DaoException {
        String actualBookName = "Hero";

        long actualSize = 1;
        long expectedSize = bookDao.findByName(actualBookName)
                .stream()
                .filter(book -> book.getName().equalsIgnoreCase(actualBookName))
                .count();
        assertEquals(expectedSize, actualSize);
    }

    @Test // To test that every time updating a record in the database the version of the lock will be incremented
    public void lockingVersionTest() throws DaoException {
        Optional<Book> optionalBook = bookDao.getById(1);
        optionalBook.ifPresent(book -> {
            try {
                book.setQuantity(1000);
                bookDao.update(book);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        });

        Optional<Book> optionalBook1 = bookDao.getById(1);
        optionalBook1.ifPresent(book -> {
            long bookVersion = book.getVersion();

            assertNotEquals(bookVersion, optionalBook.get().getVersion());
        });
    }


    @Test
    public void getOrderFromBook() throws DaoException {
        Optional<Book> optionalBook = bookDao.getById(4);

        optionalBook.ifPresent(book -> {

            long expectedOrdersSize = 17;
            long actualOrdersSize = bookDao.getOrderFromBook(book).size();
            assertEquals(expectedOrdersSize, actualOrdersSize);

        });
    }

}