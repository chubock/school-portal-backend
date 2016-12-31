package com.avin.schoolportal.specification;

import com.avin.schoolportal.domain.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Yubar on 11/24/2016.
 */
public class ClassroomSpecification extends AbstractSpecification<Classroom> {

    public ClassroomSpecification(Map<String, String> params, School school) {
        super(params, school);
    }

    @Override
    protected void fetchProperties(Root<Classroom> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        root.fetch(Classroom_.course);
    }

    @Override
    Predicate createPredicate(Root<Classroom> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder, String key) {
        String value = params.get(key);
        switch (key) {
            case "filter.name":
                return criteriaBuilder.like(root.get(Classroom_.name), "%" + value + "%");
            case "filter.course.id":
                return criteriaBuilder.equal(root.get(Classroom_.course).get(Course_.id), value);
            case "filter.academicYear":
                return criteriaBuilder.equal(root.get(Classroom_.academicYear), Integer.valueOf(value));
            default:
                return null;
        }
    }

}
