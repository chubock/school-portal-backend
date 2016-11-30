package com.avin.schoolportal.security.impl;

import com.avin.schoolportal.domain.Role;
import com.avin.schoolportal.domain.Student;
import com.avin.schoolportal.domain.User;
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
        switch (permission) {
            case "CREATE":
            case "UPDATE":
            case "DELETE":
                return user.getSchool().equals(student.getSchool()) && user.getRoles().contains(Role.MANCIPLE);
            case "READ":
                return user.getSchool().equals(student.getSchool());

        }
        return false;
    }

    @Override
    public boolean hasAccess(User user, Serializable id, String permission) {
        Student student = studentRepository.findOne((Long) id);
        return hasAccess(user, student, permission);
    }
}
