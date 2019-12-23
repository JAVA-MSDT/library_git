package com.epam.library.model.dao;

import com.epam.library.entity.User;
import com.epam.library.entity.enumeration.Role;
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
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfigurator.class})
@ActiveProfiles(value = BeanNameHolder.TESTING_PROFILE)
@Slf4j
public class UserDaoTest {

    private static User actualUser;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private Flyway flyway;

    @Before
    public void setUp() throws Exception {
        flyway.migrate();
        actualUser = new User(4, "Hale", "Crusham", "hcrusham3@baidu.com", "readerTwo", "edbb1ab6745d07e90a74af84df9e6f", Role.READER, false);

    }

    @After
    public void tearDown() throws Exception {
        flyway.clean();
    }

    @Test
    public void getById() throws DaoException {

        User actualUser = new User(4, "readerTwo", "readerTwo", "readerTwo@reader.com", "readerTwo", "edbb1ab6745d07e90a74af84df9e6f", Role.READER, false);
        Optional<User> optionalUser = userDao.getById(4);

        optionalUser.ifPresent(expectedUser -> assertEquals(expectedUser, actualUser));
    }

    @Test
    public void getAll() throws DaoException {
        int expectedUserListSize = 50;
        int actualUserListSize = userDao.getAll().size();

        assertEquals(expectedUserListSize, actualUserListSize);
    }

    @Test
    public void save() throws DaoException {
        User actualUser = new User("Hale", "Crusham", "hcrusham3@baidu.com", "readerTwo", "readerTwo", Role.READER, false);
        userDao.save(actualUser);

        userDao.getById(51).ifPresent(expectedUser -> {
            assertEquals(expectedUser, actualUser);

            // Empty List because new user has no orders yet
            int expectedOrderListSize = 0;
            assertEquals(expectedOrderListSize, expectedUser.getOrders().size());
        });
    }

    @Test
    public void removeById() throws DaoException {
        userDao.removeById(4);
        int expectedUserListSizeAfterRemoval = 49;
        int actualUserListSizeAfterRemoval = userDao.getAll().size();
        assertEquals(expectedUserListSizeAfterRemoval, actualUserListSizeAfterRemoval);

        //============================== Testing that Cascade.REMOVE will remove the orders related to that user ==============================//
        int expectedOrderSize = 98;
        int actualOrderSize = orderDao.getAll().size();
        assertEquals(expectedOrderSize, actualOrderSize);

        log.info(expectedOrderSize + " : " + actualOrderSize);
    }

    @Test
    public void update() throws DaoException {
        User actualUser = new User(4, "readerTwo", "readerTwo", "readerTwo@reader.com", "readerTwo", "edbb1ab6745d07e90a74af84df9e6f", Role.READER, false);
        userDao.update(actualUser);

        userDao.getById(4).ifPresent(expectedUser -> assertEquals(expectedUser, actualUser));
    }

    @Test
    public void findByLoginAndPassword() throws DaoException {
        Optional<User> optionalUser = userDao.findByLoginAndPassword("readerTwo", "readerTwo");

        optionalUser.ifPresent(expectedUser -> assertEquals(expectedUser, actualUser));
    }

    @Test
    public void findByLogin() throws DaoException {
        Optional<User> optionalUser = userDao.findByLogin("readerTwo");

        optionalUser.ifPresent(expectedUser -> assertEquals(expectedUser, actualUser));
    }

    @Test
    public void findByEmail() throws DaoException {
        Optional<User> optionalUser = userDao.findByEmail("hcrusham3@baidu.com");

        optionalUser.ifPresent(expectedUser -> assertEquals(expectedUser, actualUser));
    }

    @Test
    public void findAllWhereRoleReader() throws DaoException {
        List<User> expectedUserWithRoleReader = userDao.findAllWhereRoleReader();
        log.info("" + expectedUserWithRoleReader.size());
        int expectedUserWithRoleReaderSize = 48;
        int actualUserWithRoleReaderSize = expectedUserWithRoleReader.size();

        assertEquals(expectedUserWithRoleReaderSize, actualUserWithRoleReaderSize);


    }

    @Test // For Librarian without the ability to see the other users with Role Admin or Librarian
    public void sortUsersByName() throws DaoException {
        List<User> users = userDao.sortUsersByName();

        assertNotEquals(users.get(4), actualUser); // Because of Sorting the user at index 4 will not be the same as if we return it from the Database
    }

    @Test // For Librarian without the ability to see the other users with Role Admin or Librarian
    public void sortUsersByEmail() throws DaoException {
        List<User> users = userDao.sortUsersByEmail();

        assertNotEquals(users.get(4), actualUser); // Because of Sorting the user at index 4 will not be the same as if we return it from the Database
    }

    @Test // For Admin Only
    public void adminSortUsersByName() throws DaoException {
        List<User> users = userDao.adminSortUsersByName();

        assertNotEquals(users.get(4), actualUser); // Because of Sorting the user at index 4 will not be the same as if we return it from the Database
    }

    @Test // For Admin Only
    public void adminSortUsersByEmail() throws DaoException {
        List<User> users = userDao.adminSortUsersByEmail();

        assertNotEquals(users.get(4), actualUser); // Because of Sorting the user at index 4 will not be the same as if we return it from the Database
    }

    @Test
    public void getUserOrders() throws DaoException {
        Optional<User> userOptional = userDao.getById(actualUser.getId());

        userOptional.ifPresent(user -> {

            int expectedUserOrderListSize = 2;
            int actualUserOrderListSize = userDao.getUserOrders(user).size();

            assertEquals(expectedUserOrderListSize, actualUserOrderListSize);
        });
    }
}