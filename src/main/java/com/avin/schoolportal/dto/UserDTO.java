package com.avin.schoolportal.dto;

import com.avin.schoolportal.domain.Role;
import com.avin.schoolportal.domain.Student;
import com.avin.schoolportal.domain.User;
import com.avin.schoolportal.validationgroups.EmployeeRegistration;
import com.avin.schoolportal.validationgroups.SchoolRegistration;
import com.avin.schoolportal.validationgroups.StudentRegistration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yubar on 10/23/2016.
 */
public abstract class UserDTO implements AbstractDTO<User> {

    private long id;
    @NotNull(groups = SchoolRegistration.class)
    @Size(min = 5, groups = SchoolRegistration.class)
    private String username;
    @NotNull(groups = SchoolRegistration.class)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", groups = SchoolRegistration.class)
    private String password;
    private boolean enabled = true;
    private boolean expired;
    private boolean locked;
    private boolean passwordExpired;
    private String locale;

    public UserDTO() {
    }

    public UserDTO(User user) {
        if (user != null) {
            setId(user.getId());
            setUsername(user.getUsername());
            setEnabled(user.isEnabled());
            setLocale(user.getLocale());
            setLocked(user.isLocked());
            setPasswordExpired(user.isPasswordExpired());
            setExpired(user.isExpired());
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isPasswordExpired() {
        return passwordExpired;
    }

    public void setPasswordExpired(boolean passwordExpired) {
        this.passwordExpired = passwordExpired;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public abstract User convert();

    protected User convert(User user) {
        user.setUsername(getUsername());
        user.setPassword(getPassword());
        user.setId(getId());
        user.setLocale(getLocale());
        user.setLocked(isLocked());
        user.setExpired(isExpired());
        user.setPasswordExpired(isPasswordExpired());
        user.setEnabled(isEnabled());
        return user;
    }

}
