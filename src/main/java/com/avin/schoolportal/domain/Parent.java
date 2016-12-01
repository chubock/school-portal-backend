package com.avin.schoolportal.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Yubar on 11/25/2016.
 */

@Entity(name = "Parent")
public class Parent extends SchoolUser {

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    private Student student;

    public Parent() {
    }

    public Parent(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String getUsernamePrefix() {
        return "2";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parent)) return false;

        Parent parent = (Parent) o;

        if (getId() != parent.getId()) return false;
        if (getId() != 0)
            return true;
        if (getStudent() != null ? !getStudent().equals(parent.getStudent()) : parent.getStudent() != null)
            return false;
        return getSchool() != null ? getSchool().equals(parent.getSchool()) : parent.getSchool() == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getStudent() != null ? getStudent().hashCode() : 0);
        result = 31 * result + (getSchool() != null ? getSchool().hashCode() : 0);
        return result;
    }
}
