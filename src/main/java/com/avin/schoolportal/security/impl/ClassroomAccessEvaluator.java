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
        if (user instanceof SchoolUser) {
            SchoolUser schoolUser = (SchoolUser) user;
            switch (permission) {
                case "CREATE":
                case "UPDATE":
                case "DELETE":
                    return schoolUser instanceof Manciple && classroom.getSchool().equals(schoolUser.getSchool());
                case "READ":
                    return schoolUser.getSchool().equals(classroom.getSchool());
            }
        }
        return false;
    }

    @Override
    public boolean hasAccess(User user, Serializable id, String permission) {
        Classroom classroom = classroomRepository.findOne((Long) id);
        return hasAccess(user, classroom, permission);
    }
}
