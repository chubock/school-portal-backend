package com.avin.schoolportal.security;

import com.avin.schoolportal.domain.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Yubar on 11/18/2016.
 */
public class ApplicationUserDetails implements UserDetails {

    User user;

    public ApplicationUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (user instanceof Manager)
            authorities.add(new SimpleGrantedAuthority("MANAGER"));
        else if (user instanceof Manciple)
            authorities.add(new SimpleGrantedAuthority("MANCIPLE"));
        else if (user instanceof Teacher)
            authorities.add(new SimpleGrantedAuthority("TEACHER"));
        else if (user instanceof Parent)
            authorities.add(new SimpleGrantedAuthority("PARENT"));
        else if (user instanceof Student)
            authorities.add(new SimpleGrantedAuthority("STUDENT"));
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !user.isExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !user.isPasswordExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }


}
