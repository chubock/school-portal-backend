package com.avin.schoolportal.repository;

import com.avin.schoolportal.domain.SchoolUser;
import com.avin.schoolportal.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.QueryHint;

/**
 * Created by Yubar on 11/30/2016.
 */
public interface TeacherRepository extends JpaRepository<Teacher, Long>, JpaSpecificationExecutor<Teacher> {

    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    Teacher findByUsername(String username);

}
