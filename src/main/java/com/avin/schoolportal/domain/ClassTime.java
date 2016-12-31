package com.avin.schoolportal.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yubar on 11/20/2016.
 */

@Entity(name = "ClassTime")
@Table(name = "CLASS_TIMES")
public class ClassTime implements Serializable {

    private long id;
    private Classroom classroom;
    private Teacher teacher;
    private Study study;
    private WeekDay weekDay;
    private double from;
    private double to;

    public ClassTime() {
    }

    public ClassTime(Classroom classroom) {
        this.classroom = classroom;
    }

    public ClassTime(Classroom classroom, Teacher teacher) {
        this.classroom = classroom;
        this.teacher = teacher;
    }

    public ClassTime(Classroom classroom, Teacher teacher, Study study) {
        this.classroom = classroom;
        this.teacher = teacher;
        this.study = study;
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
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    public Study getStudy() {
        return study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    @Enumerated
    @Column(nullable = false)
    public WeekDay getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(WeekDay weekDay) {
        this.weekDay = weekDay;
    }

    @Column(name = "START_FROM")
    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    @Column(name = "END_TO")
    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassTime)) return false;

        ClassTime classTime = (ClassTime) o;

        if (getId() != classTime.getId()) return false;
        if (getId() > 0)
            return true;
        if (Double.compare(classTime.getFrom(), getFrom()) != 0) return false;
        if (Double.compare(classTime.getTo(), getTo()) != 0) return false;
        if (getClassroom() != null ? !getClassroom().equals(classTime.getClassroom()) : classTime.getClassroom() != null)
            return false;
        if (getTeacher() != null ? !getTeacher().equals(classTime.getTeacher()) : classTime.getTeacher() != null)
            return false;
        if (getStudy() != null ? !getStudy().equals(classTime.getStudy()) : classTime.getStudy() != null) return false;
        return getWeekDay() == classTime.getWeekDay();

    }

    @Override
    public int hashCode() {
        if (getId() > 0)
            return (int) getId();
        int result;
        long temp;
        result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getClassroom() != null ? getClassroom().hashCode() : 0);
        result = 31 * result + (getTeacher() != null ? getTeacher().hashCode() : 0);
        result = 31 * result + (getStudy() != null ? getStudy().hashCode() : 0);
        result = 31 * result + (getWeekDay() != null ? getWeekDay().hashCode() : 0);
        temp = Double.doubleToLongBits(getFrom());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getTo());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
