package com.avin.schoolportal.repository;

import com.avin.schoolportal.domain.School;
import com.avin.schoolportal.repository.custom.RepositoryWithRefresh;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Yubar on 10/28/2016.
 */
public interface SchoolRepository extends RepositoryWithRefresh<School, Long> {

    @Query(value = "select max(s.code) from School s")
    public String findLastCode();
}
