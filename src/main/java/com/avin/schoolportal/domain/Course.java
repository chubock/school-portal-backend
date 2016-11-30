package com.avin.schoolportal.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yubar on 10/20/2016.
 */

@Entity(name = "Course")
@Table(name = "COURSES")
public class Course implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Column(nullable = false)
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    private Course parent;
    @OneToMany(mappedBy = "parent")
    private List<Course> children = new ArrayList<>();
    @OneToMany(mappedBy = "course")
    private List<Book> books = new ArrayList<>();
    @OneToMany(mappedBy = "course")
    private List<Study> studies = new ArrayList<>();

    public Course() {
    }

    public Course(String title) {
        this.title = title;
    }

    public Course(String title, Course parent) {
        this.title = title;
        this.parent = parent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Course getParent() {
        return parent;
    }

    public void setParent(Course parent) {
        this.parent = parent;
    }

    public List<Course> getChildren() {
        return children;
    }

    public void setChildren(List<Course> children) {
        this.children = children;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Study> getStudies() {
        return studies;
    }

    public void setStudies(List<Study> studies) {
        this.studies = studies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;

        Course course = (Course) o;

        if (getId() != course.getId()) return false;
        if (getId() != 0)
            return true;
        if (getTitle() != null ? !getTitle().equals(course.getTitle()) : course.getTitle() != null) return false;
        return getParent() != null ? getParent().equals(course.getParent()) : course.getParent() == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getParent() != null ? getParent().hashCode() : 0);
        return result;
    }
}
