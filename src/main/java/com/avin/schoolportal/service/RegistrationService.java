package com.avin.schoolportal.service;

import com.avin.schoolportal.domain.*;
import com.avin.schoolportal.repository.EmployeeRepository;
import com.avin.schoolportal.repository.PersonRepository;
import com.avin.schoolportal.repository.SchoolRepository;
import com.avin.schoolportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Yubar on 10/28/2016.
 */

@Service
@Transactional
public class RegistrationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SchoolRepository schoolRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    UserService userService;

    public School register(School school) {

        Employee employee = school.getEmployees().get(0);

        school = schoolRepository.save(school);
        schoolRepository.refresh(school);

        User user = employee.getUser();

        Person person = user.getPerson();
        if (person.getId() == 0)
            person = personRepository.save(person);
        else
            person = personRepository.findOne(person.getId());

        if (!user.getRoles().contains(Role.MANAGER))
            user.getRoles().add(Role.MANAGER);
        user.setSchool(school);
        user.setPerson(person);
        user.setPassword(userService.encodePassword(user.getPassword()));
        user = userRepository.save(user);

        employee.setUser(user);
        employee.setSchool(school);
        employeeRepository.save(employee);
        return school;
    }
}
