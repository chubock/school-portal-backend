package com.avin.schoolportal.domain;

import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yubar on 10/21/2016.
 */

@Entity(name = "User")
@Table(name = "USERS")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String username;
    @NotNull
    private String password;
    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Person person;
    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")
    @Column(nullable = false)
    private String email;
    @Pattern(regexp = "09\\d{9}")
    private String phoneNumber;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private School school;
    @Enumerated(EnumType.STRING)
    @ElementCollection
    @CollectionTable(name = "USER_ROLES",joinColumns = @JoinColumn(name = "USERNAME"))
    @Column(name = "ROLE")
    private List<Role> roles = new ArrayList<>();
    @Column(nullable = false)
    private boolean enabled = true;
    @Column(nullable = false)
    private boolean expired = false;
    @Column(nullable = false)
    private boolean locked = false;
    @Column(nullable = false)
    private boolean passwordExpired = false;

    private String locale = "fa";

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
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

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (getId() != user.getId()) return false;
        if (getId() != 0)
            return true;
        if (isEnabled() != user.isEnabled()) return false;
        if (isExpired() != user.isExpired()) return false;
        if (isLocked() != user.isLocked()) return false;
        if (isPasswordExpired() != user.isPasswordExpired()) return false;
        if (getUsername() != null ? !getUsername().equals(user.getUsername()) : user.getUsername() != null)
            return false;
        if (getPassword() != null ? !getPassword().equals(user.getPassword()) : user.getPassword() != null)
            return false;
        if (getPerson() != null ? !getPerson().equals(user.getPerson()) : user.getPerson() != null) return false;
        if (getEmail() != null ? !getEmail().equals(user.getEmail()) : user.getEmail() != null) return false;
        if (getPhoneNumber() != null ? !getPhoneNumber().equals(user.getPhoneNumber()) : user.getPhoneNumber() != null)
            return false;
        if (getSchool() != null ? !getSchool().equals(user.getSchool()) : user.getSchool() != null) return false;
        if (getRoles() != null ? !getRoles().equals(user.getRoles()) : user.getRoles() != null) return false;
        return getLocale() != null ? getLocale().equals(user.getLocale()) : user.getLocale() == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getPerson() != null ? getPerson().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
        result = 31 * result + (getSchool() != null ? getSchool().hashCode() : 0);
        result = 31 * result + (getRoles() != null ? getRoles().hashCode() : 0);
        result = 31 * result + (isEnabled() ? 1 : 0);
        result = 31 * result + (isExpired() ? 1 : 0);
        result = 31 * result + (isLocked() ? 1 : 0);
        result = 31 * result + (isPasswordExpired() ? 1 : 0);
        result = 31 * result + (getLocale() != null ? getLocale().hashCode() : 0);
        return result;
    }
}
