package com.avin.schoolportal.dto;

import com.avin.schoolportal.domain.Employee;
import com.avin.schoolportal.validationgroups.EmployeeRegistration;
import com.avin.schoolportal.validationgroups.SchoolRegistration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yubar on 10/23/2016.
 */

public class EmployeeDTO implements AbstractDTO<Employee> {
    private long id;
    private SchoolDTO school;
    @Valid
    @NotNull(groups = {
            SchoolRegistration.class,
            EmployeeRegistration.class
    })
    private UserDTO user;
    private List<ClassTimeDTO> classTimes = new ArrayList<>();

    public EmployeeDTO() {
    }

    public EmployeeDTO(Employee employee) {
        if (employee != null){
            this.id = employee.getId();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SchoolDTO getSchool() {
        return school;
    }

    public void setSchool(SchoolDTO school) {
        this.school = school;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public List<ClassTimeDTO> getClassTimes() {
        return classTimes;
    }

    public void setClassTimes(List<ClassTimeDTO> classTimes) {
        this.classTimes = classTimes;
    }

    @Override
    public Employee convert() {
        Employee employee = new Employee();
        employee.setId(getId());
        if (getSchool() != null)
            employee.setSchool(getSchool().convert());
        if (getUser() != null)
            employee.setUser(getUser().convert());
        classTimes.forEach(classTimeDTO -> employee.getClassTimes().add(classTimeDTO.convert()));
        return employee;
    }
}
