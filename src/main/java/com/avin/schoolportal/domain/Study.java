package com.avin.schoolportal.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yubar on 11/20/2016.
 */

@Cacheable
@Entity(name = "Study")
@Table(name = "STUDIES")
public class Study implements Serializable{

    private long id;
    private String name;
    private double hours;
    private Course course;
    private List<Book> books = new ArrayList<>();

    public Study() {
    }

    public Study(String name) {
        this.name = name;
    }

    public Study(String name, double hours) {
        this.name = name;
        this.hours = hours;
    }

    public Study(String name, double hours, Course course) {
        this.name = name;
        this.hours = hours;
        this.course = course;
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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @OneToMany(mappedBy = "study")
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Study)) return false;

        Study study = (Study) o;

        if (getId() != study.getId()) return false;
        if (getId() > 0)
            return true;
        if (Double.compare(study.getHours(), getHours()) != 0) return false;
        if (getName() != null ? !getName().equals(study.getName()) : study.getName() != null) return false;
        if (getCourse() != null ? !getCourse().equals(study.getCourse()) : study.getCourse() != null) return false;
        return getBooks() != null ? getBooks().equals(study.getBooks()) : study.getBooks() == null;

    }

    @Override
    public int hashCode() {
        if (getId() > 0)
            return (int) getId();
        int result;
        long temp;
        result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        temp = Double.doubleToLongBits(getHours());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (getCourse() != null ? getCourse().hashCode() : 0);
        result = 31 * result + (getBooks() != null ? getBooks().hashCode() : 0);
        return result;
    }
}
