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
public class StudentDTO extends SchoolUserDTO {

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
    @Min(value = 1390, groups = {
            StudentRegistration.class
    })
    private int academicYear;

    public StudentDTO() {
    }

    public StudentDTO(Student student) {
        super(student);
        if (student != null) {
            setAcademicYear(student.getAcademicYear());
            setCourse(new CourseDTO(student.getCourse()));
            setClassroom(new ClassroomDTO(student.getClassroom()));
        }
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

    public int getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(int academicYear) {
        this.academicYear = academicYear;
    }

    @Override
    public Student convert() {
        return convert(new Student());
    }

    protected Student convert(Student student) {
        super.convert(student);
        student.setAcademicYear(getAcademicYear());
        if (getCourse() != null)
            student.setCourse(getCourse().convert());
        if (getClassroom() != null)
            student.setClassroom(getClassroom().convert());
        if (getParent() != null)
            student.setParent(getParent().convert());
        return student;
    }
}
