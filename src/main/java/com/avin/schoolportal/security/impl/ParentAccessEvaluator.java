package com.avin.schoolportal.security.impl;

import com.avin.schoolportal.domain.*;
import com.avin.schoolportal.repository.ParentRepository;
import com.avin.schoolportal.security.AccessEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Yubar on 11/25/2016.
 */

@Component(value = "parentAccessEvaluatorBean")
public class ParentAccessEvaluator implements AccessEvaluator<Parent> {

    @Autowired
    ParentRepository parentRepository;

    @Override
    public boolean hasAccess(User user, Parent parent, String permission) {
        if (permission.equals("CREATE")) {
            if (user instanceof SchoolUser) {
                return ((SchoolUser)user).getSchool().equals(parent.getSchool()) && (user instanceof Manciple || user instanceof Manager);
            } else {
                return false;
            }
        } else {
            return hasAccess(user, parent.getId(), permission);
        }
    }

    @Override
    public boolean hasAccess(User user, Serializable id, String permission) {
        Parent parent = parentRepository.findOne((Long) id);
        if (user instanceof SchoolUser) {
            return ((SchoolUser)user).getSchool().equals(parent.getSchool()) && (permission.equals("READ") || user instanceof Manciple || user instanceof Manager);
        }
        return false;
    }
}
