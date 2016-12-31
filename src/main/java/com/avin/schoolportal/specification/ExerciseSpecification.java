package com.avin.schoolportal.specification;

import com.avin.schoolportal.domain.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Yubar on 12/15/2016.
 */
public class ExerciseSpecification extends AbstractSpecification<Exercise> {

    private final Teacher teacher;
    private final Student student;

    public ExerciseSpecification(Map<String, String> params, Teacher teacher) {
        super(params, teacher.getSchool());
        this.teacher = teacher;
        this.student = null;
    }

    public ExerciseSpecification(Map<String, String> params, Student student) {
        super(params, student.getSchool());
        this.teacher = null;
        this.student = student;
    }

    protected void fetchProperties(Root<Exercise> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        root.fetch(Exercise_.study);
        root.fetch(Exercise_.course);
    }

    @Override
    protected List<Predicate> additionalPredicates(Root<Exercise> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (teacher != null)
            predicates.add(criteriaBuilder.equal(root.get(Exercise_.teacher), teacher));
        return predicates;
    }

    Predicate createPredicate(Root<Exercise> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder, String key) {
        String value = params.get(key);
        switch (key) {
            case "filter.title":
                return criteriaBuilder.like(root.get(Exercise_.title), "%" + value + "%");
            case "filter.study.id":
                return criteriaBuilder.equal(root.get(Exercise_.study).get(Study_.id), Long.valueOf(value));
            case "filter.academicYear":
                return criteriaBuilder.equal(root.get(Exercise_.academicYear), Integer.valueOf(value));
            case "filter.course.id":
                return criteriaBuilder.equal(root.get(Exercise_.course).get(Course_.id), Long.valueOf(value));
            default:
                return null;
        }
    }
}
