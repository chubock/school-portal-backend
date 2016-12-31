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

    private List<ClassTime> classTimes = new ArrayList<>();
    private List<Handout> handouts = new ArrayList<>();

    public Teacher() {
    }

    @OneToMany(mappedBy = "teacher")
    public List<ClassTime> getClassTimes() {
        return classTimes;
    }

    public void setClassTimes(List<ClassTime> classTimes) {
        this.classTimes = classTimes;
    }

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.REMOVE)
    public List<Handout> getHandouts() {
        return handouts;
    }

    public void setHandouts(List<Handout> handouts) {
        this.handouts = handouts;
    }

    @Override
    @Transient
    public String getUsernamePrefix() {
        return "1";
    }
}
