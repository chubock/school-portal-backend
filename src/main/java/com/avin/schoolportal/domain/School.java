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

@Entity(name = "School")
@Table(name = "SCHOOLS")
public class School implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String code;
    @NotNull
    @Column(nullable = false)
    private String name;
    @ManyToMany
    private List<Course> courses = new ArrayList<>();
    @Size(min = 1, max = 1)
    @OneToMany(mappedBy = "school")
    private List<Employee> employees = new ArrayList<>();
    @OneToMany(mappedBy = "school")
    private List<Student> students = new ArrayList<>();

    public School() {
    }

    public School(String name) {
        this.name = name;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

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
        if (getId() != 0)
            return true;
        return getName() != null ? getName().equals(school.getName()) : school.getName() == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }
}
