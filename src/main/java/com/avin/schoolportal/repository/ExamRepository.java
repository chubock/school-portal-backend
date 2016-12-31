package com.avin.schoolportal.repository;

import com.avin.schoolportal.domain.Exam;
import com.avin.schoolportal.domain.ExamScore;
import com.avin.schoolportal.repository.custom.ExtendedRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Yubar on 12/9/2016.
 */
public interface ExamRepository extends ExtendedRepository<Exam, Long>, JpaSpecificationExecutor<Exam> {

    @Query(value = "select e from Exam e left join fetch e.questions left join fetch e.answers left join fetch e.course left join fetch e.study left join fetch e.classrooms where e.id = ?1")
    Exam findOneCompletely(long id);

    @Query(value = "select s from ExamScore s join fetch s.student where s.exam.id = ?1")
    List<ExamScore> findScores(long id);

}
