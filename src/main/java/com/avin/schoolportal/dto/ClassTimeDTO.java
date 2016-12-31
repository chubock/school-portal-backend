package com.avin.schoolportal.dto;

import com.avin.schoolportal.domain.ClassTime;
import com.avin.schoolportal.domain.WeekDay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yubar on 11/20/2016.
 */
public class ClassTimeDTO implements AbstractDTO<ClassTime> {

    private long id;
    private ClassroomDTO classroom;
    private TeacherDTO teacher;
    private StudyDTO study;
    private double from;
    private double to;
    private WeekDay weekDay;

    public ClassTimeDTO() {
    }

    public ClassTimeDTO(ClassTime classTime){
        if (classTime != null) {
            setId(classTime.getId());
            setWeekDay(classTime.getWeekDay());
            setFrom(classTime.getFrom());
            setTo(classTime.getTo());
        }
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

    public TeacherDTO getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherDTO teacher) {
        this.teacher = teacher;
    }

    public StudyDTO getStudy() {
        return study;
    }

    public void setStudy(StudyDTO study) {
        this.study = study;
    }

    public WeekDay getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(WeekDay weekDay) {
        this.weekDay = weekDay;
    }

    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }

    @Override
    public ClassTime convert() {
        ClassTime classTime = new ClassTime();
        classTime.setId(id);
        classTime.setFrom(getFrom());
        classTime.setTo(getTo());
        classTime.setWeekDay(getWeekDay());
        if (getClassroom() != null)
            classTime.setClassroom(getClassroom().convert());
        if (getTeacher() != null)
            classTime.setTeacher(getTeacher().convert());
        if (getStudy() != null)
            classTime.setStudy(getStudy().convert());
        return classTime;
    }
}
