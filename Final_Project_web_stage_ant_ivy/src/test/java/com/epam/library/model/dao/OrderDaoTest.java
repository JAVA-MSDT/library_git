package com.epam.library.model.dao;

import com.epam.library.entity.Book;
import com.epam.library.entity.Order;
import com.epam.library.entity.User;
import com.epam.library.entity.enumeration.ReadingPlace;
import com.epam.library.entity.enumeration.Role;
import com.epam.library.model.configuration.RootConfigurator;
import com.epam.library.util.DataFormatParser;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfigurator.class)
@ActiveProfiles(value = BeanNameHolder.TESTING_PROFILE)
@Slf4j
public class OrderDaoTest {

    private final static String orderDate = "2018-11-19";
    private final static String returningDate = "2018-12-15";
    private static User user;
    private static Date orderDateParser;
    private static Date returningDateParser;
    @Autowired
    private Flyway flyway;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BookDao bookDao;

    @Before
    public void setUp() throws Exception {
        flyway.migrate();

        orderDateParser = DataFormatParser.parsingDate(orderDate, this.getClass().getSimpleName(), "setUpOrderDaoTest");
        returningDateParser = DataFormatParser.parsingDate(returningDate, this.getClass().getSimpleName(), "setUpOrderDaoTest");

        user = new User(3, "Evy", "Buscombe", "ebuscombe2@example.com", "readerOne", "ae1d4464c9c22f5cede23787f89fddca", Role.READER, false);

    }

    @After
    public void tearDown() throws Exception {
        flyway.clean();
    }

    @Test
    public void getById() throws DaoException {
        Order actualOrder = new Order(1, user, orderDateParser, returningDateParser, ReadingPlace.HALL, false);
        actualOrder.setBook(bookSet());

        long order_id = 1;
        Optional<Order> optionalOrder = orderDao.getById(order_id);

        optionalOrder.ifPresent(expectedOrder -> assertEquals(expectedOrder, actualOrder));
    }

    @Test
    public void getAll() throws DaoException {
        int expectedOrderListSize = 100;
        int actualOrderListSize = orderDao.getAll().size();

        assertEquals(expectedOrderListSize, actualOrderListSize);
    }

    @Test
    public void save() throws DaoException {
        Order order = new Order(bookSet(), user, orderDateParser, returningDateParser, ReadingPlace.HALL, false);
        orderDao.save(order);

        Optional<Order> optionalOrder = orderDao.getById(101);
        optionalOrder.ifPresent(order1 -> {
            assertEquals(order, order1);

            int expectedBookSetSize = 4;
            assertEquals(expectedBookSetSize, order1.getBook().size());

            // Test that the user is correct
            assertEquals(user, order1.getUser());
        });


    }

    @Test
    public void removeById() throws DaoException {
        orderDao.removeById(40);
        int expectedListSizeAfterRemove = 99;
        int actualListSizeAfterRemove = orderDao.getAll().size();

        assertEquals(expectedListSizeAfterRemove, actualListSizeAfterRemove);

        //============================== Testing that WHEN ORDER is removed will be removed the orders which related to it from the ORDER_BOOKS table ==============================//

        // Before Removal We have a 1000 records inside order_books table
        BigInteger expectedOrdersBooksSize = new BigInteger(String.valueOf(991));
        BigInteger actualOrderBooksSize = orderDao.quantityOfOrders();

        assertEquals(expectedOrdersBooksSize, actualOrderBooksSize);
        log.info(expectedOrdersBooksSize + " : " + actualOrderBooksSize);

    }

    @Test
    public void update() throws DaoException {
        Optional<Order> orderBeforeUpdate = orderDao.getById(40);
        orderBeforeUpdate.ifPresent(order -> {
            try {
                order.setBook(bookSet());
            } catch (DaoException e) {
                log.error("Exception in update method inside " + this.getClass().getSimpleName(), e);
            }
            try {
                orderDao.update(order);
            } catch (DaoException e) {
                log.error("Exception while updating Book");
            }
        });

        Optional<Order> orderOptional = orderDao.getById(40);

        orderOptional.ifPresent(expectedOrder -> assertEquals(expectedOrder, orderBeforeUpdate.get()));
    }

    @Test
    public void findOrderByUserId() throws DaoException {

        long expectedOrderListSize = 2;
        long actualOrderListSize = orderDao.findOrderByUserId(4).size();

        assertEquals(expectedOrderListSize, actualOrderListSize);
    }

    // To use it when we insert a new order Test
    private Set<Book> bookSet() throws DaoException {
        List<Book> bookList = bookDao.getAll();
        return Set.of(
                bookList.get(4),
                bookList.get(14),
                bookList.get(40),
                bookList.get(24)
        );
    }

}