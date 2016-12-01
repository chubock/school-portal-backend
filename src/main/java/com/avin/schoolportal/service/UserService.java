package com.avin.schoolportal.service;

import com.avin.schoolportal.domain.Employee;
import com.avin.schoolportal.domain.Person;
import com.avin.schoolportal.domain.Role;
import com.avin.schoolportal.domain.User;
import com.avin.schoolportal.repository.PersonRepository;
import com.avin.schoolportal.repository.SchoolUserRepository;
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

