package com.avin.schoolportal.security.impl;

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
        return hasAccess(user, target.getId(), permission);
    }

    @Override
    public boolean hasAccess(User user, Serializable serializable, String permission) {
        User target = null;
        if (serializable instanceof String)
            target = userRepository.findByUsername((String)serializable);
        else
            target = userRepository.findOne((Long) serializable);
        if (target instanceof SchoolUser)
            return schoolUserAccessEvaluator.hasAccess(user, target.getId(), permission);
        return false;
    }
}
