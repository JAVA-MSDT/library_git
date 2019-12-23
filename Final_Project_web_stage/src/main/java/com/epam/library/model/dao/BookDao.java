package com.epam.library.model.dao;

import com.epam.library.entity.Book;
import com.epam.library.entity.Order;
import com.epam.library.util.constant.BeanNameHolder;
import com.epam.library.util.constant.DiffConstant;
import com.epam.library.util.constant.entityconstant.BookConstant;
import com.epam.library.util.validate.ArgumentValidator;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository(BeanNameHolder.BOOK_DAO_BEAN)
@NoArgsConstructor
@Transactional
public class BookDao extends AbstractDao<Book> {


    @Override
    public Optional<Book> getById(long id) throws DaoException {

        Book book = entityManager.find(Book.class, id);
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> getAll() throws DaoException {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> root = criteriaQuery.from(Book.class);
        criteriaQuery.select(root).orderBy(criteriaBuilder.asc(root.get(BookConstant.BOOK_ID_POJO)));

        return entityManager.createQuery(criteriaQuery)
                .setHint(DiffConstant.HIBERNATE_QUERY_CACHING, Boolean.TRUE)
                .getResultList();
    }

    public List<Book> getAllByPage(int offset, int recordQuantity) throws DaoException {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> root = criteriaQuery.from(Book.class);
        criteriaQuery.select(root);


        return entityManager.createQuery(criteriaQuery)
                .setHint(DiffConstant.HIBERNATE_QUERY_CACHING, Boolean.TRUE)
                .setFirstResult(offset)
                .setMaxResults(recordQuantity)
                .getResultList();
    }

    @Override
    public void save(Book item) throws DaoException {
        ArgumentValidator.checkForNull(item, "Not allow for a null item in save at BookDao class");
        entityManager.persist(item);
    }

    @Override
    public void removeById(long id) throws DaoException {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Book> criteriaDelete = criteriaBuilder.createCriteriaDelete(Book.class);
        Root<Book> root = criteriaDelete.from(Book.class);
        criteriaDelete.where(criteriaBuilder.equal(root.get(BookConstant.BOOK_ID_POJO), id));
        entityManager.createQuery(criteriaDelete).executeUpdate();
    }

    @Override
    public void update(Book item) throws DaoException {
        ArgumentValidator.checkForNull(item, "Not allow for a null item in save at BookDao class");

        Book book = entityManager.find(Book.class, item.getId(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        if (book != null) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaUpdate<Book> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Book.class);
            Root<Book> root = criteriaUpdate.from(Book.class);

            criteriaUpdate.set(BookConstant.BOOK_NAME_POJO, item.getName());
            criteriaUpdate.set(BookConstant.BOOK_QUANTITY, item.getQuantity());
            criteriaUpdate.where(criteriaBuilder.equal(root.get(BookConstant.BOOK_ID_POJO), item.getId()));

            entityManager.createQuery(criteriaUpdate)
                    .executeUpdate();
        }

    }

    public void updateQuantity(Long bookId, int quantity) throws DaoException {
        Book book = entityManager.find(Book.class, bookId, LockModeType.OPTIMISTIC_FORCE_INCREMENT);

        if (book != null) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaUpdate<Book> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Book.class);
            Root<Book> root = criteriaUpdate.from(Book.class);

            criteriaUpdate.set(BookConstant.BOOK_QUANTITY, quantity);
            criteriaUpdate.where(criteriaBuilder.equal(root.get(BookConstant.BOOK_ID_POJO), bookId));

            entityManager.createQuery(criteriaUpdate)
                    .executeUpdate();
        }

    }

    public List<Book> findByName(String name) throws DaoException {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> root = criteriaQuery.from(Book.class);

        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(BookConstant.BOOK_NAME_POJO), name));
        TypedQuery<Book> bookTypedQuery = entityManager.createQuery(criteriaQuery);
        bookTypedQuery.setHint(DiffConstant.HIBERNATE_QUERY_CACHING, Boolean.TRUE);
        return bookTypedQuery.getResultList();
    }

    // Sorting
    public List<Book> sortBooksByName() throws DaoException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> root = criteriaQuery.from(Book.class);

        criteriaQuery.select(root).orderBy(criteriaBuilder.asc(root.get(BookConstant.BOOK_NAME_POJO)));
        return entityManager.createQuery(criteriaQuery)
                .setHint(DiffConstant.HIBERNATE_QUERY_CACHING, Boolean.TRUE)
                .getResultList();
    }

    public List<Book> sortBookByQuantity() throws DaoException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> root = criteriaQuery.from(Book.class);

        criteriaQuery.select(root).orderBy(criteriaBuilder.asc(root.get(BookConstant.BOOK_QUANTITY)));
        return entityManager.createQuery(criteriaQuery)
                .setHint(DiffConstant.HIBERNATE_QUERY_CACHING, Boolean.TRUE)
                .getResultList();
    }

    public List<Order> getOrderFromBook(Book book) {

        return book.getOrders()
                .stream()
                .filter(order -> order.getBook().contains(book))
                .collect(Collectors.toList());
    }

}
