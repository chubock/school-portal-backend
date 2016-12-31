package com.avin.schoolportal.repository;

import com.avin.schoolportal.domain.Handout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Yubar on 12/30/2016.
 */
public interface HandoutRepository extends JpaRepository<Handout, Long>, JpaSpecificationExecutor<Handout> {

    @Query(value = "select h from Handout h left join fetch h.course left join fetch h.study left join fetch h.teacher left join fetch h.file where h.id = ?1")
    Handout findOneCompletely(long id);

}
