package com.avin.schoolportal.security.impl;

import com.avin.schoolportal.domain.*;
import com.avin.schoolportal.repository.SchoolUserRepository;
import com.avin.schoolportal.repository.UserRepository;
import com.avin.schoolportal.security.AccessEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Yubar on 10/24/2016.
 */
@Component("schoolUserAccessEvaluatorBean")
public class SchoolUserAccessEvaluator implements AccessEvaluator<SchoolUser> {

    @Autowired
    SchoolUserRepository schoolUserRepository;

    @Autowired
    @Qualifier("employeeAccessEvaluatorBean")
    EmployeeAccessEvaluator employeeAccessEvaluator;

    @Autowired
    ManagerAccessEvaluator managerAccessEvaluator;

    @Override
    public boolean hasAccess(User user, SchoolUser target, String permission) {
        return hasAccess(user, target.getId(), permission);
    }

    @Override
    public boolean hasAccess(User user, Serializable id, String permission) {
        SchoolUser target = schoolUserRepository.findOne((Long) id);
        if (target instanceof Manager)
            return managerAccessEvaluator.hasAccess(user, id, permission);
        if (target instanceof Employee)
            return employeeAccessEvaluator.hasAccess(user, id, permission);
        if (user instanceof SchoolUser)
            return ((SchoolUser)user).getSchool().equals(target.getSchool()) && (permission.equals("READ") || user instanceof Manager || user instanceof Manciple);
        return false;
    }
}
