package com.avin.schoolportal.dto;

import com.avin.schoolportal.domain.Gender;
import com.avin.schoolportal.domain.SchoolUser;
import com.avin.schoolportal.domain.User;
import com.avin.schoolportal.validationgroups.EmployeeRegistration;
import com.avin.schoolportal.validationgroups.SchoolRegistration;
import com.avin.schoolportal.validationgroups.StudentRegistration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * Created by Yubar on 11/30/2016.
 */
public abstract class SchoolUserDTO extends UserDTO {

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

    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", groups = {
            SchoolRegistration.class,
            EmployeeRegistration.class,
            StudentRegistration.class
    })
    private String email;
    @Pattern(regexp = "09\\d{9}", groups = {
            SchoolRegistration.class,
            EmployeeRegistration.class,
            StudentRegistration.class
    })
    private String phoneNumber;
    private FileDTO pictureFile;
    private SchoolDTO school;

    public SchoolUserDTO() {
    }

    public SchoolUserDTO(SchoolUser user) {
        super(user);
        if (user != null) {
            setFirstName(user.getFirstName());
            setLastName(user.getLastName());
            setFatherName(user.getFatherName());
            setNationalId(user.getNationalId());
            setBirthday(user.getBirthday());
            setGender(user.getGender());
            setEmail(user.getEmail());
            setPhoneNumber(user.getPhoneNumber());
        }
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

    public FileDTO getPictureFile() {
        return pictureFile;
    }

    public void setPictureFile(FileDTO pictureFile) {
        this.pictureFile = pictureFile;
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
        user.setFirstName(getFirstName());
        user.setLastName(getLastName());
        user.setFatherName(getFatherName());
        user.setNationalId(getNationalId());
        user.setGender(getGender());
        user.setBirthday(getBirthday());
        user.setEmail(getEmail());
        user.setPhoneNumber(getPhoneNumber());
        if (getPictureFile() != null)
            user.setPictureFile(getPictureFile().convert());
        if (getSchool() != null)
            user.setSchool(getSchool().convert());
        return user;
    }
}
