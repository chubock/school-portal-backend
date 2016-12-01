package com.avin.schoolportal.rest;

import com.avin.schoolportal.domain.*;
import com.avin.schoolportal.dto.*;
import com.avin.schoolportal.repository.EmployeeRepository;
import com.avin.schoolportal.repository.SchoolUserRepository;
import com.avin.schoolportal.service.ManagerService;
import com.avin.schoolportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

/**
 * Created by Yubar on 10/22/2016.
 */

@RestController
@RequestMapping("/users")
public class UserRestService {

    @Autowired
    SchoolUserRepository schoolUserRepository;

    @Autowired
    ManagerService managerService;

    @Autowired
    UserService userService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public SchoolUserDTO getUser(Principal principal) {
        SchoolUser user = schoolUserRepository.findByUsername(principal.getName());
        SchoolUserDTO schoolUserDTO = null;
        if (user instanceof Student)
            schoolUserDTO = new StudentDTO((Student) user);
        else if (user instanceof Teacher)
            schoolUserDTO = new TeacherDTO((Teacher) user);
        else if (user instanceof Manciple)
            schoolUserDTO = new MancipleDTO((Manciple) user);
        else if (user instanceof Manager)
            schoolUserDTO = new ManagerDTO((Manager) user);
        if (schoolUserDTO != null) {
            schoolUserDTO.setSchool(new SchoolDTO(user.getSchool()));
        }
        return schoolUserDTO;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/authority")
    public String getAuthority(Principal principal) {
        User user = schoolUserRepository.findByUsername(principal.getName());
        return user.getClass().getSimpleName();
    }

    @PreAuthorize("permitAll()")
    @RequestMapping(value = "/{username}/changePassword", method = RequestMethod.POST)
    public void changePassword(@PathVariable String username, @RequestParam String currentPassword, @RequestParam String newPassword, HttpServletResponse response) {
        try {
            userService.changePassword(username, currentPassword, newPassword);
        } catch (IllegalAccessException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('MANAGER') AND hasPermission(#username, 'SchoolUser', 'UPDATE')")
    @RequestMapping(value = "/{username}/resetPassword", method = RequestMethod.POST)
    public void resetPassword(@PathVariable String username){
        SchoolUser user = schoolUserRepository.findByUsername(username);
        String newPass = userService.resetPassword(username);
        userService.sendPasswordEmail(user.getEmail(), newPass);
    }

    @PreAuthorize("hasAuthority('MANAGER') AND hasPermission(#username, 'SchoolUser', 'UPDATE')")
    @RequestMapping(value = "/{username}/lock", method = RequestMethod.POST)
    public void lockUser(@PathVariable String username){
        User user = schoolUserRepository.findByUsername(username);
        if (! user.isLocked())
            userService.lockUser(username);
    }

    @PreAuthorize("hasAuthority('MANAGER') AND hasPermission(#username, 'SchoolUser', 'UPDATE')")
    @RequestMapping(value = "/{username}/unlock", method = RequestMethod.POST)
    public void unlockUser(@PathVariable String username){
        User user = schoolUserRepository.findByUsername(username);
        if (user.isLocked())
            userService.unlockUser(username);
    }
}
