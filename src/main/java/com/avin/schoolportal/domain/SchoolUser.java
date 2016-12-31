package com.avin.schoolportal.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * Created by Yubar on 11/30/2016.
 */

@Entity
public abstract class SchoolUser extends User {

    private String firstName;
    private String lastName;
    private String fatherName;
    private String nationalId;
    private Date birthday;
    private Gender gender = Gender.MALE;
    private String email;
    private String phoneNumber;
    private File pictureFile;
    private School school;

    public SchoolUser() {
    }

    public SchoolUser(String username, String password) {
        super(username, password);
    }

    @NotNull
    @Column(nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotNull
    @Column(nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    @NotNull
    @Pattern(regexp = "\\d{10}")
    @Column(nullable = false)
    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    @Temporal(TemporalType.DATE)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Enumerated
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")
    @Column(nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Pattern(regexp = "09\\d{9}")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @OneToOne(cascade = CascadeType.REMOVE)
    public File getPictureFile() {
        return pictureFile;
    }

    public void setPictureFile(File pictureFile) {
        this.pictureFile = pictureFile;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    @Transient
    public abstract String getUsernamePrefix();

    @Transient
    public Person getPerson() {
        Person person = new Person(getFirstName(), getLastName(), getBirthday());
        person.setGender(getGender());
        person.setNationalId(getNationalId());
        person.setFatherName(getFatherName());
        return person;
    }
}
