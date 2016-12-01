package com.avin.schoolportal.service;

import com.avin.schoolportal.domain.*;
import com.avin.schoolportal.repository.PersonRepository;
import com.avin.schoolportal.repository.SchoolUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Yubar on 11/30/2016.
 */

@Service
@Transactional
public class SchoolUserService {

    @Autowired
    UserService userService;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    SchoolUserRepository schoolUserRepository;

    @Async
    public void sendRegistrationEmail(SchoolUser user, String password) {
        userService.sendMail(user.getEmail(), "Registration Details", "User registration is complete. username : " + user.getUsername() + " ,password : " + password);
    }

    @PreAuthorize("hasPermission(#user, 'CREATE')")
    public SchoolUser registerSchoolUser(SchoolUser user) {
        Person person = user.getPerson();
        if (person.getId() == 0)
            person = personRepository.save(person);
        else
            person = personRepository.findOne(person.getId());
        user.setPerson(person);
        user.setPassword(userService.encodePassword(userService.generatePassword()));
        user.setPasswordExpired(true);
        user = schoolUserRepository.save(user);
        return user;
    }

    @PreAuthorize("hasPermission(#user, 'UPDATE')")
    public SchoolUser updateSchoolUser(SchoolUser user) {
        SchoolUser u = schoolUserRepository.findByUsername(user.getUsername());
        u.setEmail(user.getEmail());
        u.setPhoneNumber(user.getPhoneNumber());
        u.setLocale(user.getLocale());
        Person person;
        if (user.getPerson().getId() == 0) {
            person = personRepository.save(user.getPerson());
        } else {
            person = personRepository.findOne(user.getPerson().getId());
        }
        u.setPerson(person);
        return u;
    }


    @PreAuthorize("hasPermission(#user, 'DELETE')")
    public void deleteUser(SchoolUser user) {
        schoolUserRepository.delete(user);
    }

}
