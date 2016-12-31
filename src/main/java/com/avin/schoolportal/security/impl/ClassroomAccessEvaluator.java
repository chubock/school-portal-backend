package com.avin.schoolportal.security.impl;

import com.avin.schoolportal.domain.*;
import com.avin.schoolportal.repository.ClassroomRepository;
import com.avin.schoolportal.security.AccessEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Yubar on 11/24/2016.
 */

@Component(value = "classroomAccessEvaluatorBean")
public class ClassroomAccessEvaluator implements AccessEvaluator<Classroom> {

    @Autowired
    ClassroomRepository classroomRepository;

    @Override
    public boolean hasAccess(User user, Classroom classroom, String permission) {
        if (permission.equals("CREATE")) {
            return user instanceof Manciple && classroom.getSchool().equals(((Manciple)user).getSchool());
        } else {
            return hasAccess(user, classroom.getId(), permission);
        }
    }

    @Override
    public boolean hasAccess(User user, Serializable id, String permission) {
        Classroom classroom = classroomRepository.findOne((Long) id);
        if (user instanceof SchoolUser) {
            return classroom.getSchool().equals(((SchoolUser)user).getSchool()) && (permission.equals("READ") || user instanceof Manager || user instanceof Manciple);
        }
        return false;
    }
}
