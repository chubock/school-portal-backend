package com.avin.schoolportal.dto;

import com.avin.schoolportal.domain.Exam;
import com.avin.schoolportal.domain.ExamType;
import com.avin.schoolportal.validationgroups.ExamRegistration;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Yubar on 12/9/2016.
 */
public class ExamDTO implements AbstractDTO<Exam> {

    private long id;
    @NotNull(groups = ExamRegistration.class)
    private String title;
    private ExamType type = ExamType.WRITTEN;
    private CourseDTO course;
    private StudyDTO study;
    private int academicYear;
    private List<ClassroomDTO> classrooms = new ArrayList<>();
    @NotNull(groups = ExamRegistration.class)
    private Date dueDate = new Date();
    private FileDTO questions;
    private FileDTO answers;
    private List<ExamScoreDTO> scores = new ArrayList<>();
    private TeacherDTO teacher;
    private SchoolDTO school;

    public ExamDTO() {
    }

    public ExamDTO(Exam exam) {
        if(exam != null) {
            setId(exam.getId());
            setTitle(exam.getTitle());
            setType(exam.getType());
            setDueDate(exam.getDueDate());
            setAcademicYear(exam.getAcademicYear());
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

    public ExamType getType() {
        return type;
    }

    public void setType(ExamType type) {
        this.type = type;
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

    public List<ExamScoreDTO> getScores() {
        return scores;
    }

    public void setScores(List<ExamScoreDTO> scores) {
        this.scores = scores;
    }

    public TeacherDTO getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherDTO teacher) {
        this.teacher = teacher;
    }

    public SchoolDTO getSchool() {
        return school;
    }

    public void setSchool(SchoolDTO school) {
        this.school = school;
    }

    @Override
    public Exam convert() {
        Exam exam = new Exam();
        exam.setId(getId());
        exam.setTitle(getTitle());
        exam.setType(getType());
        exam.setDueDate(getDueDate());
        exam.setAcademicYear(getAcademicYear());
        if (getQuestions() != null)
            exam.setQuestions(getQuestions().convert());
        if (getAnswers() != null)
            exam.setAnswers(getAnswers().convert());
        if (getCourse() != null)
            exam.setCourse(getCourse().convert());
        if (getStudy() != null)
            exam.setStudy(getStudy().convert());
        if (getTeacher() != null)
            exam.setTeacher(getTeacher().convert());
        if (getSchool() != null)
            exam.setSchool(getSchool().convert());
        getClassrooms().forEach(classroomDTO -> exam.getClassrooms().add(classroomDTO.convert()));
        getScores().forEach(examScoreDTO -> {exam.getScores().add(examScoreDTO.convert());});
        return exam;
    }
}
