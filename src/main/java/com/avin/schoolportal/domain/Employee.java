package com.avin.schoolportal.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yubar on 10/21/2016.
 */

@Entity(name = "Employee")
@Table (name = "EMPLOYEES")
public class Employee implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private School school;
    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.REMOVE})
    private User user;
    @OneToMany(mappedBy = "teacher")
    private List<ClassTime> classTimes = new ArrayList<>();

    public Employee() {
    }

    public Employee(School school) {
        this.school = school;
    }

    public Employee(School school, User user) {
        this.school = school;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        if (!(o instanceof Employee)) return false;

        Employee employee = (Employee) o;

        if (getId() != employee.getId()) return false;
        if (getId() != 0)
            return true;
        if (getSchool() != null ? !getSchool().equals(employee.getSchool()) : employee.getSchool() != null)
            return false;
        return getUser() != null ? getUser().equals(employee.getUser()) : employee.getUser() == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getSchool() != null ? getSchool().hashCode() : 0);
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        return result;
    }
}
