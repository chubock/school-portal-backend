package com.avin.schoolportal.repository;

import com.avin.schoolportal.domain.SchoolUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;

/**
 * Created by Yubar on 11/30/2016.
 */
public interface SchoolUserRepository extends UserRepository {

    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    SchoolUser findByUsername(String username);

}
