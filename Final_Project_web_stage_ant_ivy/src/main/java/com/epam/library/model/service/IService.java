package com.epam.library.model.service;

import java.util.List;
import java.util.Optional;

public interface IService<T> {
    Optional<T> getById(long id) throws ServiceException;

    List<T> getAll() throws ServiceException;

    void save(T item) throws ServiceException;

    void removeById(long id) throws ServiceException;

    void update(T item) throws ServiceException;

}
