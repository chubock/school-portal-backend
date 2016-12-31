package com.avin.schoolportal.security.impl;

import com.avin.schoolportal.domain.Manager;
import com.avin.schoolportal.domain.SchoolUser;
import com.avin.schoolportal.domain.User;
import com.avin.schoolportal.repository.ManagerRepository;
import com.avin.schoolportal.security.AccessEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Yubar on 12/1/2016.
 */

@Component("managerAccessEvaluatorBean")
public class ManagerAccessEvaluator implements AccessEvaluator<Manager> {

    @Autowired
    ManagerRepository managerRepository;

    @Override
    public boolean hasAccess(User user, Manager manager, String permission) {
        return hasAccess(user, manager.getId(), permission);
    }

    @Override
    public boolean hasAccess(User user, Serializable id, String permission) {
        Manager manager = managerRepository.findOne((Long) id);
        return manager.equals(user);
    }
}
