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

    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    User findByUsername(String username);

    @Query("select max(u.username) from User u where u.username like ? ")
    String findLastUsernameLike(String like);

}
