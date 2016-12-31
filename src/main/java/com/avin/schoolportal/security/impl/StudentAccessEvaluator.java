package com.avin.schoolportal.security.impl;

import com.avin.schoolportal.domain.*;
import com.avin.schoolportal.repository.StudentRepository;
import com.avin.schoolportal.security.AccessEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Yubar on 11/25/2016.
 */

@Component(value = "studentAccessEvaluatorBean")
public class StudentAccessEvaluator implements AccessEvaluator<Student> {

    @Autowired
    StudentRepository studentRepository;

    @Override
    public boolean hasAccess(User user, Student student, String permission) {
        if (permission.equals("CREATE")) {
            if (user instanceof SchoolUser)
                return student.getSchool().equals(((SchoolUser)user).getSchool()) && (user instanceof Manager || user instanceof Manciple);
            else
                return false;
        } else {
            return hasAccess(user, student.getId(), permission);
        }
    }

    @Override
    public boolean hasAccess(User user, Serializable id, String permission) {
        Student student = studentRepository.findOne((Long) id);
        if (user instanceof SchoolUser) {
            return ((SchoolUser)user).getSchool().equals(student.getSchool()) && (permission.equals("READ") || user instanceof Manciple || user instanceof Manager);
        }
        return false;
    }
}
