package com.avin.schoolportal.repository;

import com.avin.schoolportal.domain.Exam;
import com.avin.schoolportal.domain.ExamScore;
import com.avin.schoolportal.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Yubar on 12/17/2016.
 */
public interface ExamScoreRepository extends JpaRepository<ExamScore, Long> {

    ExamScore findByExamAndStudent(Exam exam, Student student);
}
