package com.avin.schoolportal.rest;

import com.avin.schoolportal.domain.Role;
import com.avin.schoolportal.domain.User;
import com.avin.schoolportal.dto.SchoolDTO;
import com.avin.schoolportal.dto.UserDTO;
import com.avin.schoolportal.repository.EmployeeRepository;
import com.avin.schoolportal.repository.UserRepository;
import com.avin.schoolportal.service.ManagerService;
import com.avin.schoolportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

/**
 * Created by Yubar on 10/22/2016.
 */

@RestController
@RequestMapping("/users")
public class UserRestService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ManagerService managerService;

    @Autowired
    UserService userService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public UserDTO getUser(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        UserDTO userDTO = new UserDTO(user);
        userDTO.getRoles().addAll(user.getRoles());
        userDTO.setSchool(new SchoolDTO(user.getSchool()));
        return userDTO;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/authorities")
    public List<Role> getAuthorities(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        return user.getRoles();
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

    @PreAuthorize("hasAuthority('MANAGER') AND hasPermission(#username, 'User', 'UPDATE')")
    @RequestMapping(value = "/{username}/resetPassword", method = RequestMethod.POST)
    public void resetPassword(@PathVariable String username){
        User user = userRepository.findByUsername(username);
        String newPass = userService.resetPassword(username);
        userService.sendPasswordEmail(user.getEmail(), newPass);
    }

    @PreAuthorize("hasAuthority('MANAGER') AND hasPermission(#username, 'User', 'UPDATE')")
    @RequestMapping(value = "/{username}/lock", method = RequestMethod.POST)
    public void lockUser(@PathVariable String username){
        User user = userRepository.findByUsername(username);
        if (! user.isLocked())
            userService.lockUser(username);
    }

    @PreAuthorize("hasAuthority('MANAGER') AND hasPermission(#username, 'User', 'UPDATE')")
    @RequestMapping(value = "/{username}/unlock", method = RequestMethod.POST)
    public void unlockUser(@PathVariable String username){
        User user = userRepository.findByUsername(username);
        if (user.isLocked())
            userService.unlockUser(username);
    }
}
