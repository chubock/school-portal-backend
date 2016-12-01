package com.avin.schoolportal.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Yubar on 11/20/2016.
 */

@Entity(name = "ClassTime")
@Table(name = "CLASS_TIMES")
public class ClassTime implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classroom classroom;
    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Teacher teacher;
    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Study study;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Study getStudy() {
        return study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassTime)) return false;

        ClassTime classTime = (ClassTime) o;

        if (getId() != classTime.getId()) return false;
        if (getId() != 0)
            return true;
        if (getClassroom() != null ? !getClassroom().equals(classTime.getClassroom()) : classTime.getClassroom() != null)
            return false;
        if (getTeacher() != null ? !getTeacher().equals(classTime.getTeacher()) : classTime.getTeacher() != null)
            return false;
        return getStudy() != null ? getStudy().equals(classTime.getStudy()) : classTime.getStudy() == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getClassroom() != null ? getClassroom().hashCode() : 0);
        result = 31 * result + (getTeacher() != null ? getTeacher().hashCode() : 0);
        result = 31 * result + (getStudy() != null ? getStudy().hashCode() : 0);
        return result;
    }
}
