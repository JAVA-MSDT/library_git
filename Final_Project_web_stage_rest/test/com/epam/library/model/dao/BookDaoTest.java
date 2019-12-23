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

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfigurator.class)
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
    public void getById() {
        long bookId = 2;
        Book actualBook = new Book(2, "White Squall", 40, 1);
        Optional<Book> expectedBook = bookDao.getById(bookId);

        expectedBook.ifPresent(expected -> log.info(expected.toString()));
        expectedBook.ifPresent(expected -> assertEquals(expected.getName(), actualBook.getName()));
    }

    @Test
    public void getAll() {
        int actualBooksSize = 50;
        int expectedBooksSize = bookDao.getAll().size();
        assertEquals(expectedBooksSize, actualBooksSize);


    }


    @Test
    public void getAllByPage() {
        int actualBooksSize = 10;
        int expectedBooksSize = bookDao.getAllByPage(0, 10).size();

        assertEquals(expectedBooksSize, actualBooksSize);

    }

    @Test
    public void save() {
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
    public void removeById() {
        bookDao.removeById(14); // 44 is the only bok that has no relation with the order table ;)

        int actualSize = 49;
        int expectedSize = bookDao.getAll().size();

        assertEquals(expectedSize, actualSize);

    }

    @Test
    public void update() {
        Book actualBook = new Book(2, "Black Hawk Down", 404, 1);
        bookDao.update(actualBook);

        Optional<Book> expectedBook = bookDao.getById(2);
        expectedBook.ifPresent(expected -> assertEquals(expected.getName(), actualBook.getName()));
    }

    @Test
    public void updateQuantity() {
        int actualQuantity = 420;
        long bookId = 14;

        bookDao.updateQuantity(bookId, actualQuantity);
        Optional<Book> optionalBook = bookDao.getById(bookId);

        optionalBook.ifPresent(book1 -> assertEquals(book1.getQuantity(), actualQuantity));
    }

    @Test
    public void findByName() {
        String actualBookName = "Hero";

        long actualSize = 1;
        long expectedSize = bookDao.findByName(actualBookName)
                .stream()
                .filter(book -> book.getName().equalsIgnoreCase(actualBookName))
                .count();
        assertEquals(expectedSize, actualSize);
    }

    @Test // To test that every time updating a record in the database the version of the lock will be incremented
    public void lockingVersionTest(){
        Optional<Book> optionalBook = bookDao.getById(1);
        optionalBook.ifPresent(book -> {
                book.setQuantity(1000);
                bookDao.update(book);

        });

        Optional<Book> optionalBook1 = bookDao.getById(1);
        optionalBook1.ifPresent(book -> {
            long bookVersion = book.getVersion();

            assertNotEquals(bookVersion, optionalBook.get().getVersion());
        });
    }


    @Test
    public void getOrderFromBook() {


            long expectedOrdersSize = 17;
            long actualOrdersSize = bookDao.getOrderFromBook(4).size();
            assertEquals(expectedOrdersSize, actualOrdersSize);


    }

}