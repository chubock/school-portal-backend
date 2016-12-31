package com.avin.schoolportal.security.impl;

import com.avin.schoolportal.domain.Handout;
import com.avin.schoolportal.domain.Teacher;
import com.avin.schoolportal.domain.User;
import com.avin.schoolportal.repository.HandoutRepository;
import com.avin.schoolportal.security.AccessEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Yubar on 12/30/2016.
 */

@Component("handoutAccessEvaluatorBean")
public class HandoutAccessEvaluator implements AccessEvaluator<Handout> {

    @Autowired
    HandoutRepository handoutRepository;

    @Override
    public boolean hasAccess(User user, Handout handout, String permission) {
        if (permission.equals("CREATE")){
            return handout.getTeacher().equals(user);
        } else {
            return hasAccess(user, handout.getId(), permission);
        }
    }

    @Override
    public boolean hasAccess(User user, Serializable id, String permission) {
        Handout handout = handoutRepository.findOne((Long) id);
        switch (permission) {
            case "READ":
                return true;
            case "UPDATE":
            case "DELETE":
                return handout.getTeacher().equals(user);
            default:
                return false;
        }
    }
}
