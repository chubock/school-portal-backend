package com.avin.schoolportal.dto;

import com.avin.schoolportal.domain.Exercise;
import com.avin.schoolportal.domain.Student;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Yubar on 12/9/2016.
 */
public class ExerciseDTO implements AbstractDTO<Exercise> {

    private long id;
    private String title;
    private TeacherDTO teacher;
    private CourseDTO course;
    private StudyDTO study;
    private int academicYear;
    private List<ClassroomDTO> classrooms = new ArrayList<>();
    private Date dueDate = new Date();
    private FileDTO questions;
    private FileDTO answers;
    private List<StudentDTO> wrongdoers = new ArrayList<>();
    private SchoolDTO school;

    public ExerciseDTO() {
    }

    public ExerciseDTO(Exercise exercise) {
        if (exercise != null) {
            setId(exercise.getId());
            setTitle(exercise.getTitle());
            setDueDate(exercise.getDueDate());
            setAcademicYear(exercise.getAcademicYear());
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TeacherDTO getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherDTO teacher) {
        this.teacher = teacher;
    }

    public CourseDTO getCourse() {
        return course;
    }

    public void setCourse(CourseDTO course) {
        this.course = course;
    }

    public StudyDTO getStudy() {
        return study;
    }

    public void setStudy(StudyDTO study) {
        this.study = study;
    }

    public int getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(int academicYear) {
        this.academicYear = academicYear;
    }

    public List<ClassroomDTO> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(List<ClassroomDTO> classrooms) {
        this.classrooms = classrooms;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public FileDTO getQuestions() {
        return questions;
    }

    public void setQuestions(FileDTO questions) {
        this.questions = questions;
    }

    public FileDTO getAnswers() {
        return answers;
    }

    public void setAnswers(FileDTO answers) {
        this.answers = answers;
    }

    public List<StudentDTO> getWrongdoers() {
        return wrongdoers;
    }

    public void setWrongdoers(List<StudentDTO> wrongdoers) {
        this.wrongdoers = wrongdoers;
    }

    public SchoolDTO getSchool() {
        return school;
    }

    public void setSchool(SchoolDTO school) {
        this.school = school;
    }

    @Override
    public Exercise convert() {
        Exercise exercise = new Exercise();
        exercise.setId(getId());
        exercise.setTitle(getTitle());
        exercise.setDueDate(getDueDate());
        exercise.setAcademicYear(getAcademicYear());
        if (getQuestions() != null)
            exercise.setQuestions(getQuestions().convert());
        if (getAnswers() != null)
            exercise.setAnswers(getAnswers().convert());
        if (getTeacher() != null)
            exercise.setTeacher(getTeacher().convert());
        if (getCourse() != null)
            exercise.setCourse(getCourse().convert());
        if (getStudy() != null)
            exercise.setStudy(getStudy().convert());
        if (getSchool() != null)
            exercise.setSchool(getSchool().convert());
        getWrongdoers().forEach(studentDTO -> exercise.getWrongdoers().add(studentDTO.convert()));
        getClassrooms().forEach(classroomDTO -> exercise.getClassrooms().add(classroomDTO.convert()));
        return exercise;
    }
}
