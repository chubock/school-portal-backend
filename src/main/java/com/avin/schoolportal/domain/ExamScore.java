package com.avin.schoolportal.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Yubar on 12/9/2016.
 */

@Entity(name = "ExamScore")
public class ExamScore implements Serializable {

    private long id;
    private Exam exam;
    private Student student;
    private double score;

    public ExamScore() {
    }

    public ExamScore(Exam exam, Student student, double score) {
        this.exam = exam;
        this.student = student;
        this.score = score;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Column(nullable = false)
    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExamScore)) return false;

        ExamScore examScore = (ExamScore) o;

        if (getId() != examScore.getId()) return false;
        if (getId() > 0)
            return true;
        if (Double.compare(examScore.getScore(), getScore()) != 0) return false;
        if (getExam() != null ? !getExam().equals(examScore.getExam()) : examScore.getExam() != null) return false;
        return getStudent() != null ? getStudent().equals(examScore.getStudent()) : examScore.getStudent() == null;

    }

    @Override
    public int hashCode() {
        if (getId() > 0)
            return (int) getId();
        int result;
        long temp;
        result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getExam() != null ? getExam().hashCode() : 0);
        result = 31 * result + (getStudent() != null ? getStudent().hashCode() : 0);
        temp = Double.doubleToLongBits(getScore());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
