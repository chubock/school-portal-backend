package com.avin.schoolportal.dto;

import com.avin.schoolportal.domain.SchoolUser;
import com.avin.schoolportal.domain.User;
import com.avin.schoolportal.validationgroups.EmployeeRegistration;
import com.avin.schoolportal.validationgroups.SchoolRegistration;
import com.avin.schoolportal.validationgroups.StudentRegistration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by Yubar on 11/30/2016.
 */
public abstract class SchoolUserDTO extends UserDTO {

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
    @Pattern(regexp = "09\\d{9}", groups = {
            SchoolRegistration.class,
            EmployeeRegistration.class
    })
    private String phoneNumber;
    private SchoolDTO school;

    public SchoolUserDTO() {
    }

    public SchoolUserDTO(SchoolUser user) {
        super(user);
        if (user != null) {
            setEmail(user.getEmail());
            setPhoneNumber(user.getPhoneNumber());
            setPerson(new PersonDTO(user.getPerson()));
            setSchool(new SchoolDTO(user.getSchool()));
        }
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

    @Override
    public abstract SchoolUser convert();

    protected SchoolUser convert(SchoolUser user) {
        super.convert(user);
        user.setEmail(getEmail());
        user.setPhoneNumber(getPhoneNumber());
        if (getPerson() != null)
            user.setPerson(getPerson().convert());
        if (getSchool() != null)
            user.setSchool(getSchool().convert());
        return user;
    }
}
