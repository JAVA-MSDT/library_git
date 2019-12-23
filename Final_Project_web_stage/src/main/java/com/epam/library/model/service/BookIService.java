package com.epam.library.model.service;

import com.epam.library.entity.Book;
import com.epam.library.entity.Order;
import com.epam.library.model.dao.BookDao;
import com.epam.library.model.dao.DaoException;
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

    public Optional<Book> getById(long id) throws ServiceException {
        try {
            return bookDao.getById(id);
        } catch (DaoException e) {
            throw new ServiceException("exception in getById at bookService class", e);
        }
    }

    public List<Book> getAll() throws ServiceException {
        try {
            return bookDao.getAll();
        } catch (DaoException e) {
            throw new ServiceException("exception in findAllBook at bookService class", e);
        }
    }

    public List<Book> getAllByPage(int offset, int recordQuantity) throws ServiceException {
        try {
            return bookDao.getAllByPage(offset, recordQuantity);
        } catch (DaoException e) {
            throw new ServiceException("exception in findAllBook at bookService class", e);
        }

    }

    public void save(Book book) throws ServiceException {
        ArgumentValidator.checkForNull(book, "Not allow for a null book in save at bookService class");
        try {
            bookDao.save(book);
        } catch (DaoException e) {
            throw new ServiceException("exception in save at bookService class", e);
        }
    }

    public void removeById(long id) throws ServiceException {
        try {
            bookDao.removeById(id);
        } catch (DaoException e) {
            throw new ServiceException("exception in save at bookService class", e);
        }
    }

    public void update(Book book) throws ServiceException {
        ArgumentValidator.checkForNull(book, "Not allow for a null book in update at bookService class");
        try {
            bookDao.update(book);
        } catch (DaoException e) {
            throw new ServiceException("exception in update at bookService class", e);
        }
    }

    public void updateQuantity(Long bookId, int quantity) throws ServiceException {
        try {
            bookDao.updateQuantity(bookId, quantity);
        } catch (DaoException e) {
            throw new ServiceException("exception in updateQuantity at bookService class", e);
        }
    }

    public List<Book> findByName(String name) throws ServiceException {
        try {
            return bookDao.findByName(name);
        } catch (DaoException e) {
            throw new ServiceException("exception in findByName at bookService class", e);
        }
    }

    // Sorting
    public List<Book> sortBooksByName() throws ServiceException {
        try {
            return bookDao.sortBooksByName();
        } catch (DaoException e) {
            throw new ServiceException("exception in sortBooksByName at bookService class", e);
        }
    }

    public List<Book> sortBookByQuantity() throws ServiceException {
        try {
            return bookDao.sortBookByQuantity();
        } catch (DaoException e) {
            throw new ServiceException("exception in sortBookByQuantity at bookService class", e);
        }
    }

    public List<Order> getOrderFromBook(Book book) {
        return bookDao.getOrderFromBook(book);
    }
}
