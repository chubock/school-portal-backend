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

@Entity(name = "Exam")
public class Exam implements Serializable {

    private long id;
    private String title;
    private ExamType type = ExamType.WRITTEN;
    private Course course;
    private Study study;
    private int academicYear;
    private List<Classroom> classrooms = new ArrayList<>();
    private Date dueDate = new Date();
    private File questions;
    private File answers;
    private List<ExamScore> scores = new ArrayList<>();
    private Teacher teacher;
    private School school;

    public Exam() {
    }

    public Exam(String title, Study study, Date dueDate) {
        this.title = title;
        this.study = study;
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
    @Enumerated
    @Column(nullable = false)
    public ExamType getType() {
        return type;
    }

    public void setType(ExamType type) {
        this.type = type;
    }

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
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

    @NotNull
    @ManyToMany(fetch = FetchType.LAZY)
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

    @OneToMany(mappedBy = "exam", cascade = CascadeType.REMOVE, orphanRemoval = true)
    public List<ExamScore> getScores() {
        return scores;
    }

    public void setScores(List<ExamScore> scores) {
        this.scores = scores;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
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
        if (!(o instanceof Exam)) return false;

        Exam exam = (Exam) o;

        if (getId() != exam.getId()) return false;
        if (getId() > 0)
            return true;
        if (getAcademicYear() != exam.getAcademicYear()) return false;
        if (getTitle() != null ? !getTitle().equals(exam.getTitle()) : exam.getTitle() != null) return false;
        if (getType() != exam.getType()) return false;
        if (getCourse() != null ? !getCourse().equals(exam.getCourse()) : exam.getCourse() != null) return false;
        if (getStudy() != null ? !getStudy().equals(exam.getStudy()) : exam.getStudy() != null) return false;
        if (getClassrooms() != null ? !getClassrooms().equals(exam.getClassrooms()) : exam.getClassrooms() != null)
            return false;
        if (getDueDate() != null ? !getDueDate().equals(exam.getDueDate()) : exam.getDueDate() != null) return false;
        if (getQuestions() != null ? !getQuestions().equals(exam.getQuestions()) : exam.getQuestions() != null)
            return false;
        if (getAnswers() != null ? !getAnswers().equals(exam.getAnswers()) : exam.getAnswers() != null) return false;
        if (getScores() != null ? !getScores().equals(exam.getScores()) : exam.getScores() != null) return false;
        if (getTeacher() != null ? !getTeacher().equals(exam.getTeacher()) : exam.getTeacher() != null) return false;
        return getSchool() != null ? getSchool().equals(exam.getSchool()) : exam.getSchool() == null;

    }

    @Override
    public int hashCode() {
        if (getId() > 0)
            return (int) getId();
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + (getCourse() != null ? getCourse().hashCode() : 0);
        result = 31 * result + (getStudy() != null ? getStudy().hashCode() : 0);
        result = 31 * result + getAcademicYear();
        result = 31 * result + (getClassrooms() != null ? getClassrooms().hashCode() : 0);
        result = 31 * result + (getDueDate() != null ? getDueDate().hashCode() : 0);
        result = 31 * result + (getQuestions() != null ? getQuestions().hashCode() : 0);
        result = 31 * result + (getAnswers() != null ? getAnswers().hashCode() : 0);
        result = 31 * result + (getScores() != null ? getScores().hashCode() : 0);
        result = 31 * result + (getTeacher() != null ? getTeacher().hashCode() : 0);
        result = 31 * result + (getSchool() != null ? getSchool().hashCode() : 0);
        return result;
    }
}
