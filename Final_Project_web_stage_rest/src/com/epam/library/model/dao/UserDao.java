package com.epam.library.model.dao;

import com.epam.library.entity.Order;
import com.epam.library.entity.User;
import com.epam.library.entity.enumeration.Role;
import com.epam.library.util.MD5Encrypt;
import com.epam.library.util.constant.BeanNameHolder;
import com.epam.library.util.constant.DiffConstant;
import com.epam.library.util.constant.entityconstant.UserConstant;
import com.epam.library.util.validate.ArgumentValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository(BeanNameHolder.USER_DAO_BEAN)
@Transactional
@Slf4j
public class UserDao extends AbstractDao<User> {

	private static final String UPDATE_USER_QUERY = "update User set name =:" + UserConstant.NAME + ", lastName =:"
			+ UserConstant.LAST_NAME + ", email =:" + UserConstant.EMAIL + ", login =:" + UserConstant.LOGIN
			+ ", password =:" + UserConstant.PASSWORD + ", blocked =:" + UserConstant.BLOCKED + ", role =:"
			+ UserConstant.ROLE + " where id =:" + UserConstant.ID;
	private static final String FIND_USER_BY_LOGIN_PASSWORD_QUERY = "FROM User WHERE login =:user_login AND password =:user_password";
	private static final String FIND_USER_BY_LOGIN_QUERY = "FROM User WHERE login =:user_login";
	private static final String FIND_USER_BY_EMAIL_QUERY = "FROM User WHERE email =:user_email";
	//// Librarian Query ////
	private static final String FIND_USER_BY_READER_ROLE = "FROM User WHERE role =:" + UserConstant.ROLE;
	private static final String FIND_ALL_USERS_SORTING_BY_NAME = "FROM User WHERE role =:" + UserConstant.ROLE
			+ " order by " + UserConstant.NAME + " ASC";
	private static final String FIND_ALL_USERS_SORTING_BY_EMAIL = "FROM User WHERE role =:" + UserConstant.ROLE
			+ " order by " + UserConstant.EMAIL + " ASC";
	//// ADMIN Query ////
	private static final String GET_ALL_QUERY = "FROM User";
	private static final String FIND_ALL_SORTING_BY_NAME_FOR_ADMIN = "FROM User order by " + UserConstant.NAME + " ASC";
	private static final String FIND_ALL_SORTING_BY_EMAIL_FOR_ADMIN = "FROM User order by " + UserConstant.EMAIL
			+ " ASC";

	@Override
	public Optional<User> getById(long id) throws DaoException {
		User user = entityManager.find(User.class, id);
		return Optional.of(user);
	}

	@Override
	public List<User> getAll() throws DaoException {
		return entityManager.createQuery(GET_ALL_QUERY, User.class)
				.setHint(DiffConstant.HIBERNATE_QUERY_CACHING, Boolean.TRUE).getResultList();
	}

	/**
	 * @param item the user to be saved
	 * @throws DaoException if something went wrong
	 */
	@Override
	public int save(User item) throws DaoException {
		String encryptedPassword = MD5Encrypt.encrypt(item.getPassword());
		item.setPassword(encryptedPassword);
		entityManager.persist(item);
		return 1;
	}

