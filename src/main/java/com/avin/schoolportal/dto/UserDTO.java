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
public class UserDTO implements AbstractDTO<User> {

    private long id;
    @NotNull(groups = SchoolRegistration.class)
    @Size(min = 5, groups = SchoolRegistration.class)
    private String username;
    @NotNull(groups = SchoolRegistration.class)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", groups = SchoolRegistration.class)
    private String password;
    @NotNull(groups = {
            SchoolRegistration.class,
            EmployeeRegistration.class,
            StudentRegistration.class
    })
    @Valid
    private PersonDTO person;
    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", groups = {
            SchoolRegistration.class,
            EmployeeRegistration.class,
            StudentRegistration.class
    })
    private String email;
    @Pattern(regexp = "09\\d{9}")
    private String phoneNumber;
    private SchoolDTO school;
    private List<Role> roles = new ArrayList<>();
    private boolean enabled;
    private boolean expired;
    private boolean locked;
    private boolean passwordExpired;
    private String locale;

    public UserDTO() {
    }

    public UserDTO(User user) {
        if (user != null) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.phoneNumber = user.getPhoneNumber();
            this.enabled = user.isEnabled();
            this.locale = user.getLocale();
            this.expired = user.isExpired();
            this.locked = user.isLocked();
            this.passwordExpired = user.isPasswordExpired();
            this.person = new PersonDTO(user.getPerson());
            this.school = new SchoolDTO(user.getSchool());
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

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public SchoolDTO getSchool() {
        return school;
    }

    public void setSchool(SchoolDTO school) {
        this.school = school;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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
    public User convert() {
        User user = new User(getUsername(), getPassword());
        user.setId(getId());
        user.setEmail(getEmail());
        user.setRoles(getRoles());
        user.setLocale(getLocale());
        if (getPerson() != null)
            user.setPerson(getPerson().convert());
        if (getSchool() != null)
            user.setSchool(getSchool().convert());
        return user;
    }
}
