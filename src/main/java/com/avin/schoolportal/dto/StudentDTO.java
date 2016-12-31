package com.avin.schoolportal.dto;

import com.avin.schoolportal.domain.Course;
import com.avin.schoolportal.domain.File;
import com.avin.schoolportal.domain.Student;
import com.avin.schoolportal.domain.Violation;
import com.avin.schoolportal.validationgroups.ClassroomRegistration;
import com.avin.schoolportal.validationgroups.StudentRegistration;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yubar on 11/20/2016.
 */
public class StudentDTO extends SchoolUserDTO {

    @NotNull(groups = {
            StudentRegistration.class
    })
    private CourseDTO course;
    private ClassroomDTO classroom;
    private ParentDTO parent;
    @Min(value = 1390, groups = {
            StudentRegistration.class
    })
    private int academicYear;
    private double lastYearGrade = 17.0;
    private List<ViolationDTO> violations = new ArrayList<>();

    public StudentDTO() {
    }

    public StudentDTO(Student student) {
        super(student);
        if (student != null) {
            setAcademicYear(student.getAcademicYear());
            setLastYearGrade(student.getLastYearGrade());
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

    public double getLastYearGrade() {
        return lastYearGrade;
    }

    public void setLastYearGrade(double lastYearGrade) {
        this.lastYearGrade = lastYearGrade;
    }

    public List<ViolationDTO> getViolations() {
        return violations;
    }

    public void setViolations(List<ViolationDTO> violations) {
        this.violations = violations;
    }

    @Override
    public Student convert() {
        return convert(new Student());
    }

    protected Student convert(Student student) {
        super.convert(student);
        student.setAcademicYear(getAcademicYear());
        student.setLastYearGrade(getLastYearGrade());
        if (getCourse() != null)
            student.setCourse(getCourse().convert());
        if (getClassroom() != null)
            student.setClassroom(getClassroom().convert());
        if (getParent() != null)
            student.setParent(getParent().convert());
        getViolations().forEach(violationDTO -> student.getViolations().add(violationDTO.convert()));
        return student;
    }
}
