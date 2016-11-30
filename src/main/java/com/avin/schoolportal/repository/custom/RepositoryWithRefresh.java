package com.avin.schoolportal.repository.custom;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

/**
 * Created by Yubar on 11/24/2016.
 */

@NoRepositoryBean
public interface RepositoryWithRefresh<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {
    void refresh(T entity);
}
