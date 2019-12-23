package com.epam.library.model.service;

import com.epam.library.entity.Book;
import com.epam.library.entity.Order;
import com.epam.library.model.dao.DaoException;
import com.epam.library.model.dao.OrderDao;
import com.epam.library.util.constant.BeanNameHolder;
import com.epam.library.util.validate.ArgumentValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service(BeanNameHolder.ORDER_SERVICE_BEAN)
@Transactional
@Slf4j
public class OrderIService implements IService<Order> {

	@Autowired
	private OrderDao orderDao;

	public OrderIService(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	@Override
	public Optional<Order> getById(long id) throws ServiceException {
		try {
			return orderDao.getById(id);
		} catch (DaoException e) {
			throw new ServiceException("exception in getById at OrderService class", e);
		}
	}

	@Override
	public List<Order> getAll() throws ServiceException {
		try {
			return orderDao.getAll();
		} catch (DaoException e) {
			throw new ServiceException("exception in getAll at OrderService class", e);
		}
	}

	@Override
	public int save(Order order) throws ServiceException {
		ArgumentValidator.checkForNull(order, "Not allow for a null order in save at OrderService class");

		try {
			return orderDao.save(order);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("exception in save at OrderService class", e);
		}
	}

	@Override
	public int removeById(long id) throws ServiceException {
		try {
			return orderDao.removeById(id);
		} catch (DaoException e) {
			throw new ServiceException("exception in removeById at OrderService class", e);
		}
	}

	@Override
	public int update(Order order) throws ServiceException {
		ArgumentValidator.checkForNull(order, "Not allow for a null order in update at OrderService class");

		try {
			System.out.println(" OrderService update Updae : " + order + " : " + order.getBook());
			return orderDao.update(order);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("exception in update at OrderService class", e);
		}
	}

	/**
	 * @param orderId     to get the specified order to be removed
	 * @param bookService to pass it to the increasing book quantity
	 * @throws ServiceException in case of something wrong happens during the
	 *                          process
	 */
	public void administrationOrderRemoval(long orderId, BookIService bookService) throws ServiceException {
		Optional<Order> optionalOrder = getById(orderId);
		if (optionalOrder.isPresent()) {
			for (Book book : optionalOrder.get().getBook()) {
				increaseBookQuantity(book.getId(), bookService);
			}
		}

		removeById(orderId);
	}

	/**
	 * @param order       to be confirmed and to added to the user order list
	 * @param bookService to pass it to the decreasing book quantity
	 * @throws ServiceException in case of something wrong happens during the
	 *                          process
	 */
	public void confirmUserOrder(Order order, BookIService bookService) throws ServiceException {
		save(order);
		order.getBook().forEach(book1 -> {
			try {
				decreaseBookQuantity(book1, bookService);
			} catch (ServiceException e) {
				log.error("Exception decreaseBookQuantity at confirmUserOrder in " + this.getClass().getSimpleName(),
						e);
			}
		});

	}

	/**
	 * To update the book quantity in the data base
	 *
	 * @param book that the user want to order it
	 * @throws ServiceException if something wrong with updating method
	 */
	private void decreaseBookQuantity(Book book, BookIService bookService) throws ServiceException {
		Long id = book.getId();
		int updatedQuantity = book.getQuantity() - 1;
		bookService.updateQuantity(id, updatedQuantity);
	}

	/**
	 * @param bookId of the book that will be updated by one
	 * @throws ServiceException if something wrong during the connection with
	 *                          database
	 */
	private void increaseBookQuantity(long bookId, BookIService bookService) throws ServiceException {

		Optional<Book> optionalBook = bookService.getById(bookId);
		if (optionalBook.isPresent()) {
			Book book = optionalBook.get();
			int updatedQuantity = book.getQuantity() + 1;
			bookService.updateQuantity(bookId, updatedQuantity);
		}
	}

	public Set<Book> booksFromOrder(long id) throws ServiceException {
		try {
			return orderDao.booksFromOrder(id);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

}
