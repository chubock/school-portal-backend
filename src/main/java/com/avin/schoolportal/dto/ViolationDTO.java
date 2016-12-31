package com.avin.schoolportal.dto;

import com.avin.schoolportal.domain.Violation;
import com.avin.schoolportal.domain.ViolationType;
import com.avin.schoolportal.dto.AbstractDTO;
import com.avin.schoolportal.dto.MancipleDTO;
import com.avin.schoolportal.dto.SchoolDTO;
import com.avin.schoolportal.dto.StudentDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Yubar on 12/4/2016.
 */
public class ViolationDTO implements AbstractDTO<Violation> {

    private long id;
    private ViolationType type;
    private List<StudentDTO> students = new ArrayList<>();
    private MancipleDTO manciple;
    private Date date = new Date();
    private String comment;
    private SchoolDTO school;

    public ViolationDTO() {
    }

    public ViolationDTO(Violation violation) {
        if (violation != null) {
            setId(violation.getId());
            setType(violation.getType());
            setDate(violation.getDate());
            setComment(violation.getComment());
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ViolationType getType() {
        return type;
    }

    public void setType(ViolationType type) {
        this.type = type;
    }

    public List<StudentDTO> getStudents() {
        return students;
    }

    public void setStudents(List<StudentDTO> students) {
        this.students = students;
    }

    public MancipleDTO getManciple() {
        return manciple;
    }

    public void setManciple(MancipleDTO manciple) {
        this.manciple = manciple;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public SchoolDTO getSchool() {
        return school;
    }

    public void setSchool(SchoolDTO school) {
        this.school = school;
    }

    @Override
    public Violation convert() {
        Violation violation = new Violation(getType());
        violation.setId(getId());
        violation.setComment(getComment());
        violation.setDate(getDate());
        if (getManciple() != null)
            violation.setManciple(getManciple().convert());
        if (getSchool() != null)
            violation.setSchool(getSchool().convert());
        getStudents().forEach(studentDTO -> {
            violation.getStudents().add(studentDTO.convert());
        });
        return violation;
    }
}
