package com.epam.library.model.dao;

import com.epam.library.entity.Book;
import com.epam.library.entity.Order;
import com.epam.library.entity.User;
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

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfigurator.class)
@ActiveProfiles(BeanNameHolder.TESTING_PROFILE)
@Slf4j
public class AssociationTester {

    private final static int expectedOrderSize = 50;
    private final static int expectedUserSize = 50;
    private final static int expectedBookSize = 50;
    private static int actualOrderSize;
    private static int actualUserSize;
    private static int actualBookSize;
    private static List<Order> orderList;
    private static List<User> userList;
    private static List<Book> bookList;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private Flyway flyway;

    @Before
    public void setUp() throws DaoException {
        flyway.migrate();

        orderList = orderDao.getAll();
        userList = userDao.getAll();
        bookList = bookDao.getAll();

        actualOrderSize = orderList.size();
        actualUserSize = userList.size();
        actualBookSize = bookList.size();


    }

    @After
    public void tearDown() {
        flyway.clean();
    }


    @Test
    public void removeUser() throws DaoException {

        userDao.removeById(10);

        int expectedOrderSize = 98;
        int actualOrderSize = orderList.size();

        assertEquals(expectedOrderSize, actualOrderSize);

        assertEquals(expectedBookSize, actualBookSize);


    }

    @Test
    public void removeBook() {

    }

    @Test
    public void removeOrder() {

    }

    @Test
    public void addUser() {

    }

    @Test
    public void addOrder() {

    }

    @Test
    public void addBook() {

    }
}
