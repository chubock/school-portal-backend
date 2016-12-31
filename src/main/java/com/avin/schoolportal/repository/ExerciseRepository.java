package com.avin.schoolportal.repository;

import com.avin.schoolportal.domain.Exercise;
import com.avin.schoolportal.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Yubar on 12/27/2016.
 */
public interface ExerciseRepository extends JpaRepository<Exercise, Long>, JpaSpecificationExecutor<Exercise> {

    @Query(value = "select e from Exercise e left join fetch e.questions left join fetch e.answers left join fetch e.course left join fetch e.study left join fetch e.classrooms where e.id = ?1")
    Exercise findOneCompletely(long id);

    @Query(value = "select e.wrongdoers from Exercise e where e.id = ?1")
    List<Student> findWrongdoers(long id);
}
