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

    private long id;
    private String name;
    private int academicYear;
    private Course course;
    private School school;
    private List<Student> students = new ArrayList<>();
    private List<ClassTime> classTimes = new ArrayList<>();
    List<Exam> exams = new ArrayList<>();

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotNull
    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public int getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(int academicYear) {
        this.academicYear = academicYear;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    @OneToMany(mappedBy = "classroom")
    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @OneToMany(mappedBy = "classroom")
    public List<ClassTime> getClassTimes() {
        return classTimes;
    }

    public void setClassTimes(List<ClassTime> classTimes) {
        this.classTimes = classTimes;
    }

    @ManyToMany(mappedBy = "classrooms")
    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Classroom)) return false;

        Classroom classroom = (Classroom) o;

        if (getId() != classroom.getId()) return false;
        if (getId() > 0)
            return true;
        if (getAcademicYear() != classroom.getAcademicYear()) return false;
        if (getName() != null ? !getName().equals(classroom.getName()) : classroom.getName() != null) return false;
        if (getCourse() != null ? !getCourse().equals(classroom.getCourse()) : classroom.getCourse() != null)
            return false;
        if (getSchool() != null ? !getSchool().equals(classroom.getSchool()) : classroom.getSchool() != null)
            return false;
        if (getStudents() != null ? !getStudents().equals(classroom.getStudents()) : classroom.getStudents() != null)
            return false;
        if (getClassTimes() != null ? !getClassTimes().equals(classroom.getClassTimes()) : classroom.getClassTimes() != null)
            return false;
        return getExams() != null ? getExams().equals(classroom.getExams()) : classroom.getExams() == null;

    }

    @Override
    public int hashCode() {
        if (getId() > 0)
            return (int) getId();
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + getAcademicYear();
        result = 31 * result + (getCourse() != null ? getCourse().hashCode() : 0);
        result = 31 * result + (getSchool() != null ? getSchool().hashCode() : 0);
        result = 31 * result + (getStudents() != null ? getStudents().hashCode() : 0);
        result = 31 * result + (getClassTimes() != null ? getClassTimes().hashCode() : 0);
        result = 31 * result + (getExams() != null ? getExams().hashCode() : 0);
        return result;
    }
}
