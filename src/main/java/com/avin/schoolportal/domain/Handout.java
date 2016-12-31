package com.avin.schoolportal.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Yubar on 12/30/2016.
 */

@Entity(name = "Handout")
public class Handout implements Serializable {

    private long id;
    private String title;
    private Course course;
    private Study study;
    private File file;
    private Teacher teacher;
    private School school;

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
    @ManyToOne(optional = false)
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @NotNull
    @ManyToOne(optional = false)
    public Study getStudy() {
        return study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    @OneToOne(cascade = CascadeType.REMOVE)
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @NotNull
    @ManyToOne(optional = false)
    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @NotNull
    @ManyToOne(optional = false)
    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Handout)) return false;

        Handout handout = (Handout) o;

        if (getId() != handout.getId()) return false;
        if (getId() > 0)
            return true;
        if (getTitle() != null ? !getTitle().equals(handout.getTitle()) : handout.getTitle() != null) return false;
        if (getCourse() != null ? !getCourse().equals(handout.getCourse()) : handout.getCourse() != null) return false;
        if (getStudy() != null ? !getStudy().equals(handout.getStudy()) : handout.getStudy() != null) return false;
        if (getTeacher() != null ? !getTeacher().equals(handout.getTeacher()) : handout.getTeacher() != null)
            return false;
        return getSchool() != null ? getSchool().equals(handout.getSchool()) : handout.getSchool() == null;

    }

    @Override
    public int hashCode() {
        if (getId() > 0)
            return (int) getId();
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getCourse() != null ? getCourse().hashCode() : 0);
        result = 31 * result + (getStudy() != null ? getStudy().hashCode() : 0);
        result = 31 * result + (getTeacher() != null ? getTeacher().hashCode() : 0);
        result = 31 * result + (getSchool() != null ? getSchool().hashCode() : 0);
        return result;
    }
}
