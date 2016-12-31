package com.avin.schoolportal.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yubar on 10/20/2016.
 */

@Cacheable
@Entity(name = "Course")
@Table(name = "COURSES")
public class Course implements Serializable {

    private long id;
    private String title;
    private Course parent;
    private List<Course> children = new ArrayList<>();
    private List<Book> books = new ArrayList<>();
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
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Course getParent() {
        return parent;
    }

    public void setParent(Course parent) {
        this.parent = parent;
    }

    @OneToMany(mappedBy = "parent")
    public List<Course> getChildren() {
        return children;
    }

    public void setChildren(List<Course> children) {
        this.children = children;
    }

    @OneToMany(mappedBy = "course")
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @OneToMany(mappedBy = "course")
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
        if (getId() > 0)
            return true;
        if (getTitle() != null ? !getTitle().equals(course.getTitle()) : course.getTitle() != null) return false;
        if (getParent() != null ? !getParent().equals(course.getParent()) : course.getParent() != null) return false;
        if (getChildren() != null ? !getChildren().equals(course.getChildren()) : course.getChildren() != null)
            return false;
        if (getBooks() != null ? !getBooks().equals(course.getBooks()) : course.getBooks() != null) return false;
        return getStudies() != null ? getStudies().equals(course.getStudies()) : course.getStudies() == null;

    }

    @Override
    public int hashCode() {
        if (getId() > 0)
            return (int) getId();
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getParent() != null ? getParent().hashCode() : 0);
        result = 31 * result + (getChildren() != null ? getChildren().hashCode() : 0);
        result = 31 * result + (getBooks() != null ? getBooks().hashCode() : 0);
        result = 31 * result + (getStudies() != null ? getStudies().hashCode() : 0);
        return result;
    }
}
