package com.avin.schoolportal.repository;

import com.avin.schoolportal.domain.Manciple;
import com.avin.schoolportal.domain.Teacher;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.QueryHint;

/**
 * Created by Yubar on 11/30/2016.
 */
public interface MancipleRepository extends PagingAndSortingRepository<Manciple, Long>, JpaSpecificationExecutor<Manciple> {

    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    Manciple findByUsername(String username);

}
