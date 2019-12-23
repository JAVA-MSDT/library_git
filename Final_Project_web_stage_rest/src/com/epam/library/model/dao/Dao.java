package com.epam.library.model.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

	Optional<T> getById(long id) throws DaoException;

	List<T> getAll() throws DaoException;

	int save(T item) throws DaoException;

	int removeById(long id) throws DaoException;

	int update(T item) throws DaoException;

}
