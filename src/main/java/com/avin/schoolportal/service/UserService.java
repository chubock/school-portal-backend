package com.avin.schoolportal.service;

import com.avin.schoolportal.domain.Employee;
import com.avin.schoolportal.domain.Person;
import com.avin.schoolportal.domain.Role;
import com.avin.schoolportal.domain.User;
import com.avin.schoolportal.repository.PersonRepository;
import com.avin.schoolportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Created by Yubar on 11/18/2016.
 */

@Service
@Transactional
public class UserService {

    @Autowired
    PersonRepository personRepository;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private JavaMailSender mailSender;

    private SecureRandom random = new SecureRandom();
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    UserRepository userRepository;

    public String encodePassword(String password) {
        return encoder.encode(password);
    }

    public String generatePassword() {
        return new BigInteger(40, random).toString(32);
    }

    public void sendMail(String to, String subject, String text) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(text);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendPasswordEmail(String email, String password) {
        sendMail(email, "Generated Password", "Generated password is : " + password);
    }

    @Async
    public void sendRegistrationEmail(User user, String password) {
        sendMail(user.getEmail(), "Registration Details", "User registration is complete. username : " + user.getUsername() + " ,password : " + password);
    }

    @PreAuthorize("hasPermission(#user, 'CREATE')")
    public User registerUser(User user) {
        Person person = user.getPerson();
        if (person.getId() == 0)
            person = personRepository.save(person);
        else
            person = personRepository.findOne(person.getId());
        user.setPerson(person);
        user.setPassword(encodePassword(generatePassword()));
        user.setPasswordExpired(true);
        user = userRepository.save(user);
        userRepository.refresh(user);
        return user;
    }

    @PreAuthorize("hasPermission(#user, 'UPDATE')")
    public User updateUser(User user) {
        User u = userRepository.findByUsername(user.getUsername());
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

    @PreAuthorize("hasAuthority('MANAGER') AND hasPermission(#username, 'User', 'UPDATE')")
    public User updateUserRoles(String username, List<Role> roles) {
        User user = userRepository.findByUsername(username);
        user.getRoles().clear();
        user.getRoles().addAll(roles);
        return user;
    }

    public void changePassword(String username, String currentPassword, String newPassword) throws IllegalAccessException {
        if (currentPassword.equals(newPassword))
            throw new IllegalArgumentException();
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new IllegalAccessException();
        if (encoder.matches(currentPassword, user.getPassword())) {
            user.setPassword(encoder.encode(newPassword));
            user.setPasswordExpired(false);
        } else {
            throw new IllegalAccessException();
        }
    }

    @PreAuthorize("hasAuthority('MANAGER') AND hasPermission(#username, 'User', 'UPDATE')")
    public String resetPassword(String username) {
        User user = userRepository.findByUsername(username);
        String newPassword = generatePassword();
        user.setPassword(encodePassword(newPassword));
        user.setPasswordExpired(true);
        return newPassword;
    }

    @PreAuthorize("hasAuthority('MANAGER') AND hasPermission(#username, 'User', 'UPDATE')")
    public void lockUser(String username) {
        User user = userRepository.findByUsername(username);
        user.setLocked(true);
    }

    @PreAuthorize("hasAuthority('MANAGER') AND hasPermission(#username, 'User', 'UPDATE')")
    public void unlockUser(String username) {
        User user = userRepository.findByUsername(username);
        user.setLocked(false);
    }


}

