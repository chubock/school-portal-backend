package com.avin.schoolportal.security.impl;

import com.avin.schoolportal.domain.Employee;
import com.avin.schoolportal.domain.Role;
import com.avin.schoolportal.domain.School;
import com.avin.schoolportal.domain.User;
import com.avin.schoolportal.repository.EmployeeRepository;
import com.avin.schoolportal.security.AccessEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Yubar on 10/28/2016.
 */

@Component(value = "employeeAccessEvaluatorBean")
public class EmployeeAccessEvaluator implements AccessEvaluator<Employee> {

    @Autowired
    EmployeeRepository repository;

    @Override
    public boolean hasAccess(User user, Employee employee, String permission) {
        switch (permission) {
            case "CREATE" :
            case "READ" :
            case "UPDATE":
            case "DELETE":
                return employee.getSchool().equals(user.getSchool()) && user.getRoles().contains(Role.MANAGER);
        }
        return false;
    }

    @Override
    public boolean hasAccess(User user, Serializable id, String permission) {
        Employee employee = repository.findOne((Long) id);
        return hasAccess(user, employee, permission);
    }
}
