package com.avin.schoolportal.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by Yubar on 11/20/2016.
 */

@Entity(name = "Student")
public class Student extends SchoolUser {

    @OneToOne(mappedBy = "student", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    private Parent parent;
    @NotNull
    @ManyToOne
    private Course course;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classroom classroom;
    private int academicYear;

    public Student() {
    }

    public Student(String username, String password) {
        super(username, password);
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public int getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(int academicYear) {
        this.academicYear = academicYear;
    }

    @Override
    public String getUsernamePrefix() {
        return "3";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;

        Student student = (Student) o;

        if (getId() != student.getId()) return false;
        if (getId() != 0)
            return true;
        if (getId() != student.getId()) return false;
        if (getParent() != null ? !getParent().equals(student.getParent()) : student.getParent() != null)
            return false;
        if (getClassroom() != null ? !getClassroom().equals(student.getClassroom()) : student.getClassroom() != null)
            return false;
        return getSchool() != null ? getSchool().equals(student.getSchool()) : student.getSchool() == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getParent() != null ? getParent().hashCode() : 0);
        result = 31 * result + (getClassroom() != null ? getClassroom().hashCode() : 0);
        result = 31 * result + (getSchool() != null ? getSchool().hashCode() : 0);
        return result;
    }
}
