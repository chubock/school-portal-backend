package com.avin.schoolportal.service;

import com.avin.schoolportal.domain.*;
import com.avin.schoolportal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Yubar on 11/24/2016.
 */

@Service
@Transactional
public class MancipleService {

    @Autowired
    ClassroomRepository classroomRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    SchoolUserService schoolUserService;

    @Autowired
    ParentRepository parentRepository;

    @Autowired
    StudentRepository studentRepository;

    @PreAuthorize("hasPermission(#classroom, 'CREATE')")
    public Classroom registerClassroom(Classroom classroom) {
        return classroomRepository.save(classroom);
    }

    @PreAuthorize("hasPermission(#classroom.id, 'Classroom', 'UPDATE')")
    public Classroom updateClassroom(Classroom classroom) {
        Classroom c = classroomRepository.findOne(classroom.getId());
        c.setName(classroom.getName());
        c.setAcademicYear(classroom.getAcademicYear());
        if (classroom.getCourse() != null)
            c.setCourse(courseRepository.findOne(classroom.getCourse().getId()));
        return c;
    }

    @PreAuthorize("hasPermission(#classroom, 'DELETE')")
    public void deleteClassroom(Classroom classroom) {
        classroomRepository.delete(classroom);
    }

    @PreAuthorize("hasPermission(#student, 'CREATE')")
    public Student registerStudent(Student student) {

        student.setCourse(courseRepository.findOne(student.getCourse().getId()));

        Classroom classroom = student.getClassroom();
        if (classroom.getId() == 0)
            classroom = classroomRepository.save(classroom);
        else
            classroom = classroomRepository.findOne(classroom.getId());
        student.setClassroom(classroom);

        String lastUsername = userRepository.findLastUsernameLike(student.getSchool().getCode() + student.getUsernamePrefix() + student.getAcademicYear() + student.getCourse().getId() + "%");
        if (lastUsername == null)
            lastUsername = student.getSchool().getCode() + student.getUsernamePrefix() + student.getAcademicYear() + student.getCourse().getId() + "000";

        student.setUsername(new BigDecimal(lastUsername).add(BigDecimal.ONE).toString());
        student.setSchool(student.getSchool());
        schoolUserService.registerSchoolUser(student);

        Parent parent = student.getParent();
        parent.setSchool(student.getSchool());
        parent.setUsername(student.getUsername().replaceFirst(student.getSchool().getCode() + '2', student.getSchool().getCode() + '2'));
        parent.setPerson(student.getPerson());
        schoolUserService.registerSchoolUser(parent);

        return student;
    }

    @PreAuthorize("hasPermission(#student, 'UPDATE')")
    public Student updateStudent(Student student) {
        Student s = studentRepository.findOne(student.getId());

        if (student.getCourse() != null)
            s.setCourse(courseRepository.findOne(student.getCourse().getId()));

        if (student.getAcademicYear() != 0 && student.getAcademicYear() != s.getAcademicYear())
            s.setAcademicYear(student.getAcademicYear());

        Classroom classroom = student.getClassroom();
        if (classroom.getId() == 0)
            classroom = classroomRepository.save(classroom);
        else
            classroom = classroomRepository.findOne(classroom.getId());
        s.setClassroom(classroom);

        if (! s.getUsername().startsWith(s.getSchool().getCode() + s.getUsernamePrefix() + s.getAcademicYear() + s.getCourse().getId())) {
            String lastUsername = userRepository.findLastUsernameLike(s.getSchool().getCode() + s.getUsernamePrefix() + s.getAcademicYear() + s.getCourse().getId() + "%");
            if (lastUsername == null)
                lastUsername = s.getSchool().getCode() + s.getUsernamePrefix() + s.getAcademicYear() + s.getCourse().getId() + "000";
            s.setUsername(new BigDecimal(lastUsername).add(BigDecimal.ONE).toString());
        }

        schoolUserService.updateSchoolUser(student);

        if (! s.getParent().getUsername().startsWith(s.getSchool().getCode() + s.getParent().getUsernamePrefix() + s.getAcademicYear() + s.getCourse().getId())) {
            String lastUsername = userRepository.findLastUsernameLike(s.getSchool().getCode() + s.getParent().getUsernamePrefix() + s.getAcademicYear() + s.getCourse().getId() + "%");
            if (lastUsername == null)
                lastUsername = s.getSchool().getCode() + s.getParent().getUsernamePrefix() + s.getAcademicYear() + s.getCourse().getId() + "000";
            s.getParent().setUsername(new BigDecimal(lastUsername).add(BigDecimal.ONE).toString());
        }

        return s;
    }

    @PreAuthorize("hasPermission(#student, 'DELETE')")
    public void deleteStudent(Student student){
        schoolUserService.deleteUser(student);
    }


}
