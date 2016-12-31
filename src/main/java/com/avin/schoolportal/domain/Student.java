package com.avin.schoolportal.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yubar on 11/20/2016.
 */

@Entity(name = "Student")
public class Student extends SchoolUser {

    private Parent parent;
    private Course course;
    private Classroom classroom;
    private int academicYear;
    private double lastYearGrade = 17.0;
    private List<Violation> violations = new ArrayList<>();

    public Student() {
    }

    public Student(String username, String password) {
        super(username, password);
    }

    @OneToOne(mappedBy = "student", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @ManyToOne(fetch = FetchType.LAZY)
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

    public double getLastYearGrade() {
        return lastYearGrade;
    }

    public void setLastYearGrade(double lastYearGrade) {
        this.lastYearGrade = lastYearGrade;
    }

    @ManyToMany(mappedBy = "students")
    public List<Violation> getViolations() {
        return violations;
    }

    public void setViolations(List<Violation> violations) {
        this.violations = violations;
    }

    @Override
    @Transient
    public String getUsernamePrefix() {
        return "3";
    }
}
