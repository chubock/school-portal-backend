package com.avin.schoolportal.dto;

import com.avin.schoolportal.domain.Handout;
import com.avin.schoolportal.domain.Teacher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yubar on 10/23/2016.
 */

public class TeacherDTO extends EmployeeDTO {
    private List<ClassTimeDTO> classTimes = new ArrayList<>();
    private List<HandoutDTO> handouts = new ArrayList<>();

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

    public List<HandoutDTO> getHandouts() {
        return handouts;
    }

    public void setHandouts(List<HandoutDTO> handouts) {
        this.handouts = handouts;
    }

    @Override
    public Teacher convert() {
        return convert(new Teacher());
    }

    protected Teacher convert(Teacher teacher) {
        super.convert(teacher);
        getClassTimes().forEach(classTimeDTO -> teacher.getClassTimes().add(classTimeDTO.convert()));
        getHandouts().forEach(handoutDTO -> teacher.getHandouts().add(handoutDTO.convert()));
        return teacher;
    }
}
