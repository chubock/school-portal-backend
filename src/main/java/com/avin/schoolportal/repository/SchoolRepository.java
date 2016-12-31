package com.avin.schoolportal.repository;

import com.avin.schoolportal.domain.School;
import com.avin.schoolportal.repository.custom.ExtendedRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Yubar on 10/28/2016.
 */
public interface SchoolRepository extends ExtendedRepository<School, Long> {

    @Query(value = "select max(s.code) from School s")
    public String findLastCode();
}
