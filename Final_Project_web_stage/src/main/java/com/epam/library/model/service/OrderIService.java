package com.epam.library.model.service;

import com.epam.library.entity.Book;
import com.epam.library.entity.Order;
import com.epam.library.model.dao.DaoException;
import com.epam.library.model.dao.OrderDao;
import com.epam.library.model.dto.orderservice.AdministrationOrderDisplay;
import com.epam.library.model.dto.orderservice.UserOrderDisplay;
import com.epam.library.util.constant.BeanNameHolder;
import com.epam.library.util.validate.ArgumentValidator;
import lombok.Setter;
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


    public Optional<Order> getById(long id) throws ServiceException {
        try {
            return orderDao.getById(id);
        } catch (DaoException e) {
            throw new ServiceException("exception in getById at OrderService class", e);
        }
    }


    public List<Order> getAll() throws ServiceException {
        try {
            return orderDao.getAll();
        } catch (DaoException e) {
            throw new ServiceException("exception in getAll at OrderService class", e);
        }
    }

    public void save(Order order) throws ServiceException {
        ArgumentValidator.checkForNull(order, "Not allow for a null order in save at OrderService class");

        try {
            orderDao.save(order);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("exception in save at OrderService class", e);
        }
    }

    public void removeById(long id) throws ServiceException {
        try {
            orderDao.removeById(id);
        } catch (DaoException e) {
            throw new ServiceException("exception in removeById at OrderService class", e);
        }
    }

    public void update(Order order) throws ServiceException {
        ArgumentValidator.checkForNull(order, "Not allow for a null order in update at OrderService class");

        try {
            System.out.println(" OrderService update Updae : " + order + " : " + order.getBook());
            orderDao.update(order);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("exception in update at OrderService class", e);
        }
    }


    public List<Order> findOrderByUserId(long userId) throws ServiceException {
        try {
            return orderDao.findOrderByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException("exception in findOrderByUserId at OrderService class", e);
        }
    }


    /**
     * @return list of a special object to use it later to display the detailed order
     * information for the administration and admin
     * @throws ServiceException if something wrong during the process
     */
    public List<AdministrationOrderDisplay> administrationAllOrder() throws ServiceException {
        try {
            return orderDao.administrationAllOrder();
        } catch (DaoException e) {
            throw new ServiceException("exception in administrationAllOrder at OrderService class", e);
        }
    }

    /**
     * @param orderID     to get the specified order to be removed
     * @param bookId      to get the returning book and updated it by 1 after removing the order
     * @param bookService to pass it to the increasing book quantity
     * @throws ServiceException in case of something wrong happens during the process
     */
    public void administrationOrderRemoval(String orderID, String bookId, BookIService bookService) throws ServiceException {

         increaseBookQuantity(bookId, bookService);
        removeById(Long.parseLong(orderID));
    }

    /**
     * @param order       to be confirmed and to added to the user order list
     * @param book        to get the desired book then decrees it by 1 after the user order it
     * @param bookService to pass it to the decreasing book quantity
     * @throws ServiceException in case of something wrong happens during the process
     */
    public void confirmUserOrder(Order order, Set<Book> book, BookIService bookService) throws ServiceException {
        save(order);
        book.forEach(book1 -> {
            try {
                decreaseBookQuantity(book1, bookService);
            } catch (ServiceException e) {
                log.error("Exception decreaseBookQuantity at confirmUserOrder in " + this.getClass().getSimpleName(), e);
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
     * @throws ServiceException if something wrong during the connection with database
     */
    private void increaseBookQuantity(String bookId, BookIService bookService) throws ServiceException {

        long id = Long.parseLong(bookId);
        Optional<Book> optionalBook = bookService.getById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            int updatedQuantity = book.getQuantity() + 1;
            bookService.updateQuantity(id, updatedQuantity);
        }
    }

}
