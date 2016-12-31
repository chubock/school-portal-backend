package com.avin.schoolportal.repository;

import com.avin.schoolportal.domain.Study;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Yubar on 12/1/2016.
 */
public interface StudyRepository extends PagingAndSortingRepository<Study, Long> {
}
