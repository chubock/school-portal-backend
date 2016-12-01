package com.avin.schoolportal.service;

import com.avin.schoolportal.domain.*;
import com.avin.schoolportal.repository.EmployeeRepository;
import com.avin.schoolportal.repository.PersonRepository;
import com.avin.schoolportal.repository.SchoolRepository;
import com.avin.schoolportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;

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
    PersonRepository personRepository;

    @Autowired
    UserService userService;

    public School register(School school) {

        Manager manager = school.getManager();
        String lastCode = schoolRepository.findLastCode();

        if (lastCode == null)
            lastCode = "100";
        school.setCode(String.valueOf(Integer.valueOf(lastCode) + 1));

        school = schoolRepository.save(school);

        Person person = manager.getPerson();
        if (person.getId() == 0)
            person = personRepository.save(person);
        else
            person = personRepository.findOne(person.getId());

        manager.setSchool(school);
        manager.setPerson(person);
        manager.setPassword(userService.encodePassword(manager.getPassword()));
        userRepository.save(manager);
        return school;

    }
}
