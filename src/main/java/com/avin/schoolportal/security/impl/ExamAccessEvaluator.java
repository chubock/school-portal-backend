package com.avin.schoolportal.security.impl;

import com.avin.schoolportal.domain.*;
import com.avin.schoolportal.repository.ExamRepository;
import com.avin.schoolportal.security.AccessEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Yubar on 12/9/2016.
 */

@Component("examAccessEvaluatorBean")
public class ExamAccessEvaluator implements AccessEvaluator<Exam> {

    @Autowired
    ExamRepository examRepository;

    @Override
    public boolean hasAccess(User user, Exam exam, String permission) {
        if (permission.equals("CREATE")) {
            if (! exam.getTeacher().equals(user))
                return false;
            return true;
        } else {
            return hasAccess(user, exam.getId(), permission);
        }
    }

    @Override
    public boolean hasAccess(User user, Serializable id, String permission) {
        Exam exam = examRepository.findOne((Long) id);
        if (user instanceof SchoolUser && ((SchoolUser)user).getSchool().equals(exam.getSchool())) {
            if (permission.equals("READ"))
                return user instanceof Manager || user instanceof Manciple || (user instanceof Teacher && exam.getTeacher().equals(user)) || (user instanceof Student && exam.getClassrooms().contains(((Student)user).getClassroom()));
            else
                return user instanceof Teacher && exam.getTeacher().equals(user);
        }
        return false;
    }
}
