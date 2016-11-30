package com.avin.schoolportal.service;

import com.avin.schoolportal.domain.*;
import com.avin.schoolportal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    UserService userService;

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

        User user = student.getUser();
        user.setUsername(student.getSchool().getCode() + '1' + student.getAcademicYear() + student.getCourse().getId());
        user.setSchool(student.getSchool());
        user.getRoles().clear();
        user.getRoles().add(Role.STUDENT);
        student.setUser(userService.registerUser(user));
        studentRepository.save(student);

        Parent parent = student.getParent();
        parent.setSchool(student.getSchool());
        parent.getUser().setUsername(student.getUser().getUsername().replaceFirst(student.getSchool().getCode() + '2', student.getSchool().getCode() + '2'));
        parent.getUser().setRoles(new ArrayList<>());
        parent.getUser().getRoles().add(Role.PARENT);
        parent.getUser().setPerson(student.getUser().getPerson());
        parent.setUser(userService.registerUser(parent.getUser()));

        return student;
    }

    @PreAuthorize("hasPermission(#student, 'UPDATE')")
    public Student updateStudent(Student student) {
        Student s = studentRepository.findOne(student.getId());

        if (student.getCourse() != null)
            s.setCourse(courseRepository.findOne(student.getCourse().getId()));

        if (student.getAcademicYear() != 0 && student.getAcademicYear() != s.getAcademicYear())
            s.setAcademicYear(student.getAcademicYear());

        userService.updateUser(student.getUser());
        if (! s.getUser().getUsername().startsWith(s.getSchool().getCode() + '1' + s.getAcademicYear() + s.getCourse().getId()))
            s.getUser().setUsername(s.getSchool().getCode() + '1' + s.getAcademicYear() + s.getCourse().getId());

        Classroom classroom = student.getClassroom();
        if (classroom.getId() == 0)
            classroom = classroomRepository.save(classroom);
        else
            classroom = classroomRepository.findOne(classroom.getId());
        s.setClassroom(classroom);

        userService.updateUser(s.getParent().getUser());
        if (! s.getParent().getUser().getUsername().startsWith(s.getSchool().getCode() + '2' + s.getAcademicYear() + s.getCourse().getId()))
            s.getParent().getUser().setUsername(s.getSchool().getCode() + '2' + s.getAcademicYear() + s.getCourse().getId());
        return s;
    }

    @PreAuthorize("hasPermission(#student, 'DELETE')")
    public void deleteStudent(Student student){
        studentRepository.delete(student);
    }


}
