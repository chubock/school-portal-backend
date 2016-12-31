package com.avin.schoolportal.service;

import com.avin.schoolportal.domain.*;
import com.avin.schoolportal.repository.FileRepository;
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

    @Autowired
    FileRepository fileRepository;

    @Async
    public void sendRegistrationEmail(SchoolUser user, String password) {
        userService.sendMail(user.getEmail(), "Registration Details", "User registration is complete. username : " + user.getUsername() + " ,password : " + password);
    }

    @PreAuthorize("hasPermission(#user, 'CREATE')")
    public SchoolUser registerSchoolUser(SchoolUser user) {
        Person person = personRepository.findByNationalId(user.getNationalId());
        if (person == null) {
            person = user.getPerson();
            personRepository.save(person);
        }
        user.setPassword(userService.encodePassword(userService.generatePassword()));
        user.setPasswordExpired(true);
        user = schoolUserRepository.save(user);
        return user;
    }

    @PreAuthorize("hasPermission(#user, 'UPDATE')")
    public SchoolUser updateSchoolUser(SchoolUser user) {
        SchoolUser u = schoolUserRepository.findOne(user.getId());
        u.setFirstName(user.getFirstName());
        u.setLastName(user.getLastName());
        u.setFatherName(user.getFatherName());
        u.setNationalId(user.getNationalId());
        u.setBirthday(user.getBirthday());
        u.setGender(user.getGender());
        u.setEmail(user.getEmail());
        u.setPhoneNumber(user.getPhoneNumber());
        u.setLocale(user.getLocale());
        return u;
    }

    @PreAuthorize("hasPermission(#schoolUser, 'UPDATE')")
    public void updateSchoolUserPictureFile(SchoolUser schoolUser) {
        SchoolUser s = schoolUserRepository.findOne(schoolUser.getId());
        if (s.getPictureFile() != null) {
            fileRepository.delete(schoolUser.getPictureFile());
        }
        s.setPictureFile(fileRepository.save(schoolUser.getPictureFile()));
    }


    @PreAuthorize("hasPermission(#user, 'DELETE')")
    public void deleteUser(SchoolUser user) {
        schoolUserRepository.delete(user);
    }

}
