package com.epam.library.model.dao;

import com.epam.library.util.constant.BeanNameHolder;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository(BeanNameHolder.ABSTRACT_DAO_BEAN)
@NoArgsConstructor
abstract class AbstractDao<T> implements Dao<T> {

	@PersistenceContext
	protected EntityManager entityManager;

}
