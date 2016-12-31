package com.avin.schoolportal.repository;

import com.avin.schoolportal.domain.Violation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Yubar on 12/4/2016.
 */
public interface ViolationRepository extends PagingAndSortingRepository<Violation, Long>, JpaSpecificationExecutor<Violation> {

    @Query(value = "select v from Violation v join fetch v.students s where v.id = ?1")
    Violation findOneWithStudents(Long id);

}
