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
public class EmployeeSpecification implements Specification<Employee> {

    private Map<String, String> params;
    private School school;

    public EmployeeSpecification(Map<String, String> params, School school) {
        this.params = params;
        this.school = school;
    }

    @Override
    public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Class<?> clazz = criteriaQuery.getResultType();
        if (clazz.equals(Employee.class))
            root.fetch(Employee_.user).fetch(User_.person);
        final List<Predicate> predicates = new ArrayList<>();
        Path<School> schoolPath = root.get(Employee_.school);
        Path<Person> personPath = root.get(Employee_.user).get(User_.person);
        predicates.add(criteriaBuilder.equal(schoolPath, school));
        if (params.get("filter.user.person.firstName") != null)
            predicates.add(criteriaBuilder.like(personPath.get(Person_.firstName), "%" + params.get("filter.user.person.firstName") + "%"));
        if (params.get("filter.user.person.lastName") != null)
            predicates.add(criteriaBuilder.like(personPath.get(Person_.lastName), "%" + params.get("filter.user.person.lastName") + "%"));
        if (params.get("filter.user.person.nationalId") != null)
            predicates.add(criteriaBuilder.like(personPath.get(Person_.nationalId), "%" + params.get("filter.user.person.nationalId") + "%"));
        if (params.get("filter.user.person.gender") != null)
            predicates.add(criteriaBuilder.equal(personPath.get(Person_.gender), Gender.valueOf(params.get("filter.user.person.gender"))));
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
