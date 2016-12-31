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
 * Created by Yubar on 12/30/2016.
 */
public class HandoutSpecification extends AbstractSpecification<Handout> {

    private final Teacher teacher;

    public HandoutSpecification(Map<String, String> params, Teacher teacher) {
        super(params, teacher.getSchool());
        this.teacher = teacher;
    }

    @Override
    protected void fetchProperties(Root<Handout> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        root.fetch(Handout_.course);
        root.fetch(Handout_.study);
        if (teacher == null)
            root.fetch(Handout_.teacher);
    }

    @Override
    protected List<Predicate> additionalPredicates(Root<Handout> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (teacher != null)
            predicates.add(criteriaBuilder.equal(root.get(Handout_.teacher), teacher));
        return predicates;
    }

    @Override
    Predicate createPredicate(Root<Handout> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder, String key) {
        String value = params.get(key);
        switch (key) {
            case "filter.title":
                return criteriaBuilder.like(root.get(Handout_.title), "%" + value + "%");
            case "filter.course.id":
                return criteriaBuilder.equal(root.get(Handout_.course).get(Course_.id), Long.valueOf(value));
            case "filter.study.id":
                return criteriaBuilder.equal(root.get(Handout_.study).get(Study_.id), Long.valueOf(value));
            case "filter.teacher.id":
                return criteriaBuilder.equal(root.get(Handout_.teacher).get(Teacher_.id), Long.valueOf(value));
            default:
                return null;
        }
    }
}
