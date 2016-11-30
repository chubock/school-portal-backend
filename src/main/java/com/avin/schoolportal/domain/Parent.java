package com.avin.schoolportal.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Yubar on 11/25/2016.
 */

@Entity(name = "Parent")
@Table(name = "PARENTS")
public class Parent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @OneToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    private User user;
    @NotNull
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Student student;
    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private School school;

    public Parent() {
    }

    public Parent(Student student) {
        this.student = student;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parent)) return false;

        Parent parent = (Parent) o;

        if (getId() != parent.getId()) return false;
        if (getId() != 0)
            return true;
        if (getUser() != null ? !getUser().equals(parent.getUser()) : parent.getUser() != null) return false;
        if (getStudent() != null ? !getStudent().equals(parent.getStudent()) : parent.getStudent() != null)
            return false;
        return getSchool() != null ? getSchool().equals(parent.getSchool()) : parent.getSchool() == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        result = 31 * result + (getStudent() != null ? getStudent().hashCode() : 0);
        result = 31 * result + (getSchool() != null ? getSchool().hashCode() : 0);
        return result;
    }
}
