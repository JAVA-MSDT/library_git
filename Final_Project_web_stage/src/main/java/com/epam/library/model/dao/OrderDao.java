package com.epam.library.model.dao;

import com.epam.library.entity.Book;
import com.epam.library.entity.Order;
import com.epam.library.entity.User;
import com.epam.library.model.dto.orderservice.AdministrationOrderDisplay;
import com.epam.library.util.constant.BeanNameHolder;
import com.epam.library.util.constant.DiffConstant;
import com.epam.library.util.constant.entityconstant.BookConstant;
import com.epam.library.util.constant.entityconstant.OrderConstant;
import com.epam.library.util.constant.entityconstant.UserConstant;
import com.epam.library.util.validate.ArgumentValidator;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository(BeanNameHolder.ORDER_DAO_BEAN)
@Transactional
@NoArgsConstructor
public class OrderDao extends AbstractDao<Order> {

    @Override
    public Optional<Order> getById(long id) throws DaoException {
        Order order = entityManager.find(Order.class, id);
        return Optional.of(order);
    }

    @Override
    public List<Order> getAll() throws DaoException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery)
                .setHint(DiffConstant.HIBERNATE_QUERY_CACHING, Boolean.TRUE)
                .getResultList();
    }

    @Override
    public void save(Order item) throws DaoException {
        ArgumentValidator.checkForNull(item, "Not allow for a null item in save at OrderDao class");
        entityManager.persist(item);
    }

    @Override
    public void removeById(long id) throws DaoException {
        Optional<Order> orderOptional = getById(id);
        orderOptional.ifPresent(order -> entityManager.remove(order));
    }

    @Override
    public void update(Order item) throws DaoException {
        ArgumentValidator.checkForNull(item, "Not allow for a null item in update at OrderDao class");
        entityManager.merge(item);
    }

    public List<Order> findOrderByUserId(long userId) throws DaoException {
        User user = entityManager.find(User.class, userId);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get(UserConstant.USER_ATTRIBUTE), user));

        return entityManager.createQuery(criteriaQuery)
                .setHint(DiffConstant.HIBERNATE_QUERY_CACHING, Boolean.TRUE)
                .getResultList();
    }

    /**
     * @return list of a special object to use it later to display the detailed order
     * information for the administration and admin
     * @throws DaoException is something wrong during the process
     */
    public List<AdministrationOrderDisplay> administrationAllOrder() throws DaoException {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AdministrationOrderDisplay> criteriaQuery = criteriaBuilder.createQuery(AdministrationOrderDisplay.class);

        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        Join<Order, User> userJoin = orderRoot.join(UserConstant.USER_ATTRIBUTE, JoinType.INNER);
        Join<Order, Book> bookJoin = orderRoot.join(BookConstant.BOOK_ATTRIBUTE, JoinType.INNER);

        criteriaQuery.select(criteriaBuilder.construct(
                AdministrationOrderDisplay.class,
                orderRoot.get(OrderConstant.ORDER_ID_POJO),
                criteriaBuilder.concat(bookJoin.get(BookConstant.BOOK_NAME_POJO), ""),
                criteriaBuilder.concat(userJoin.get(UserConstant.USER_NAME_POJO), ""),
                criteriaBuilder.concat(userJoin.get(UserConstant.USER_EMAIL_POJO), ""),
                orderRoot.get(OrderConstant.ORDER_DATE_POJO),
                orderRoot.get(OrderConstant.RETURNING_DATE_POJO),
                orderRoot.get(OrderConstant.READING_PLACE_POJO),
                orderRoot.get(OrderConstant.BOOK_RETURNED_POJO)
        ));

        return entityManager.createQuery(criteriaQuery)
                .setHint(DiffConstant.HIBERNATE_QUERY_CACHING, Boolean.TRUE)
                .getResultList();
    }



    //============================== Experimental method ==============================//
    private void removeFromOrderBooksTable(Order order, Book book) {
        String sql = "DELETE FROM order_books WHERE order_id= ? AND book_id = ?";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, order.getOrderId());
        query.setParameter(2, book.getId());

        query.executeUpdate();
    }

    //============================== Experimental method ==============================//
    public BigInteger quantityOfOrders() {
        String countAll = "SELECT COUNT(*) FROM order_books";
        Query query = entityManager.createNativeQuery(countAll);
        return (BigInteger) query.getSingleResult();
    }
}
