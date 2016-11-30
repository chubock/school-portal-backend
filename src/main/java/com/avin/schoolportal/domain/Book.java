package com.avin.schoolportal.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Yubar on 11/19/2016.
 */

@Entity(name = "Book")
@Table(name = "BOOKS")
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Column(nullable = false)
    private String name;
    @Column(unique = true)
    private String code;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Course course;
    @ManyToOne(fetch = FetchType.LAZY)
    private Study study;

    public Book() {
    }

    public Book(String name) {
        this.name = name;
    }

    public Book(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Book(String name, String code, Course course) {
        this.name = name;
        this.code = code;
        this.course = course;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        if (getId() != book.getId()) return false;
        if (getId() != 0)
            return true;
        if (getName() != null ? !getName().equals(book.getName()) : book.getName() != null) return false;
        if (getCode() != null ? !getCode().equals(book.getCode()) : book.getCode() != null) return false;
        if (getCourse() != null ? !getCourse().equals(book.getCourse()) : book.getCourse() != null) return false;
        return getStudy() != null ? getStudy().equals(book.getStudy()) : book.getStudy() == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getCode() != null ? getCode().hashCode() : 0);
        result = 31 * result + (getCourse() != null ? getCourse().hashCode() : 0);
        result = 31 * result + (getStudy() != null ? getStudy().hashCode() : 0);
        return result;
    }
}
