package com.avin.schoolportal.repository;

import com.avin.schoolportal.domain.Person;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Yubar on 10/28/2016.
 */
public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {
    public List<Person> findByNationalIdStartingWith(String key);
}
