package com.avin.schoolportal.security.impl;

import com.avin.schoolportal.domain.*;
import com.avin.schoolportal.repository.ClassTimeRepository;
import com.avin.schoolportal.security.AccessEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.Serializable;

import static com.avin.schoolportal.domain.User_.id;

/**
 * Created by Yubar on 12/2/2016.
 */

@Component("classTimeAccessEvaluatorBean")
public class ClassTimeAccessEvaluator implements AccessEvaluator<ClassTime> {

    @Autowired
    ClassTimeRepository classTimeRepository;

    @Override
    public boolean hasAccess(User user, ClassTime classTime, String permission) {
        return hasAccess(user, classTimeRepository.findOne(classTime.getId()), permission);
    }

    @Override
    public boolean hasAccess(User user, Serializable id, String permission) {
        ClassTime classTime = classTimeRepository.findOne((Long) id);
        if (SchoolUser.class.isAssignableFrom(user.getClass())) {
            SchoolUser schoolUser = (SchoolUser) user;
            if (! classTime.getClassroom().getSchool().equals(schoolUser.getSchool()))
                return false;
            if (permission.equals("READ"))
                return true;
            else
                return user instanceof Manager || user instanceof Manciple;
        }
        return false;
    }
}
