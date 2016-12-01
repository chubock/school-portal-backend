package com.avin.schoolportal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by Yubar on 11/30/2016.
 */

@Entity
public abstract class SchoolUser extends User {

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

    public SchoolUser() {
    }

    public SchoolUser(String username, String password) {
        super(username, password);
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

    public abstract String getUsernamePrefix();
}
