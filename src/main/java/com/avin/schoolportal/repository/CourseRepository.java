package com.avin.schoolportal.repository;

import com.avin.schoolportal.domain.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Yubar on 10/20/2016.
 */

public interface CourseRepository extends PagingAndSortingRepository<Course, Long> {

    @Query(value = "select c from Course c left join fetch c.studies where c.id = ?")
    Course findOneWithStudies(long id);

}
