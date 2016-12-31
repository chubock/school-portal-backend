package com.avin.schoolportal.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Yubar on 11/19/2016.
 */

@Cacheable
@Entity(name = "Book")
@Table(name = "BOOKS")
public class Book implements Serializable {

    private long id;
    private String name;
    private String code;
    private Course course;
    private Study study;
    private File cover;

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

    @Column(unique = true)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Study getStudy() {
        return study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    @OneToOne(cascade = CascadeType.REMOVE)
    public File getCover() {
        return cover;
    }

    public void setCover(File cover) {
        this.cover = cover;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        if (getId() != book.getId()) return false;
        if (getId() > 0)
            return true;
        if (getName() != null ? !getName().equals(book.getName()) : book.getName() != null) return false;
        if (getCode() != null ? !getCode().equals(book.getCode()) : book.getCode() != null) return false;
        if (getCourse() != null ? !getCourse().equals(book.getCourse()) : book.getCourse() != null) return false;
        return getStudy() != null ? getStudy().equals(book.getStudy()) : book.getStudy() == null;

    }

    @Override
    public int hashCode() {
        if (getId() > 0)
            return (int) getId();
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getCode() != null ? getCode().hashCode() : 0);
        result = 31 * result + (getCourse() != null ? getCourse().hashCode() : 0);
        result = 31 * result + (getStudy() != null ? getStudy().hashCode() : 0);
        return result;
    }
}
