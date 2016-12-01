package com.avin.schoolportal.security.impl;

import com.avin.schoolportal.domain.Manager;
import com.avin.schoolportal.domain.SchoolUser;
import com.avin.schoolportal.domain.User;
import com.avin.schoolportal.repository.UserRepository;
import com.avin.schoolportal.security.AccessEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Yubar on 11/30/2016.
 */

@Component("userAccessEvaluatorBean")
public class UserAccessEvaluator implements AccessEvaluator<User> {

    @Autowired
    SchoolUserAccessEvaluator schoolUserAccessEvaluator;

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean hasAccess(User user, User target, String permission) {
        if (target instanceof SchoolUser)
            return schoolUserAccessEvaluator.hasAccess(user, (SchoolUser) target, permission);
        return false;
    }

    @Override
    public boolean hasAccess(User user, Serializable username, String permission) {
        User u = userRepository.findByUsername((String) username);
        return hasAccess(user, u, permission);
    }
}
