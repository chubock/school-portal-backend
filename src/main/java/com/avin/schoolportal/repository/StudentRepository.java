package com.avin.schoolportal.repository;

import com.avin.schoolportal.domain.School;
import com.avin.schoolportal.domain.Student;
import com.avin.schoolportal.domain.User;
import com.avin.schoolportal.repository.custom.ExtendedRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Yubar on 11/25/2016.
 */
public interface StudentRepository extends ExtendedRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    @EntityGraph(attributePaths = {"course", "classroom"})
    Student findByUsername(String username);

    Page<Student> findByUsernameLikeAndSchool(String username, School school, Pageable pageable);
}
