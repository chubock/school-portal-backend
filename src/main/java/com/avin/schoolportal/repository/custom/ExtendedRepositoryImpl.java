package com.avin.schoolportal.repository.custom;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Created by Yubar on 11/24/2016.
 */
public class ExtendedRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements ExtendedRepository<T, ID> {

    private final EntityManager em;

    public ExtendedRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.em = entityManager;
    }

    @Override
    public void refresh(T entity) {
        em.flush();
        em.refresh(entity);
    }
}
