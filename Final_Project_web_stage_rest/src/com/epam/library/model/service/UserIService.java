package com.epam.library.model.service;

import com.epam.library.entity.Book;
import com.epam.library.entity.Order;
import com.epam.library.entity.User;
import com.epam.library.model.dao.DaoException;
import com.epam.library.model.dao.UserDao;
import com.epam.library.model.dto.orderservice.UserOrderDisplay;
import com.epam.library.util.constant.BeanNameHolder;
import com.epam.library.util.validate.entityvalidate.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(BeanNameHolder.USER_SERVICE_BEAN)
@Transactional
public class UserIService implements IService<User> {

	@Autowired
	private UserDao userDao;

	public UserIService(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * @param id of the user in the database
	 * @return user if it is exist or empty optional
	 * @throws ServiceException
	 */

	@Override
	public Optional<User> getById(long id) throws ServiceException {
		try {
			return userDao.getById(id);
		} catch (DaoException e) {
			throw new ServiceException("Dao Exception in getById method in UserService class", e);
		}
	}

	/**
	 * @param login    of the user
	 * @param password of the user
	 * @return user if it is exist or empty optional
	 * @throws ServiceException
	 */

	public Optional<User> findByLoginPassword(String login, String password) throws ServiceException {
		Optional<User> optionalUser = Optional.empty();
		if (UserValidator.isValidLogin(login) && UserValidator.isValidPassword(password)) {
			try {
				optionalUser = userDao.findByLoginAndPassword(login, password);
			} catch (DaoException e) {
				throw new ServiceException("Dao Exception in findByLoginPassword method in UserService class", e);
			}
		}
		return optionalUser;
	}

	/**
	 * @return List of the users from the Dao
	 * @throws ServiceException
	 */

	@Override
	public List<User> getAll() throws ServiceException {
		try {
			return userDao.getAll();
		} catch (DaoException e) {
			throw new ServiceException("Dao Exception in getAll method in UserService class", e);
		}
	}

	@Override
	public int save(User user) throws ServiceException {
		try {
			return userDao.save(user);
		} catch (DaoException e) {
			throw new ServiceException("Dao Exception in save method in UserService class", e);
		}
	}

	@Override
	public int removeById(long id) throws ServiceException {
		try {
			return userDao.removeById(id);
		} catch (DaoException e) {
			throw new ServiceException("Dao Exception in removeById method in UserService class", e);
		}
	}

	@Override
	public int update(User user) throws ServiceException {
		try {
			return userDao.update(user);
		} catch (DaoException e) {
			throw new ServiceException("Dao Exception in update method in UserService class", e);
		}
	}

	public Optional<User> findByLogin(String login) throws ServiceException {
		try {
			return userDao.findByLogin(login);
		} catch (DaoException e) {
			throw new ServiceException("Dao Exception in findByLogin method in UserService class", e);
		}
	}

	public Optional<User> findByEmail(String email) throws ServiceException {
		try {
			return userDao.findByEmail(email);
		} catch (DaoException e) {
			throw new ServiceException("Dao Exception in findByEmail method in UserService class", e);
		}
	}

	// Librarian Query

	/**
	 * @return list of user by the role Reader
	 * @throws ServiceException if something wrong happens while executing the query
	 */

	public List<User> findAllWhereRoleReader() throws ServiceException {
		try {
			return userDao.findAllWhereRoleReader();
		} catch (DaoException e) {
			throw new ServiceException("Dao Exception in findAllWhereRoleReader method in UserService class", e);
		}
	}

	public List<User> sortUsersByName() throws ServiceException {
		try {
			return userDao.sortUsersByName();
		} catch (DaoException e) {
			throw new ServiceException("Dao Exception in sortUsersByName method in UserService class", e);
		}
	}

	public List<User> sortUsersByEmail() throws ServiceException {
		try {
			return userDao.sortUsersByEmail();
		} catch (DaoException e) {
			throw new ServiceException("Dao Exception in sortUsersByEmail method in UserService class", e);
		}
	}

	// Admin Query

	public List<User> adminSortUsersByName() throws ServiceException {
		try {
			return userDao.adminSortUsersByName();
		} catch (DaoException e) {
			throw new ServiceException("Dao Exception in adminSortUsersByName method in UserService class", e);
		}
	}

	public List<User> adminSortUsersByEmail() throws ServiceException {
		try {
			return userDao.adminSortUsersByEmail();
		} catch (DaoException e) {
			throw new ServiceException("Dao Exception in adminSortUsersByEmail method in UserService class", e);
		}
	}

	/**
	 * @return list of a special class to use it later to display the detailed order
	 *         information for the User
	 * @throws ServiceException if something wrong during the process
	 */

	public List<UserOrderDisplay> userOrders(User user) throws ServiceException {
		List<Order> orders = userDao.getUserOrders(user);
		List<UserOrderDisplay> userOrderDisplays = new ArrayList<>();

		for (Order order : orders) {
			List<String> bookName = order.getBook().stream().map(Book::getName).collect(Collectors.toList());

			UserOrderDisplay userOrderDisplay = new UserOrderDisplay();
			userOrderDisplay.setId(order.getOrderId());
			userOrderDisplay.setUserId(order.getUser().getId());
			userOrderDisplay.setBookName(bookName);
			userOrderDisplay.setOrderDate(order.getOrderDate());
			userOrderDisplay.setReturningDate(order.getReturningDate());
			userOrderDisplay.setReadingPlace(order.getReadingPlace());
			userOrderDisplay.setBookReturned(order.isBookReturned());

			userOrderDisplays.add(userOrderDisplay);
		}

		userOrderDisplays.forEach(System.out::println);
		return userOrderDisplays;
	}
}
