package com.avin.schoolportal.dto;

import com.avin.schoolportal.domain.File;
import com.avin.schoolportal.domain.Handout;

/**
 * Created by Yubar on 12/30/2016.
 */
public class HandoutDTO implements AbstractDTO<Handout> {

    private long id;
    private String title;
    private CourseDTO course;
    private StudyDTO study;
    private FileDTO file;
    private TeacherDTO teacher;
    private SchoolDTO school;

    public HandoutDTO() {
    }

    public HandoutDTO(Handout handout) {
        if (handout != null) {
            setId(handout.getId());
            setTitle(handout.getTitle());
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

    public FileDTO getFile() {
        return file;
    }

    public void setFile(FileDTO file) {
        this.file = file;
    }

    public TeacherDTO getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherDTO teacher) {
        this.teacher = teacher;
    }

    public SchoolDTO getSchool() {
        return school;
    }

    public void setSchool(SchoolDTO school) {
        this.school = school;
    }

    @Override
    public Handout convert() {
        Handout handout = new Handout();
        handout.setId(getId());
        handout.setTitle(getTitle());
        if (getCourse() != null)
            handout.setCourse(getCourse().convert());
        if (getStudy() != null)
            handout.setStudy(getStudy().convert());
        if (getFile() != null)
            handout.setFile(getFile().convert());
        if (getTeacher() != null)
            handout.setTeacher(getTeacher().convert());
        if (getSchool() != null)
            handout.setSchool(getSchool().convert());
        return handout;
    }
}
