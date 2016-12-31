package com.avin.schoolportal.specification;

import com.avin.schoolportal.domain.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Yubar on 11/25/2016.
 */
public class StudentSpecification extends AbstractSpecification<Student> {

    public StudentSpecification(Map<String, String> params, School school) {
        super(params, school);
    }

    @Override
    protected void fetchProperties(Root<Student> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        root.fetch(Student_.course);
        root.fetch(Student_.classroom, JoinType.LEFT);
    }

    @Override
    Predicate createPredicate(Root<Student> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder, String key) {
        String value = params.get(key);
        switch (key) {
            case "filter.firstName":
                return criteriaBuilder.like(root.get(Student_.firstName), "%" + value + "%");
            case "filter.lastName":
                return criteriaBuilder.like(root.get(Student_.lastName), "%" + value + "%");
            case "filter.nationalId":
                return criteriaBuilder.like(root.get(Student_.nationalId), "%" + value + "%");
            case "filter.fatherName":
                return criteriaBuilder.like(root.get(Student_.fatherName), "%" + value + "%");
            case "filter.course.id":
                return criteriaBuilder.equal(root.get(Student_.course).get(Course_.id), value);
            case "filter.classroom.id":
                return value.equals("-1") ? criteriaBuilder.isNull(root.get(Student_.classroom)) : criteriaBuilder.equal(root.get(Student_.classroom).get(Classroom_.id), value);
            case "filter.academicYear":
                return criteriaBuilder.equal(root.get(Student_.academicYear), Integer.valueOf(value));
            case "filter.lastYearGrade":
                return criteriaBuilder.equal(root.get(Student_.lastYearGrade), Double.valueOf(value));
            default:
                return null;
        }
    }
}
