package com.avin.schoolportal.dto;

import com.avin.schoolportal.domain.ClassTime;
import com.avin.schoolportal.domain.Classroom;

import java.util.List;

/**
 * Created by Yubar on 11/20/2016.
 */
public class ClassTimeDTO implements AbstractDTO<ClassTime> {

    private long id;
    private ClassroomDTO classroom;
    private EmployeeDTO teacher;
    private StudyDTO study;

    public ClassTimeDTO() {
    }

    public ClassTimeDTO(ClassTime classTime){
        this.id = classTime.getId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ClassroomDTO getClassroom() {
        return classroom;
    }

    public void setClassroom(ClassroomDTO classroom) {
        this.classroom = classroom;
    }

    public EmployeeDTO getTeacher() {
        return teacher;
    }

    public void setTeacher(EmployeeDTO teacher) {
        this.teacher = teacher;
    }

    public StudyDTO getStudy() {
        return study;
    }

    public void setStudy(StudyDTO study) {
        this.study = study;
    }

    @Override
    public ClassTime convert() {
        ClassTime classTime = new ClassTime();
        classTime.setId(id);
        if (classroom != null)
            classTime.setClassroom(classroom.convert());
        if (teacher != null)
            classTime.setTeacher(teacher.convert());
        if (study != null)
            classTime.setStudy(study.convert());
        return classTime;
    }
}
