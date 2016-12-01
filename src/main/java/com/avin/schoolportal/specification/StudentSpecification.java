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
public class StudentSpecification implements Specification<Student> {

    private Map<String, String> params;
    private School school;

    public StudentSpecification(Map<String, String> params, School school) {
        this.params = params;
        this.school = school;
    }

    @Override
    public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Class<?> clazz = criteriaQuery.getResultType();
        if (clazz.equals(Student.class)) {
            root.fetch(Student_.person);
            root.fetch(Student_.course);
            root.fetch(Student_.classroom);
        }
        final List<Predicate> predicates = new ArrayList<>();
        Path<School> schoolPath = root.get(Student_.school);
        predicates.add(criteriaBuilder.equal(schoolPath, school));
        Path<Person> personPath = root.get(Student_.person);
        Path<Course> coursePath = root.get(Student_.course);
        Path<Classroom> classroomPath = root.get(Student_.classroom);
        if (params.get("filter.person.firstName") != null)
            predicates.add(criteriaBuilder.like(personPath.get(Person_.firstName), "%" + params.get("filter.person.firstName") + "%"));
        if (params.get("filter.person.lastName") != null)
            predicates.add(criteriaBuilder.like(personPath.get(Person_.lastName), "%" + params.get("filter.person.lastName") + "%"));
        if (params.get("filter.person.nationalId") != null)
            predicates.add(criteriaBuilder.like(personPath.get(Person_.nationalId), "%" + params.get("filter.person.nationalId") + "%"));
        if (params.get("filter.person.fatherName") != null)
            predicates.add(criteriaBuilder.like(personPath.get(Person_.fatherName), "%" + params.get("filter.person.fatherName") + "%"));
        if (params.get("filter.course.id") != null)
            predicates.add(criteriaBuilder.equal(coursePath.get(Course_.id), params.get("filter.course.id")));
        if (params.get("filter.classroom.id") != null)
            predicates.add(criteriaBuilder.equal(classroomPath.get(Classroom_.id), params.get("filter.classroom.id")));
        if (params.get("filter.academicYear") != null)
            predicates.add(criteriaBuilder.equal(root.get(Student_.academicYear), Integer.valueOf(params.get("filter.academicYear"))));
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
