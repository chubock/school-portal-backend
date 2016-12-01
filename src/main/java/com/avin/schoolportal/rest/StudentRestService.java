package com.avin.schoolportal.rest;

import com.avin.schoolportal.domain.SchoolUser;
import com.avin.schoolportal.domain.Student;
import com.avin.schoolportal.dto.*;
import com.avin.schoolportal.repository.SchoolUserRepository;
import com.avin.schoolportal.repository.StudentRepository;
import com.avin.schoolportal.service.MancipleService;
import com.avin.schoolportal.service.SchoolUserService;
import com.avin.schoolportal.service.UserService;
import com.avin.schoolportal.specification.StudentSpecification;
import com.avin.schoolportal.validationgroups.StudentRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

/**
 * Created by Yubar on 11/25/2016.
 */

@RestController
@RequestMapping("/students")
public class StudentRestService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    SchoolUserRepository schoolUserRepository;

    @Autowired
    MancipleService mancipleService;

    @Autowired
    UserService userService;

    @Autowired
    SchoolUserService schoolUserService;

    @PreAuthorize("hasAuthority('MANCIPLE')")
    @RequestMapping(method = RequestMethod.GET)
    public Page<StudentDTO> getStudents(@RequestParam Map<String, String> params, Pageable pageable, Principal principal) {
        SchoolUser user = schoolUserRepository.findByUsername(principal.getName());
        Page<Student> students = studentRepository.findAll(new StudentSpecification(params, user.getSchool()), pageable);
        return students.map(student -> {
            StudentDTO studentDTO = new StudentDTO(student);
            studentDTO.setCourse(new CourseDTO(student.getCourse()));
            studentDTO.setClassroom(new ClassroomDTO(student.getClassroom()));
            studentDTO.setPerson(new PersonDTO(student.getPerson()));
            return studentDTO;
        });
    }

    @PreAuthorize("isAuthenticated")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public StudentDTO getStudent(Principal principal) {
        Student student = studentRepository.findByUsername(principal.getName());
        StudentDTO studentDTO = new StudentDTO(student);
        studentDTO.setCourse(new CourseDTO(student.getCourse()));
        studentDTO.setClassroom(new ClassroomDTO(student.getClassroom()));
        studentDTO.setPerson(new PersonDTO(student.getPerson()));
        return studentDTO;
    }

    @PreAuthorize("hasPermission(#id, 'Student', 'READ')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public StudentDTO getStudent(@PathVariable long id, Principal principal) {
        Student student = studentRepository.findOne(id);
        StudentDTO studentDTO = new StudentDTO(student);
        studentDTO.setCourse(new CourseDTO(student.getCourse()));
        studentDTO.setClassroom(new ClassroomDTO(student.getClassroom()));
        studentDTO.setPerson(new PersonDTO(student.getPerson()));
        return studentDTO;
    }

    @PreAuthorize("hasAuthority('MANCIPLE')")
    @RequestMapping(method = RequestMethod.POST)
    public StudentDTO registerStudent(@Validated(StudentRegistration.class) @RequestBody StudentDTO studentDTO, Principal principal) {
        SchoolUser user = schoolUserRepository.findByUsername(principal.getName());
        Student student = studentDTO.convert();
        student.setSchool(user.getSchool());
        student = mancipleService.registerStudent(student);
        String studentPassword = userService.resetPassword(student.getUsername());
        schoolUserService.sendRegistrationEmail(student, studentPassword);
        String parentPassword = userService.resetPassword(student.getParent().getUsername());
        schoolUserService.sendRegistrationEmail(student.getParent(), parentPassword);
        StudentDTO ret = new StudentDTO(student);
        ret.setPerson(new PersonDTO(student.getPerson()));
        ret.setParent(new ParentDTO(student.getParent()));
        ret.getParent().setPerson(new PersonDTO(student.getParent().getPerson()));
        return ret;
    }

    @PreAuthorize("hasPermission(#studentDTO.id, 'Student', 'UPDATE')")
    @RequestMapping(method = RequestMethod.PUT)
    public StudentDTO updateStudent(@RequestBody StudentDTO studentDTO) {
        Student student = studentDTO.convert();
        student = mancipleService.updateStudent(student);
        StudentDTO ret = new StudentDTO(student);
        ret.setPerson(new PersonDTO(student.getPerson()));
        ret.setParent(new ParentDTO(student.getParent()));
        ret.getParent().setPerson(new PersonDTO(student.getParent().getPerson()));
        return ret;
    }

    @PreAuthorize("hasPermission(#id, 'Student', 'DELETE')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteStudent(@PathVariable long id) {
        Student student = studentRepository.findOne(id);
        mancipleService.deleteStudent(student);
    }

}
