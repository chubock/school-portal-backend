package com.avin.schoolportal.specification;

import com.avin.schoolportal.domain.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Yubar on 12/2/2016.
 */
public class ClassTimeSpecification extends AbstractSpecification<ClassTime> {

    private final Teacher teacher;

    public ClassTimeSpecification(Map<String, String> params, Teacher teacher) {
        super(params, teacher.getSchool());
        this.teacher = teacher;
    }

    @Override
    protected void fetchProperties(Root<ClassTime> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        root.fetch(ClassTime_.classroom).fetch(Classroom_.course);
        if (teacher == null)
            root.fetch(ClassTime_.teacher, JoinType.LEFT);
        root.fetch(ClassTime_.study);
    }

    @Override
    protected List<Predicate> additionalPredicates(Root<ClassTime> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (teacher != null)
            predicates.add(criteriaBuilder.equal(root.get(ClassTime_.teacher), teacher));
        return predicates;
    }

    @Override
    Predicate createPredicate(Root<ClassTime> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder, String key) {
        String value = params.get(key);
        switch (key) {
            case "filter.classroom.id":
                return criteriaBuilder.equal(root.get(ClassTime_.classroom).get(Classroom_.id), Long.valueOf(value));
            case "filter.study.id":
                return criteriaBuilder.equal(root.get(ClassTime_.study).get(Study_.id), Long.valueOf(value));
            case "filter.weekDay":
                return criteriaBuilder.equal(root.get(ClassTime_.weekDay), WeekDay.valueOf(value));
            case "filter.classroom.course.id":
                return criteriaBuilder.equal(root.get(ClassTime_.classroom).get(Classroom_.course).get(Course_.id), Long.valueOf(value));
            case "filter.classroom.academicYear":
                return criteriaBuilder.equal(root.get(ClassTime_.classroom).get(Classroom_.academicYear), Long.valueOf(value));
            default:
                return null;
        }
    }

}
