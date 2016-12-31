package com.avin.schoolportal.domain;

import javax.annotation.Generated;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Yubar on 12/3/2016.
 */

@Entity(name = "Violation")
public class Violation implements Serializable {

    private long id;
    private ViolationType type;
    private List<Student> students = new ArrayList<>();
    private Manciple manciple;
    private Date date = new Date();
    private String comment;
    private School school;

    public Violation() {
    }

    public Violation(ViolationType type) {
        this.type = type;
    }

    public Violation(ViolationType type, Manciple manciple, String comment) {
        this.type = type;
        this.manciple = manciple;
        this.comment = comment;
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
    @Enumerated
    @Column(nullable = false)
    public ViolationType getType() {
        return type;
    }

    public void setType(ViolationType type) {
        this.type = type;
    }

    @NotNull
    @Size(min = 1)
    @ManyToMany
    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @NotNull
    @ManyToOne(optional = false)
    public Manciple getManciple() {
        return manciple;
    }

    public void setManciple(Manciple manciple) {
        this.manciple = manciple;
    }

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(nullable = true)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(length = 3000)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

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
        if (!(o instanceof Violation)) return false;

        Violation violation = (Violation) o;

        if (getId() != violation.getId()) return false;
        if (getId() > 0)
            return true;
        if (getType() != violation.getType()) return false;
        if (getStudents() != null ? !getStudents().equals(violation.getStudents()) : violation.getStudents() != null)
            return false;
        if (getManciple() != null ? !getManciple().equals(violation.getManciple()) : violation.getManciple() != null)
            return false;
        if (getDate() != null ? !getDate().equals(violation.getDate()) : violation.getDate() != null) return false;
        if (getComment() != null ? !getComment().equals(violation.getComment()) : violation.getComment() != null)
            return false;
        return getSchool() != null ? getSchool().equals(violation.getSchool()) : violation.getSchool() == null;

    }

    @Override
    public int hashCode() {
        if (getId() > 0)
            return (int) getId();
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + (getStudents() != null ? getStudents().hashCode() : 0);
        result = 31 * result + (getManciple() != null ? getManciple().hashCode() : 0);
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        result = 31 * result + (getComment() != null ? getComment().hashCode() : 0);
        result = 31 * result + (getSchool() != null ? getSchool().hashCode() : 0);
        return result;
    }
}
