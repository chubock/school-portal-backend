package com.avin.schoolportal.specification;

import com.avin.schoolportal.domain.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Yubar on 12/15/2016.
 */
public class ExamSpecification extends AbstractSpecification<Exam> {

    private final Teacher teacher;
    private final Student student;

    public ExamSpecification(Map<String, String> params, Teacher teacher) {
        super(params, teacher.getSchool());
        this.teacher = teacher;
        this.student = null;
    }

    public ExamSpecification(Map<String, String> params, Student student) {
        super(params, student.getSchool());
        this.student = student;
        this.teacher = null;
    }

    @Override
    protected void fetchProperties(Root<Exam> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        root.fetch(Exam_.study);
        root.fetch(Exam_.course);
    }

    @Override
    Predicate createPredicate(Root<Exam> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder, String key) {
        String value = params.get(key);
        switch (key) {
            case "filter.title":
                return criteriaBuilder.like(root.get(Exam_.title), "%" + value + "%");
            case "filter.type":
                return criteriaBuilder.equal(root.get(Exam_.type), ExamType.valueOf(value));
            case "filter.study.id":
                return criteriaBuilder.equal(root.get(Exam_.study).get(Study_.id), Long.valueOf(value));
            case "filter.academicYear":
                return criteriaBuilder.equal(root.get(Exam_.academicYear), Integer.valueOf(value));
            case "filter.course.id":
                return criteriaBuilder.equal(root.get(Exam_.course).get(Course_.id), Long.valueOf(value));
            default:
                return null;
        }
    }

    @Override
    protected List<Predicate> additionalPredicates(Root<Exam> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (teacher != null)
            predicates.add(criteriaBuilder.equal(root.get(Exam_.teacher), teacher));
        return predicates;
    }
}
