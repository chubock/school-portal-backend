package com.avin.schoolportal.dto;

import com.avin.schoolportal.domain.Book;

/**
 * Created by Yubar on 11/19/2016.
 */
public class BookDTO implements AbstractDTO<Book> {

    private long id;
    private String name;
    private String code;
    private CourseDTO course;
    private StudyDTO study;

    public BookDTO() {
    }

    public BookDTO(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.code = book.getCode();
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

    public CourseDTO getCourse() {
        return course;
    }

    public void setCourse(CourseDTO course) {
        this.course = course;
    }

    public StudyDTO getStudy() {
        return study;
    }

    public void setStudy(StudyDTO study) {
        this.study = study;
    }

    @Override
    public Book convert() {
        Book book = new Book(name, code);
        book.setId(id);
        if (course != null)
            book.setCourse(course.convert());
        if (study != null)
            book.setStudy(study.convert());
        return book;
    }
}
