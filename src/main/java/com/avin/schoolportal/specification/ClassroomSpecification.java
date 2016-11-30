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
public class ClassroomSpecification implements Specification<Classroom> {

    private Map<String, String> params;
    private School school;

    public ClassroomSpecification(Map<String, String> params, School school) {
        this.params = params;
        this.school = school;
    }

    @Override
    public Predicate toPredicate(Root<Classroom> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Class<?> clazz = criteriaQuery.getResultType();
        if (clazz.equals(Classroom.class))
            root.fetch(Classroom_.course);
        final List<Predicate> predicates = new ArrayList<>();
        Path<School> schoolPath = root.get(Classroom_.school);
        Path<Course> coursePath = root.get(Classroom_.course);
        predicates.add(criteriaBuilder.equal(schoolPath, school));
        if (params.get("filter.name") != null)
            predicates.add(criteriaBuilder.like(root.get(Classroom_.name), "%" + params.get("filter.name") + "%"));
        if (params.get("filter.course.id") != null)
            predicates.add(criteriaBuilder.equal(coursePath.get(Course_.id), params.get("filter.course.id")));
        if (params.get("filter.academicYear") != null)
            predicates.add(criteriaBuilder.equal(root.get(Classroom_.academicYear), Integer.valueOf(params.get("filter.academicYear"))));
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
