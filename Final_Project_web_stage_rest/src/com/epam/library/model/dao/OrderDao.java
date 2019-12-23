package com.epam.library.model.dao;

import com.epam.library.entity.Book;
import com.epam.library.entity.Order;
import com.epam.library.entity.User;
import com.epam.library.util.constant.BeanNameHolder;
import com.epam.library.util.constant.DiffConstant;
import com.epam.library.util.constant.entityconstant.UserConstant;
import com.epam.library.util.validate.ArgumentValidator;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Repository(BeanNameHolder.ORDER_DAO_BEAN)
@Transactional
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
		return entityManager.createQuery(criteriaQuery).setHint(DiffConstant.HIBERNATE_QUERY_CACHING, Boolean.TRUE)
				.getResultList();
	}

	@Override
	public int save(Order item) throws DaoException {
		ArgumentValidator.checkForNull(item, "Not allow for a null item in save at OrderDao class");
		entityManager.persist(item);
		return 1;
	}

	@Override
	public int removeById(long id) throws DaoException {
		AtomicInteger removed = new AtomicInteger();
		Optional<Order> orderOptional = getById(id);
		orderOptional.ifPresent(order -> {
			entityManager.remove(order);
			removed.set(1);
		});
		return removed.get();
	}

	@Override
	public int update(Order item) throws DaoException {
		ArgumentValidator.checkForNull(item, "Not allow for a null item in update at OrderDao class");
		entityManager.merge(item);
		return 1;
	}

	public List<Order> findOrderByUserId(long userId) throws DaoException {
		User user = entityManager.find(User.class, userId);

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get(UserConstant.USER_ATTRIBUTE), user));

		return entityManager.createQuery(criteriaQuery).setHint(DiffConstant.HIBERNATE_QUERY_CACHING, Boolean.TRUE)
				.getResultList();
	}

	public Set<Book> booksFromOrder(long id) throws DaoException {
		Optional<Order> orderOptional = getById(id);
		if (orderOptional.isPresent()) {
			Order order = orderOptional.get();
			return order.getBook();
		} else {
			return Collections.emptySet();
		}
	}

}
