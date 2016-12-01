package com.avin.schoolportal.dto;

import com.avin.schoolportal.domain.Parent;
import com.avin.schoolportal.validationgroups.StudentRegistration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by Yubar on 11/25/2016.
 */
public class ParentDTO extends SchoolUserDTO {

    private StudentDTO student;

    public ParentDTO() {
    }

    public ParentDTO(Parent parent) {
        super(parent);
    }

    public StudentDTO getStudent() {
        return student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    @Override
    public Parent convert() {
        return convert(new Parent());
    }

    protected Parent convert(Parent parent) {
        super.convert(parent);
        if (getStudent() != null)
            parent.setStudent(getStudent().convert());
        return parent;
    }
}
