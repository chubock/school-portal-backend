package com.avin.schoolportal.security.impl;

import com.avin.schoolportal.domain.Role;
import com.avin.schoolportal.domain.User;
import com.avin.schoolportal.repository.UserRepository;
import com.avin.schoolportal.security.AccessEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Yubar on 10/24/2016.
 */
@Component("userAccessEvaluatorBean")
public class UserAccessEvaluator implements AccessEvaluator<User> {

    @Autowired
    UserRepository repository;

    @Override
    public boolean hasAccess(User user, User target, String permission) {
        switch (permission) {
            case "CREATE":
            case "READ" :
            case "UPDATE":
                return user.equals(target) || user.getRoles().contains(Role.MANAGER) && user.getSchool().equals(target.getSchool());
        }
        return false;
    }

    @Override
    public boolean hasAccess(User user, Serializable username, String permission) {
        User u = repository.findByUsername((String) username);
        return hasAccess(user, u, permission);
    }
}
