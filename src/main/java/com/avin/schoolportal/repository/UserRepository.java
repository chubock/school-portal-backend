package com.avin.schoolportal.repository;

import com.avin.schoolportal.domain.User;
import com.avin.schoolportal.repository.custom.RepositoryWithRefresh;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.QueryHint;

/**
 * Created by Yubar on 10/22/2016.
 */

public interface UserRepository extends RepositoryWithRefresh<User, Long> {

    @Query("select u from User u join fetch u.roles where u.username = ?")
    User findOneWithRoles(String username);

    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    User findByUsername(String username);

}
