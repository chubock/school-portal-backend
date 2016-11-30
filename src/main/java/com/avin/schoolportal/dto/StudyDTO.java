package com.avin.schoolportal.dto;

import com.avin.schoolportal.domain.Study;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yubar on 11/20/2016.
 */
public class StudyDTO implements AbstractDTO<Study> {

    private long id;
    private String name;
    private double hours;
    private CourseDTO course;
    private List<BookDTO> books = new ArrayList<>();

    public StudyDTO() {
    }

    public StudyDTO(Study study) {
        this.id = study.getId();
        this.name = study.getName();
        this.hours = study.getHours();
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

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public CourseDTO getCourse() {
        return course;
    }

    public void setCourse(CourseDTO course) {
        this.course = course;
    }

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }

    @Override
    public Study convert() {
        Study study = new Study(name, hours);
        if (course != null)
            study.setCourse(course.convert());
        books.forEach(bookDTO -> study.getBooks().add(bookDTO.convert()));
        return study;
    }
}
