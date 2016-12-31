package com.avin.schoolportal.repository;

import com.avin.schoolportal.domain.Manager;
import com.avin.schoolportal.domain.Manciple;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.QueryHint;

/**
 * Created by Yubar on 12/8/2016.
 */
public interface ManagerRepository extends CrudRepository<Manager, Long> {

    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    Manager findByUsername(String username);

}
