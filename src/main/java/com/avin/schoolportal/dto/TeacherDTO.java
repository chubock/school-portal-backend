package com.avin.schoolportal.dto;

import com.avin.schoolportal.domain.Employee;
import com.avin.schoolportal.domain.Teacher;
import com.avin.schoolportal.validationgroups.EmployeeRegistration;
import com.avin.schoolportal.validationgroups.SchoolRegistration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yubar on 10/23/2016.
 */

public class TeacherDTO extends EmployeeDTO {
    private List<ClassTimeDTO> classTimes = new ArrayList<>();

    public TeacherDTO() {
    }

    public TeacherDTO(Teacher teacher) {
        super(teacher);
    }

    public List<ClassTimeDTO> getClassTimes() {
        return classTimes;
    }

    public void setClassTimes(List<ClassTimeDTO> classTimes) {
        this.classTimes = classTimes;
    }

    @Override
    public Teacher convert() {
        return convert(new Teacher());
    }

    protected Teacher convert(Teacher teacher) {
        super.convert(teacher);
        getClassTimes().forEach(classTimeDTO -> teacher.getClassTimes().add(classTimeDTO.convert()));
        return teacher;
    }
}
