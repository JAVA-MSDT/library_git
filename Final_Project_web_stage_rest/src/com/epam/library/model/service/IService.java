package com.epam.library.model.service;

import java.util.List;
import java.util.Optional;

public interface IService<T> {
	Optional<T> getById(long id) throws ServiceException;

	List<T> getAll() throws ServiceException;

	int save(T item) throws ServiceException;

	int removeById(long id) throws ServiceException;

	int update(T item) throws ServiceException;

}
