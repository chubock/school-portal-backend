package com.avin.schoolportal.security.impl;

import com.avin.schoolportal.domain.*;
import com.avin.schoolportal.repository.EmployeeRepository;
import com.avin.schoolportal.security.AccessEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Yubar on 10/28/2016.
 */

@Component("employeeAccessEvaluatorBean")
public class EmployeeAccessEvaluator implements AccessEvaluator<Employee> {

    @Autowired
    EmployeeRepository repository;

    @Override
    public boolean hasAccess(User user, Employee employee, String permission) {
        if (permission.equals("CREATE")) {
            return user instanceof Manager && employee.getSchool().equals(((Manager)user).getSchool());
        } else {
            return hasAccess(user, employee.getId(), permission);
        }
    }

    @Override
    public boolean hasAccess(User user, Serializable id, String permission) {
        Employee employee = repository.findOne((Long) id);
        if (user instanceof SchoolUser) {
            SchoolUser schoolUser = (SchoolUser) user;
            return schoolUser.getSchool().equals(employee.getSchool()) && ((permission.equals("READ") || schoolUser instanceof Manager));
        }
        return false;
    }
}
