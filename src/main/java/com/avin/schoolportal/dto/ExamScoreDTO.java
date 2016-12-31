package com.avin.schoolportal.dto;

import com.avin.schoolportal.domain.ExamScore;
import com.avin.schoolportal.validationgroups.ExamScoreRegistration;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by Yubar on 12/9/2016.
 */
public class ExamScoreDTO implements AbstractDTO<ExamScore> {

    private long id;
    @NotNull(groups = ExamScoreRegistration.class)
    @Valid
    private ExamDTO exam;
    @NotNull(groups = ExamScoreRegistration.class)
    @Valid
    private StudentDTO student;
    @Min(value = 0, groups = ExamScoreRegistration.class)
    @Max(value = 20, groups = ExamScoreRegistration.class)
    private double score;

    public ExamScoreDTO() {
    }

    public ExamScoreDTO(ExamScore examScore) {
        if (examScore != null) {
            setId(examScore.getId());
            setScore(examScore.getScore());
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ExamDTO getExam() {
        return exam;
    }

    public void setExam(ExamDTO exam) {
        this.exam = exam;
    }

    public StudentDTO getStudent() {
        return student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public ExamScore convert() {
        ExamScore examScore = new ExamScore();
        examScore.setId(getId());
        examScore.setScore(getScore());
        if (getExam() != null)
            examScore.setExam(getExam().convert());
        if (getStudent() != null)
            examScore.setStudent(getStudent().convert());
        return null;
    }
}
