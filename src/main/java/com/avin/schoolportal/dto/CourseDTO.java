package com.avin.schoolportal.dto;

import com.avin.schoolportal.domain.Course;
import com.avin.schoolportal.validationgroups.ClassroomRegistration;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yubar on 10/23/2016.
 */
public class CourseDTO implements AbstractDTO<Course> {

    @Min(value = 1, groups = {
            ClassroomRegistration.class
    })
    private long id;
    private String title;
    private CourseDTO parent;
    private List<CourseDTO> children = new ArrayList<>();
    private List<BookDTO> books = new ArrayList<>();
    private List<StudyDTO> studies = new ArrayList<>();

    public CourseDTO() {
    }

    public CourseDTO(Course course) {
        if (course != null) {
            this.id = course.getId();
            this.title = course.getTitle();
        }
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

    public CourseDTO getParent() {
        return parent;
    }

    public void setParent(CourseDTO parent) {
        this.parent = parent;
    }

    public List<CourseDTO> getChildren() {
        return children;
    }

    public void setChildren(List<CourseDTO> children) {
        this.children = children;
    }

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }

    public List<StudyDTO> getStudies() {
        return studies;
    }

    public void setStudies(List<StudyDTO> studies) {
        this.studies = studies;
    }

    @Override
    public Course convert() {
        Course course = new Course(getTitle());
        course.setId(getId());
        if (getParent() != null)
            course.setParent(getParent().convert());
        children.forEach(courseDTO -> course.getChildren().add(courseDTO.convert()));
        books.forEach(bookDTO -> course.getBooks().add(bookDTO.convert()));
        studies.forEach(studyDTO -> course.getStudies().add(studyDTO.convert()));
        return course;
    }
}
