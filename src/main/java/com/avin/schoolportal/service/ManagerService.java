package com.avin.schoolportal.service;

import com.avin.schoolportal.domain.*;
import com.avin.schoolportal.repository.EmployeeRepository;
import com.avin.schoolportal.repository.PersonRepository;
import com.avin.schoolportal.repository.SchoolRepository;
import com.avin.schoolportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Yubar on 10/28/2016.
 */

@Service
@Transactional
public class ManagerService {

    @Autowired
    SchoolRepository schoolRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    SchoolUserService schoolUserService;

    @PreAuthorize("hasPermission(#school, 'UPDATE')")
    public void updateSchoolCourses(School school, List<Course> courses) {
        school = schoolRepository.findOne(school.getId());
        school.getCourses().clear();
        school.getCourses().addAll(courses);
    }

    @PreAuthorize("hasPermission(#employee, 'CREATE')")
    public Employee registerEmployee(Employee employee) {
        String lastUsername = userRepository.findLastUsernameLike(employee.getSchool().getCode() + employee.getUsernamePrefix() + "%");
        if (lastUsername == null)
            lastUsername = employee.getSchool().getCode() + employee.getUsernamePrefix() + "000";
        employee.setUsername(new BigDecimal(lastUsername).add(BigDecimal.ONE).toString());
        employee.setSchool(employee.getSchool());
        return (Employee) schoolUserService.registerSchoolUser(employee);
    }

    @PreAuthorize("hasPermission(#employee, 'UPDATE')")
    public Employee updateEmployee(Employee employee) {
        Employee emp = employeeRepository.findOne(employee.getId());
        return (Employee) schoolUserService.updateSchoolUser(emp);
    }

    @PreAuthorize("hasPermission(#employee, 'DELETE')")
    public void deleteEmployee(Employee employee) {
        schoolUserService.deleteUser(employee);
    }


}
