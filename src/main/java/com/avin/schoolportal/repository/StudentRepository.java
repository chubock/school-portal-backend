package com.avin.schoolportal.repository;

import com.avin.schoolportal.domain.Student;
import com.avin.schoolportal.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Yubar on 11/25/2016.
 */
public interface StudentRepository extends PagingAndSortingRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    @EntityGraph(attributePaths = {"user", "course", "classroom"})
    Student findByUser(User user);
}
