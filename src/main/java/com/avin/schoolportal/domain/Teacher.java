package com.avin.schoolportal.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yubar on 10/21/2016.
 */

@Entity(name = "Teacher")
public class Teacher extends Employee{

    @OneToMany(mappedBy = "teacher")
    private List<ClassTime> classTimes = new ArrayList<>();

    public Teacher() {
    }

    public List<ClassTime> getClassTimes() {
        return classTimes;
    }

    public void setClassTimes(List<ClassTime> classTimes) {
        this.classTimes = classTimes;
    }

    @Override
    public String getUsernamePrefix() {
        return "1";
    }
}
