package com.avin.schoolportal.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yubar on 11/20/2016.
 */

@Entity(name = "Classroom")
@Table(name = "CLASSROOMS")
public class Classroom implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int academicYear;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private School school;
    @OneToMany
    private List<Student> students = new ArrayList<>();
    @OneToMany(mappedBy = "classroom")
    private List<ClassTime> classTimes = new ArrayList<>();

    public Classroom() {
    }

    public Classroom(String name) {
        this.name = name;
    }

    public Classroom(String name, int academicYear) {
        this.name = name;
        this.academicYear = academicYear;
    }

    public Classroom(String name, int academicYear, Course course) {
        this.name = name;
        this.academicYear = academicYear;
        this.course = course;
    }

    public Classroom(String name, int academicYear, Course course, School school) {
        this.name = name;
        this.academicYear = academicYear;
        this.course = course;
        this.school = school;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(int academicYear) {
        this.academicYear = academicYear;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<ClassTime> getClassTimes() {
        return classTimes;
    }

    public void setClassTimes(List<ClassTime> classTimes) {
        this.classTimes = classTimes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Classroom)) return false;

        Classroom classroom = (Classroom) o;

        if (getId() != classroom.getId()) return false;
        if (getId() != 0)
            return true;
        if (getAcademicYear() != classroom.getAcademicYear()) return false;
        if (getName() != null ? !getName().equals(classroom.getName()) : classroom.getName() != null) return false;
        if (getCourse() != null ? !getCourse().equals(classroom.getCourse()) : classroom.getCourse() != null)
            return false;
        return getSchool() != null ? getSchool().equals(classroom.getSchool()) : classroom.getSchool() == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + getAcademicYear();
        result = 31 * result + (getCourse() != null ? getCourse().hashCode() : 0);
        result = 31 * result + (getSchool() != null ? getSchool().hashCode() : 0);
        return result;
    }
}
