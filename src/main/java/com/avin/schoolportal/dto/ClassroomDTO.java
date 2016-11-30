package com.avin.schoolportal.dto;

import com.avin.schoolportal.domain.Classroom;
import com.avin.schoolportal.domain.Employee;
import com.avin.schoolportal.validationgroups.ClassroomRegistration;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yubar on 11/20/2016.
 */
public class ClassroomDTO implements AbstractDTO<Classroom> {

    private long id;
    @NotNull(groups = {
            ClassroomRegistration.class
    })
    private String name;
    @Min(value = 1390, groups = {
            ClassroomRegistration.class
    })
    private int academicYear;
    @NotNull(groups = {
            ClassroomRegistration.class
    })
    @Valid
    private CourseDTO course;
    private SchoolDTO school;
    private List<StudentDTO> students = new ArrayList<>();
    private List<ClassTimeDTO> classTimes = new ArrayList<>();

    public ClassroomDTO() {
    }

    public ClassroomDTO(Classroom classroom) {
        this.id = classroom.getId();
        this.name = classroom.getName();
        this.academicYear = classroom.getAcademicYear();
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

    public int getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(int academicYear) {
        this.academicYear = academicYear;
    }

    public CourseDTO getCourse() {
        return course;
    }

    public void setCourse(CourseDTO course) {
        this.course = course;
    }

    public SchoolDTO getSchool() {
        return school;
    }

    public void setSchool(SchoolDTO school) {
        this.school = school;
    }

    public List<StudentDTO> getStudents() {
        return students;
    }

    public void setStudents(List<StudentDTO> students) {
        this.students = students;
    }

    public List<ClassTimeDTO> getClassTimes() {
        return classTimes;
    }

    public void setClassTimes(List<ClassTimeDTO> classTimes) {
        this.classTimes = classTimes;
    }

    @Override
    public Classroom convert() {
        Classroom classroom = new Classroom(this.name, this.academicYear);
        classroom.setId(this.id);
        if (course != null)
            classroom.setCourse(course.convert());
        if (school != null)
            classroom.setSchool(school.convert());
        classTimes.forEach(classTimeDTO -> classroom.getClassTimes().add(classTimeDTO.convert()));
        return classroom;
    }
}
