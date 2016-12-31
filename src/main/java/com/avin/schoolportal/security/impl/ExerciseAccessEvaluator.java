package com.avin.schoolportal.security.impl;

import com.avin.schoolportal.domain.*;
import com.avin.schoolportal.repository.ExerciseRepository;
import com.avin.schoolportal.security.AccessEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Yubar on 12/9/2016.
 */

@Component("exerciseAccessEvaluatorBean")
public class ExerciseAccessEvaluator implements AccessEvaluator<Exercise> {

    @Autowired
    ExerciseRepository exerciseRepository;

    @Override
    public boolean hasAccess(User user, Exercise exercise, String permission) {
        if (permission.equals("CREATE")) {
            if (! exercise.getTeacher().equals(user))
                return false;
            return true;
        } else {
            return hasAccess(user, exercise.getId(), permission);
        }
    }

    @Override
    public boolean hasAccess(User user, Serializable id, String permission) {
        Exercise exercise = exerciseRepository.findOne((Long) id);
        if (user instanceof SchoolUser && ((SchoolUser)user).getSchool().equals(exercise.getSchool())) {
            if (permission.equals("READ"))
                return user instanceof Manager || user instanceof Manciple || (user instanceof Teacher && exercise.getTeacher().equals(user)) || (user instanceof Student && exercise.getClassrooms().contains(((Student)user).getClassroom()));
            else
                return user instanceof Teacher && exercise.getTeacher().equals(user);
        }
        return false;
    }
}
