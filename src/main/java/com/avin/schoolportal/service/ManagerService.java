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
    UserService userService;

    @PreAuthorize("hasPermission(#school, 'UPDATE')")
    public void updateSchoolCourses(School school, List<Course> courses) {
        school = schoolRepository.findOne(school.getId());
        school.getCourses().clear();
        school.getCourses().addAll(courses);
    }

    @PreAuthorize("hasPermission(#employee, 'CREATE')")
    public Employee registerEmployee(Employee employee) {
        User user = employee.getUser();
        user.setUsername(employee.getSchool().getCode() + '0'); // 0 for employees, 1 for students, 2 for parents.
        user.setSchool(employee.getSchool());
        user.getRoles().remove(Role.MANAGER);
        employee.setUser(userService.registerUser(user));
        return employeeRepository.save(employee);
    }

    @PreAuthorize("hasPermission(#employee, 'UPDATE')")
    public Employee updateEmployee(Employee employee) {
        Employee emp = employeeRepository.findOne(employee.getId());
        userService.updateUser(emp.getUser());
        employee.getUser().getRoles().remove(Role.MANAGER);
        userService.updateUserRoles(emp.getUser().getUsername(), employee.getUser().getRoles());
        return emp;
    }

    @PreAuthorize("hasPermission(#employee, 'DELETE')")
    public void deleteEmployee(Employee employee) {
        employeeRepository.delete(employee);
    }


}
