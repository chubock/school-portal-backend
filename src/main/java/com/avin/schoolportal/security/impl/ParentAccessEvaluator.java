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
        if (user instanceof SchoolUser) {
            SchoolUser schoolUser = (SchoolUser) user;
            switch (permission) {
                case "CREATE":
                case "UPDATE":
                case "DELETE":
                    return schoolUser instanceof Manciple && schoolUser.getSchool().equals(parent.getSchool());
                case "READ":
                    return schoolUser.getSchool().equals(parent.getSchool());
            }
        }

        return false;
    }

    @Override
    public boolean hasAccess(User user, Serializable id, String permission) {
        Parent parent = parentRepository.findOne((Long) id);
        return hasAccess(user, parent, permission);
    }
}
