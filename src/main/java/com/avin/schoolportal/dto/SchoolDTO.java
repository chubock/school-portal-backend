package com.avin.schoolportal.dto;

import com.avin.schoolportal.domain.School;
import com.avin.schoolportal.domain.Student;
import com.avin.schoolportal.validationgroups.SchoolRegistration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yubar on 10/23/2016.
 */
public class SchoolDTO implements AbstractDTO<School> {
    private long id;
    private String code;
    @NotNull(groups = SchoolRegistration.class)
    private String name;
    private List<CourseDTO> courses = new ArrayList<>();
    @Valid
    @Size(min = 1, groups = SchoolRegistration.class)
    private List<EmployeeDTO> employees = new ArrayList<>();
    private List<StudentDTO> students = new ArrayList<>();

    public SchoolDTO() {
    }

    public SchoolDTO(School school) {
        if (school != null) {
            this.id = school.getId();
            this.name = school.getName();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CourseDTO> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseDTO> courses) {
        this.courses = courses;
    }

    public List<EmployeeDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDTO> employees) {
        this.employees = employees;
    }

    public List<StudentDTO> getStudents() {
        return students;
    }

    public void setStudents(List<StudentDTO> students) {
        this.students = students;
    }

    @Override
    public School convert() {
        School school = new School(getName());
        school.setId(getId());
        school.setCode(getCode());
        getEmployees().forEach(employeeDTO -> school.getEmployees().add(employeeDTO.convert()));
        getCourses().forEach(courseDTO -> school.getCourses().add(courseDTO.convert()));
        getStudents().forEach(studentDTO -> school.getStudents().add(studentDTO.convert()));
        return school;
    }
}
