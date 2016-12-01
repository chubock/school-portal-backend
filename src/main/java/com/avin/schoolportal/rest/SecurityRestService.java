package com.avin.schoolportal.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by Yubar on 10/28/2016.
 */

@RestController
public class SecurityRestService {

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/login")
    public Principal getUser(Principal principal) {
        return principal;
    }
}