	@Override
	public int removeById(long id) throws DaoException {

		Optional<User> user = getById(id);
		if (user.isPresent()) {
			entityManager.remove(user.get());
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * @param item user to be update by the Librarian.
	 * @throws DaoException
	 */
	@Override
	public int update(User item) throws DaoException {

		ArgumentValidator.checkForNull(item, "User item in update method at UserDao class can not be null");

		Query query = entityManager.createQuery(UPDATE_USER_QUERY);
		query.setParameter(UserConstant.NAME, item.getName());
		query.setParameter(UserConstant.LAST_NAME, item.getLastName());
		query.setParameter(UserConstant.EMAIL, item.getEmail());
		query.setParameter(UserConstant.LOGIN, item.getLogin());
		query.setParameter(UserConstant.PASSWORD, item.getPassword());
		query.setParameter(UserConstant.ROLE, item.getRole());
		query.setParameter(UserConstant.BLOCKED, item.isBlocked());
		query.setParameter(UserConstant.ID, item.getId());
		query.executeUpdate();
		return 1;

	}

	/**
	 * @param login    of the user
	 * @param password of the user
	 * @return userOptional
	 * @throws DaoException
	 */
	public Optional<User> findByLoginAndPassword(String login, String password) throws DaoException {
		ArgumentValidator.checkForNullOrEmptyString(login,
				"Not allow for a null or empty login in findByLoginAndPassword at UserDao class");
		ArgumentValidator.checkForNullOrEmptyString(login,
				"Not allow for a null or empty password in findByLoginAndPassword at UserDao class");
		String encryptedPassword = MD5Encrypt.encrypt(password);

		log.info("findByLoginAndPassword: " + login + " : " + password + " : " + encryptedPassword + " UserDao");

		Query query = entityManager.createQuery(FIND_USER_BY_LOGIN_PASSWORD_QUERY);
		query.setParameter(UserConstant.LOGIN, login);
		query.setParameter(UserConstant.PASSWORD, encryptedPassword);
		query.setHint(DiffConstant.HIBERNATE_QUERY_CACHING, Boolean.TRUE);
		User user = (User) query.getSingleResult();
		return Optional.of(user);
	}

	public Optional<User> findByLogin(String login) throws DaoException {
		ArgumentValidator.checkForNullOrEmptyString(login,
				"Not allow for null or empty value in findByLogin " + "method at userDao Class");
		Query query = entityManager.createQuery(FIND_USER_BY_LOGIN_QUERY, User.class);
		query.setParameter(UserConstant.LOGIN, login);
		query.setHint(DiffConstant.HIBERNATE_QUERY_CACHING, Boolean.TRUE);
		User user = (User) query.getSingleResult();
		return Optional.of(user);
	}

	public Optional<User> findByEmail(String email) throws DaoException {
		ArgumentValidator.checkForNullOrEmptyString(email,
				"Not allow for null or empty value in findByEmail " + "method at userDao Class");
		Query query = entityManager.createQuery(FIND_USER_BY_EMAIL_QUERY, User.class);
		query.setParameter(UserConstant.EMAIL, email);
		query.setHint(DiffConstant.HIBERNATE_QUERY_CACHING, Boolean.TRUE);
		User user = (User) query.getSingleResult();
		return Optional.of(user);
	}

	// Librarian Query

	/**
	 * @return list of user by the role Reader
	 * @throws DaoException if something wrong happens while executing the query
	 */
	public List<User> findAllWhereRoleReader() throws DaoException {
		
		Query query = entityManager.createQuery(FIND_USER_BY_READER_ROLE);
		query.setParameter(UserConstant.ROLE, Role.READER);
		query.setHint(DiffConstant.HIBERNATE_QUERY_CACHING, Boolean.TRUE);
		return query.getResultList();
	}

	public List<User> sortUsersByName() throws DaoException {
		Query query = entityManager.createQuery(FIND_ALL_USERS_SORTING_BY_NAME);
		query.setParameter(UserConstant.ROLE, Role.READER);
		query.setHint(DiffConstant.HIBERNATE_QUERY_CACHING, Boolean.TRUE);
		return query.getResultList();
	}

	public List<User> sortUsersByEmail() throws DaoException {
		Query query = entityManager.createQuery(FIND_ALL_USERS_SORTING_BY_EMAIL);
		query.setParameter(UserConstant.ROLE, Role.READER);
		query.setHint(DiffConstant.HIBERNATE_QUERY_CACHING, Boolean.TRUE);
		return query.getResultList();
	}

	// Admin Query
	public List<User> adminSortUsersByName() throws DaoException {
		return entityManager.createQuery(FIND_ALL_SORTING_BY_NAME_FOR_ADMIN, User.class)
				.setHint(DiffConstant.HIBERNATE_QUERY_CACHING, Boolean.TRUE).getResultList();
	}

	public List<User> adminSortUsersByEmail() throws DaoException {
		return entityManager.createQuery(FIND_ALL_SORTING_BY_EMAIL_FOR_ADMIN, User.class)
				.setHint(DiffConstant.HIBERNATE_QUERY_CACHING, Boolean.TRUE).getResultList();
	}

	public List<Order> getUserOrders(User user) {
		return user.getOrders();
	}
}
