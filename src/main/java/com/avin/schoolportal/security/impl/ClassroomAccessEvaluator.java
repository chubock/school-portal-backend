package com.avin.schoolportal.security.impl;

import com.avin.schoolportal.domain.Classroom;
import com.avin.schoolportal.domain.Role;
import com.avin.schoolportal.domain.User;
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
        switch (permission) {
            case "CREATE":
            case "UPDATE":
            case "DELETE":
                return user.getRoles().contains(Role.MANCIPLE) && classroom.getSchool().equals(user.getSchool());
            case "READ":
                return user.getSchool().equals(classroom.getSchool());
        }
        return false;
    }

    @Override
    public boolean hasAccess(User user, Serializable id, String permission) {
        Classroom classroom = classroomRepository.findOne((Long) id);
        return hasAccess(user, classroom, permission);
    }
}
