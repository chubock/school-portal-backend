package com.avin.schoolportal.security.impl;

import com.avin.schoolportal.domain.*;
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
        return hasAccess(user, school.getId(), permission);
    }

    @Override
    public boolean hasAccess(User user, Serializable id, String permission) {
        School school = repository.findOne((Long) id);
        if (user instanceof SchoolUser) {
            return ((SchoolUser)user).getSchool().equals(school) && (permission.equals("READ") || user instanceof Manager);
        }
        return false;
    }
}
