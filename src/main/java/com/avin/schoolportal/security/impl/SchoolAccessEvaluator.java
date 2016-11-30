package com.avin.schoolportal.security.impl;

import com.avin.schoolportal.domain.Role;
import com.avin.schoolportal.domain.School;
import com.avin.schoolportal.domain.User;
import com.avin.schoolportal.repository.SchoolRepository;
import com.avin.schoolportal.security.AccessEvaluator;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Yubar on 10/28/2016.
 */

@Component("schoolAccessEvaluatorBean")
public class SchoolAccessEvaluator implements AccessEvaluator<School> {

    @Autowired
    SchoolRepository repository;

    @Override
    public boolean hasAccess(User user, School school, String permission) {
        switch (permission) {
            case "READ" :
                return user.getSchool().equals(school);
            case "UPDATE" :
                return user.getRoles().contains(Role.MANAGER) && user.getSchool().equals(school);
        }
        return false;
    }

    @Override
    public boolean hasAccess(User user, Serializable id, String permission) {
        School school = repository.findOne((Long) id);
        school.setId((Long) id);
        return hasAccess(user, school, permission);
    }
}
