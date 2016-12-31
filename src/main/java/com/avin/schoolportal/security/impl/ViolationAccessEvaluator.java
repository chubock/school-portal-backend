package com.avin.schoolportal.security.impl;

import com.avin.schoolportal.domain.*;
import com.avin.schoolportal.repository.UserRepository;
import com.avin.schoolportal.repository.ViolationRepository;
import com.avin.schoolportal.security.AccessEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Yubar on 12/5/2016.
 */

@Component("violationAccessEvaluatorBean")
public class ViolationAccessEvaluator implements AccessEvaluator<Violation> {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ViolationRepository violationRepository;

    @Override
    public boolean hasAccess(User user, Violation violation, String permission) {
        if (permission.equals("CREATE")) {
            if (user instanceof SchoolUser)
                return ((SchoolUser)user).getSchool().equals(violation.getSchool()) && (permission.equals("READ") || user instanceof Manager || user instanceof Manciple);
            return false;
        } else {
            return hasAccess(user, violation.getId(), permission);
        }
    }

    @Override
    public boolean hasAccess(User user, Serializable id, String permission) {
        Violation violation = violationRepository.findOne((Long) id);
        if (user instanceof SchoolUser) {
            return ((SchoolUser)user).getSchool().equals(violation.getSchool()) && (permission.equals("READ") || user instanceof Manager || user instanceof Manciple);
        }
        return false;
    }
}
