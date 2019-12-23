package com.epam.library.model.service;

import com.epam.library.entity.Book;
import com.epam.library.entity.Order;
import com.epam.library.model.dao.BookDao;
import com.epam.library.util.constant.BeanNameHolder;
import com.epam.library.util.validate.ArgumentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service(BeanNameHolder.BOOK_SERVICE_BEAN)
@Transactional
public class BookIService implements IService<Book> {

	@Autowired
	private BookDao bookDao;

	public BookIService(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	@Override
	public Optional<Book> getById(long id) {
		return bookDao.getById(id);
	}

	@Override
	public List<Book> getAll() {

		return bookDao.getAll();

	}

	public List<Book> getAllByPage(int offset, int recordQuantity) {

		return bookDao.getAllByPage(offset, recordQuantity);

	}

	@Override
	public int save(Book book) {
		ArgumentValidator.checkForNull(book, "Not allow for a null book in save at bookService class");

		return bookDao.save(book);
	}

	@Override
	public int removeById(long id) {

		return bookDao.removeById(id);

	}

	@Override
	public int update(Book book) {
		ArgumentValidator.checkForNull(book, "Not allow for a null book in update at bookService class");

		return bookDao.update(book);
	}

	public void updateQuantity(Long bookId, int quantity) {
		bookDao.updateQuantity(bookId, quantity);
	}

	public List<Book> findByName(String name) throws ServiceException {

		return bookDao.findByName(name);
	}

	// Sorting
	public List<Book> sortBooksByName() {

		return bookDao.sortBooksByName();
	}

	public List<Book> sortBookByQuantity() {

		return bookDao.sortBookByQuantity();

	}

	public List<Order> getOrderFromBook(long id) {
		return bookDao.getOrderFromBook(id);
	}
}
