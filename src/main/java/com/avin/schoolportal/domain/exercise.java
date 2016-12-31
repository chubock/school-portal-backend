package com.avin.schoolportal.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Yubar on 12/9/2016.
 */

@Entity(name = "Exercise")
public class Exercise implements Serializable {

    private long id;
    private String title;
    private Teacher teacher;
    private Course course;
    private Study study;
    private int academicYear;
    private List<Classroom> classrooms = new ArrayList<>();
    private Date dueDate;
    private File questions;
    private File answers;
    private List<Student> wrongdoers = new ArrayList<>();
    private School school;

    public Exercise() {
    }

    public Exercise(String title, Date dueDate) {
        this.title = title;
        this.dueDate = dueDate;
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
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Study getStudy() {
        return study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    @Column(nullable = false)
    public int getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(int academicYear) {
        this.academicYear = academicYear;
    }

    @ManyToMany
    public List<Classroom> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(List<Classroom> classrooms) {
        this.classrooms = classrooms;
    }

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public File getQuestions() {
        return questions;
    }

    public void setQuestions(File questions) {
        this.questions = questions;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public File getAnswers() {
        return answers;
    }

    public void setAnswers(File answers) {
        this.answers = answers;
    }


    @ManyToMany
    public List<Student> getWrongdoers() {
        return wrongdoers;
    }

    public void setWrongdoers(List<Student> wrongdoers) {
        this.wrongdoers = wrongdoers;
    }

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Exercise)) return false;

        Exercise exercise = (Exercise) o;

        if (getId() != exercise.getId()) return false;
        if (getId() > 0)
            return true;
        if (getAcademicYear() != exercise.getAcademicYear()) return false;
        if (getTitle() != null ? !getTitle().equals(exercise.getTitle()) : exercise.getTitle() != null) return false;
        if (getCourse() != null ? !getCourse().equals(exercise.getCourse()) : exercise.getCourse() != null)
            return false;
        if (getStudy() != null ? !getStudy().equals(exercise.getStudy()) : exercise.getStudy() != null) return false;
        if (getClassrooms() != null ? !getClassrooms().equals(exercise.getClassrooms()) : exercise.getClassrooms() != null)
            return false;
        if (getDueDate() != null ? !getDueDate().equals(exercise.getDueDate()) : exercise.getDueDate() != null)
            return false;
        return getWrongdoers() != null ? getWrongdoers().equals(exercise.getWrongdoers()) : exercise.getWrongdoers() == null;

    }

    @Override
    public int hashCode() {
        if (getId() > 0)
            return (int) getId();
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getCourse() != null ? getCourse().hashCode() : 0);
        result = 31 * result + (getStudy() != null ? getStudy().hashCode() : 0);
        result = 31 * result + getAcademicYear();
        result = 31 * result + (getClassrooms() != null ? getClassrooms().hashCode() : 0);
        result = 31 * result + (getDueDate() != null ? getDueDate().hashCode() : 0);
        result = 31 * result + (getWrongdoers() != null ? getWrongdoers().hashCode() : 0);
        return result;
    }
}
