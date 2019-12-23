package com.epam.library.model.dao;

import com.epam.library.util.constant.BeanNameHolder;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository(BeanNameHolder.ABSTRACT_DAO_BEAN)
@NoArgsConstructor
public abstract class AbstractDao<T> implements Dao<T> {

    @PersistenceContext
    protected EntityManager entityManager;


    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


}
