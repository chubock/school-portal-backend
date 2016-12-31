package com.avin.schoolportal.specification;

import com.avin.schoolportal.domain.School;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Yubar on 12/27/2016.
 */

abstract class AbstractSpecification<T> implements Specification<T> {

    protected final Map<String, String> params;
    protected final School school;
    protected final String schoolPropertyName;

    public AbstractSpecification(Map<String, String> params, School school) {
        this.params = params;
        this.school = school;
        this.schoolPropertyName = "school";
    }

    public AbstractSpecification(Map<String, String> params, School school, String schoolPropertyName) {
        this.params = params;
        this.school = school;
        this.schoolPropertyName = schoolPropertyName;
    }

    @Override
    public final Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Class<?> clazz = criteriaQuery.getResultType();
        if (! clazz.equals(Long.class)) {
            fetchProperties(root, criteriaQuery, criteriaBuilder);
        }
        final List<Predicate> predicates = new ArrayList<>(additionalPredicates(root, criteriaQuery, criteriaBuilder));
        predicates.add(criteriaBuilder.equal(root.get(schoolPropertyName), school));
        for (String key: params.keySet()) {
            Predicate predicate = createPredicate(root, criteriaQuery, criteriaBuilder, key);
            if (predicate != null)
                predicates.add(predicate);
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }

    protected void fetchProperties(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

    }

    protected List<Predicate> additionalPredicates(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return Collections.emptyList();
    }

    abstract Predicate createPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder, String key);

}
