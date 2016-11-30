package com.avin.schoolportal.dto;

import com.avin.schoolportal.domain.Course;
import com.avin.schoolportal.domain.Student;
import com.avin.schoolportal.validationgroups.ClassroomRegistration;
import com.avin.schoolportal.validationgroups.StudentRegistration;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by Yubar on 11/20/2016.
 */
public class StudentDTO implements AbstractDTO<Student> {

    private long id;
    @NotNull(groups = {
            StudentRegistration.class
    })
    @Valid
    private UserDTO user;
    @NotNull(groups = {
            StudentRegistration.class
    })
    private CourseDTO course;
    private ClassroomDTO classroom;
    @NotNull(groups = {
            StudentRegistration.class
    })
    @Valid
    private ParentDTO parent;
    private SchoolDTO school;
    @Min(value = 1390, groups = {
            StudentRegistration.class
    })
    private int academicYear;

    public StudentDTO() {
    }

    public StudentDTO(Student student) {
        if (student != null) {
            this.id = student.getId();
            this.academicYear = student.getAcademicYear();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public CourseDTO getCourse() {
        return course;
    }

    public void setCourse(CourseDTO course) {
        this.course = course;
    }

    public ClassroomDTO getClassroom() {
        return classroom;
    }

    public void setClassroom(ClassroomDTO classroom) {
        this.classroom = classroom;
    }

    public ParentDTO getParent() {
        return parent;
    }

    public void setParent(ParentDTO parent) {
        this.parent = parent;
    }

    public SchoolDTO getSchool() {
        return school;
    }

    public void setSchool(SchoolDTO school) {
        this.school = school;
    }

    public int getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(int academicYear) {
        this.academicYear = academicYear;
    }

    @Override
    public Student convert() {
        Student student = new Student();
        student.setId(getId());
        student.setAcademicYear(getAcademicYear());
        if (getUser() != null)
            student.setUser(getUser().convert());
        if (getCourse() != null)
            student.setCourse(getCourse().convert());
        if (getClassroom() != null)
            student.setClassroom(getClassroom().convert());
        if (getParent() != null)
            student.setParent(getParent().convert());
        if (getSchool() != null)
            student.setSchool(getSchool().convert());
        return student;
    }
}
