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
public class SchoolUserSpecification implements Specification<SchoolUser> {

    private Map<String, String> params;
    private School school;

    public SchoolUserSpecification(Map<String, String> params, School school) {
        this.params = params;
        this.school = school;
    }

    @Override
    public Predicate toPredicate(Root<SchoolUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Class<?> clazz = criteriaQuery.getResultType();
        if (SchoolUser.class.isAssignableFrom(clazz))
            root.fetch(SchoolUser_.person);
        final List<Predicate> predicates = new ArrayList<>();
        Path<School> schoolPath = root.get(SchoolUser_.school);
        Path<Person> personPath = root.get(SchoolUser_.person);
        predicates.add(criteriaBuilder.equal(schoolPath, school));
        if (params.get("filter.person.firstName") != null)
            predicates.add(criteriaBuilder.like(personPath.get(Person_.firstName), "%" + params.get("filter.person.firstName") + "%"));
        if (params.get("filter.person.lastName") != null)
            predicates.add(criteriaBuilder.like(personPath.get(Person_.lastName), "%" + params.get("filter.person.lastName") + "%"));
        if (params.get("filter.person.nationalId") != null)
            predicates.add(criteriaBuilder.like(personPath.get(Person_.nationalId), "%" + params.get("filter.person.nationalId") + "%"));
        if (params.get("filter.person.gender") != null)
            predicates.add(criteriaBuilder.equal(personPath.get(Person_.gender), Gender.valueOf(params.get("filter.person.gender"))));
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
