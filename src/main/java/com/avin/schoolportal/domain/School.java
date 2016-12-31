package com.avin.schoolportal.domain;

import org.hibernate.annotations.Generated;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yubar on 10/21/2016.
 */

@Cacheable
@Entity(name = "School")
@Table(name = "SCHOOLS")
public class School implements Serializable {

    private long id;
    private String code;
    private String name;
    private List<Course> courses = new ArrayList<>();
    private List<Teacher> teachers = new ArrayList<>();
    private List<Manciple> manciples = new ArrayList<>();
    private List<Student> students = new ArrayList<>();
    @OneToOne
    private Manager manager;

    public School() {
    }

    public School(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @NotNull
    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @OneToMany(mappedBy = "school", targetEntity = SchoolUser.class)
    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    @OneToMany(mappedBy = "school", targetEntity = SchoolUser.class)
    public List<Manciple> getManciples() {
        return manciples;
    }

    public void setManciples(List<Manciple> manciples) {
        this.manciples = manciples;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    @OneToMany(mappedBy = "school", targetEntity = SchoolUser.class)
    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof School)) return false;

        School school = (School) o;

        if (getId() != school.getId()) return false;
        if (getId() > 0)
            return true;
        if (getCode() != null ? !getCode().equals(school.getCode()) : school.getCode() != null) return false;
        if (getName() != null ? !getName().equals(school.getName()) : school.getName() != null) return false;
        if (getCourses() != null ? !getCourses().equals(school.getCourses()) : school.getCourses() != null)
            return false;
        if (getTeachers() != null ? !getTeachers().equals(school.getTeachers()) : school.getTeachers() != null)
            return false;
        if (getManciples() != null ? !getManciples().equals(school.getManciples()) : school.getManciples() != null)
            return false;
        if (getStudents() != null ? !getStudents().equals(school.getStudents()) : school.getStudents() != null)
            return false;
        return getManager() != null ? getManager().equals(school.getManager()) : school.getManager() == null;

    }

    @Override
    public int hashCode() {
        if (getId() > 0)
            return (int) getId();
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getCode() != null ? getCode().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getCourses() != null ? getCourses().hashCode() : 0);
        result = 31 * result + (getTeachers() != null ? getTeachers().hashCode() : 0);
        result = 31 * result + (getManciples() != null ? getManciples().hashCode() : 0);
        result = 31 * result + (getStudents() != null ? getStudents().hashCode() : 0);
        result = 31 * result + (getManager() != null ? getManager().hashCode() : 0);
        return result;
    }
}
