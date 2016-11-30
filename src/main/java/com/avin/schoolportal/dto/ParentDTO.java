package com.avin.schoolportal.dto;

import com.avin.schoolportal.domain.Parent;
import com.avin.schoolportal.validationgroups.StudentRegistration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by Yubar on 11/25/2016.
 */
public class ParentDTO implements AbstractDTO<Parent> {

    private long id;
    @NotNull(groups = {
            StudentRegistration.class
    })
    @Valid
    private UserDTO user;
    private StudentDTO student;
    private SchoolDTO school;

    public ParentDTO() {
    }

    public ParentDTO(Parent parent) {
        if (parent != null) {
            this.id = parent.getId();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public StudentDTO getStudent() {
        return student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    public SchoolDTO getSchool() {
        return school;
    }

    public void setSchool(SchoolDTO school) {
        this.school = school;
    }

    @Override
    public Parent convert() {
        Parent parent = new Parent();
        parent.setId(getId());
        if (getUser() != null)
            parent.setUser(getUser().convert());
        if (getStudent() != null)
            parent.setStudent(getStudent().convert());
        if (getSchool() != null)
            parent.setSchool(getSchool().convert());
        return parent;
    }
}
