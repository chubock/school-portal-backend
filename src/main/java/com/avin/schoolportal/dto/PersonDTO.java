package com.avin.schoolportal.dto;

import com.avin.schoolportal.domain.Employee;
import com.avin.schoolportal.domain.Gender;
import com.avin.schoolportal.domain.Person;
import com.avin.schoolportal.validationgroups.EmployeeRegistration;
import com.avin.schoolportal.validationgroups.SchoolRegistration;
import com.avin.schoolportal.validationgroups.StudentRegistration;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * Created by Yubar on 10/23/2016.
 */
public class PersonDTO implements AbstractDTO<Person> {
    private long id;
    @NotNull(groups = {
            SchoolRegistration.class,
            EmployeeRegistration.class,
            StudentRegistration.class
    })
    private String firstName;
    @NotNull(groups = {
            SchoolRegistration.class,
            EmployeeRegistration.class,
            StudentRegistration.class
    })
    private String lastName;
    @NotNull(groups = {
            StudentRegistration.class
    })
    private String fatherName;
    @NotNull(groups = {
            SchoolRegistration.class,
            EmployeeRegistration.class,
            StudentRegistration.class
    })
    @Pattern(regexp = "\\d{10}", groups = {
            SchoolRegistration.class,
            EmployeeRegistration.class,
            StudentRegistration.class
    })
    private String nationalId;
    @NotNull(groups = {
            StudentRegistration.class
    })
    private Date birthday;
    private Gender gender = Gender.MALE;

    public PersonDTO() {
    }

    public PersonDTO(Person person) {
        if (person != null) {
            this.id = person.getId();
            this.firstName = person.getFirstName();
            this.lastName = person.getLastName();
            this.fatherName = person.getFatherName();
            this.nationalId = person.getNationalId();
            this.birthday = person.getBirthday();
            this.gender = person.getGender();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

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

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public Person convert() {
        Person person = new Person(getFirstName(), getLastName(), getBirthday());
        person.setId(getId());
        person.setFatherName(getFatherName());
        person.setNationalId(getNationalId());
        person.setGender(getGender());
        return person;
    }
}
