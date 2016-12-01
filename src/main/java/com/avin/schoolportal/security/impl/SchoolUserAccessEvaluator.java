package com.avin.schoolportal.security.impl;

import com.avin.schoolportal.domain.*;
import com.avin.schoolportal.repository.SchoolUserRepository;
import com.avin.schoolportal.repository.UserRepository;
import com.avin.schoolportal.security.AccessEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Yubar on 10/24/2016.
 */
@Component("schoolUserAccessEvaluatorBean")
public class SchoolUserAccessEvaluator implements AccessEvaluator<SchoolUser> {

    @Autowired
    SchoolUserRepository schoolUserRepository;

    @Override
    public boolean hasAccess(User user, SchoolUser target, String permission) {
        if (user instanceof SchoolUser) {
            SchoolUser schoolUser = (SchoolUser) user;
            switch (permission) {
                case "CREATE":
                case "READ" :
                case "UPDATE":
                    return user.equals(target) || schoolUser instanceof Manager && schoolUser.getSchool().equals(target.getSchool());
            }
        }
        return false;
    }

    @Override
    public boolean hasAccess(User user, Serializable username, String permission) {
        SchoolUser u = schoolUserRepository.findByUsername((String) username);
        return hasAccess(user, u, permission);
    }
}
