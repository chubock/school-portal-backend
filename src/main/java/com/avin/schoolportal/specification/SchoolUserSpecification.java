package com.avin.schoolportal.specification;

import com.avin.schoolportal.domain.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Yubar on 11/16/2016.
 */
public abstract class SchoolUserSpecification<T extends SchoolUser> extends AbstractSpecification<T> {

    public SchoolUserSpecification(Map<String, String> params, School school) {
        super(params, school);
    }

    @Override
    Predicate createPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder, String key) {
        String value = params.get(key);
        switch (key) {
            case "filter.firstName":
                return criteriaBuilder.like(root.get(SchoolUser_.firstName), "%" + value + "%");
            case "filter.lastName":
                return criteriaBuilder.like(root.get(SchoolUser_.lastName), "%" + value + "%");
            case "filter.nationalId":
                return criteriaBuilder.like(root.get(SchoolUser_.nationalId), "%" + value + "%");
            case "filter.gender":
                return criteriaBuilder.equal(root.get(SchoolUser_.gender), Gender.valueOf(value));
            default:
                return null;
        }
    }
}
