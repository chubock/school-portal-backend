package com.avin.schoolportal.service;

import com.avin.schoolportal.domain.*;
import com.avin.schoolportal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    StudyRepository studyRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    ClassTimeRepository classTimeRepository;

    @Autowired
    ViolationRepository violationRepository;

    @Autowired
    FileRepository fileRepository;

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

        Parent parent = student.getParent();
        student.setParent(null);

        student.setCourse(courseRepository.findOne(student.getCourse().getId()));

        if (student.getClassroom() != null)
            student.setClassroom(classroomRepository.findOne(student.getClassroom().getId()));

        String lastUsername = userRepository.findLastUsernameLike(student.getSchool().getCode() + student.getUsernamePrefix() + student.getAcademicYear() + student.getCourse().getId() + "%");
        if (lastUsername == null)
            lastUsername = student.getSchool().getCode() + student.getUsernamePrefix() + student.getAcademicYear() + student.getCourse().getId() + "000";

        student.setUsername(new BigDecimal(lastUsername).add(BigDecimal.ONE).toString());
        student.setSchool(student.getSchool());

        student = (Student) schoolUserService.registerSchoolUser(student);

        parent.setStudent(student);
        parent.setSchool(student.getSchool());
        parent.setUsername(student.getSchool().getCode() + parent.getUsernamePrefix() + student.getUsername().substring(4));
        parent.setFirstName(student.getFirstName());
        parent.setLastName(student.getLastName());
        parent.setFatherName(student.getFatherName());
        parent.setNationalId(student.getNationalId());
        parent.setBirthday(student.getBirthday());
        parent.setGender(student.getGender());

        schoolUserService.registerSchoolUser(parent);
        student.setParent(parent);

        return student;
    }

    @PreAuthorize("hasPermission(#student, 'UPDATE')")
    public Student updateStudent(Student student) {
        Student s = studentRepository.findOne(student.getId());
        s.setLastYearGrade(student.getLastYearGrade());

        if (student.getCourse() != null)
            s.setCourse(courseRepository.findOne(student.getCourse().getId()));

        if (student.getAcademicYear() != 0)
            s.setAcademicYear(student.getAcademicYear());

        if (student.getClassroom() != null)
            s.setClassroom(classroomRepository.findOne(student.getClassroom().getId()));
        else
            s.setClassroom(null);

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
        schoolUserService.updateSchoolUser(student.getParent());

        return s;
    }

    @PreAuthorize("hasPermission(#student, 'DELETE')")
    public void deleteStudent(Student student){
        schoolUserService.deleteUser(student);
    }

    @PreAuthorize("hasPermission(#classTime.teacher, 'READ') and hasPermission(#classTime.classroom, 'UPDATE')")
    public ClassTime addClassTime(ClassTime classTime) {
        Classroom classroom = classroomRepository.findOne(classTime.getClassroom().getId());
        Study study = studyRepository.findOne(classTime.getStudy().getId());
        Teacher teacher = teacherRepository.findOne(classTime.getTeacher().getId());

        List<ClassTime> classTimes = classTimeRepository.findConflictClassTimeByClassroom(classroom, classTime.getFrom(), classTime.getTo(), classTime.getWeekDay());
        if (! classTimes.isEmpty())
            throw new IllegalArgumentException();

        classTimes = classTimeRepository.findConflictClassTimeByTeacher(teacher, classTime.getFrom(), classTime.getTo(), classTime.getWeekDay());
        if (! classTimes.isEmpty())
            throw new IllegalArgumentException();

        classTimes = classTimeRepository.findByClassroomAndStudy(classroom, study);
        double total = 0;
        for (ClassTime ct : classTimes)
            total += (ct.getTo() - ct.getFrom());
        if (total + (classTime.getTo() - classTime.getFrom()) > study.getHours())
            throw new IllegalArgumentException();

        classTime.setTeacher(teacher);
        classTime.setStudy(study);
        classTime.setClassroom(classroom);
        return classTimeRepository.save(classTime);
    }

    @PreAuthorize("hasPermission(#classTime.teacher, 'READ') and hasPermission(#classTime.classroom, 'UPDATE')")
    public void removeClassTime(ClassTime classTime) {
        classTime = classTimeRepository.findOne(classTime.getId());
        classTimeRepository.delete(classTime);
    }

    @PreAuthorize("hasPermission(#violation, 'CREATE')")
    public Violation registerViolation(Violation violation) {
        return violationRepository.save(violation);
    }

    @PreAuthorize("hasPermission(#violation, 'DELETE')")
    public void removeViolation(Violation violation){
        violationRepository.delete(violation);
    }

    @PreAuthorize("hasPermission(#violation, 'UPDATE')")
    public List<Student> updateViolation(Violation violation) {
        List<Student> newStudents = new ArrayList<>();
        Violation v = violationRepository.findOneWithStudents(violation.getId());
        v.setComment(violation.getComment());
        v.setDate(violation.getDate());
        v.setType(violation.getType());
        violation.getStudents().forEach(student -> {
            Student s = studentRepository.findOne(student.getId());
            if (!v.getStudents().contains(s))
                newStudents.add(s);
        });
        v.getStudents().clear();
        v.getStudents().addAll(violation.getStudents());
        return newStudents;
    }

}
