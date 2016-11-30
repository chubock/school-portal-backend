package com.avin.schoolportal.rest;

import com.avin.schoolportal.domain.Employee;
import com.avin.schoolportal.domain.Student;
import com.avin.schoolportal.domain.User;
import com.avin.schoolportal.dto.*;
import com.avin.schoolportal.repository.StudentRepository;
import com.avin.schoolportal.repository.UserRepository;
import com.avin.schoolportal.service.MancipleService;
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
    UserRepository userRepository;

    @Autowired
    MancipleService mancipleService;

    @Autowired
    UserService userService;

    @PreAuthorize("hasAuthority('MANCIPLE')")
    @RequestMapping(method = RequestMethod.GET)
    public Page<StudentDTO> getStudents(@RequestParam Map<String, String> params, Pageable pageable, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        Page<Student> students = studentRepository.findAll(new StudentSpecification(params, user.getSchool()), pageable);
        return students.map(student -> {
            StudentDTO studentDTO = new StudentDTO(student);
            studentDTO.setCourse(new CourseDTO(student.getCourse()));
            studentDTO.setClassroom(new ClassroomDTO(student.getClassroom()));
            studentDTO.setUser(new UserDTO(student.getUser()));
            studentDTO.getUser().setPerson(new PersonDTO(student.getUser().getPerson()));
            return studentDTO;
        });
    }

    @PreAuthorize("isAuthenticated")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public StudentDTO getStudent(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        Student student = studentRepository.findByUser(user);
        StudentDTO studentDTO = new StudentDTO(student);
        studentDTO.setCourse(new CourseDTO(student.getCourse()));
        studentDTO.setClassroom(new ClassroomDTO(student.getClassroom()));
        studentDTO.setUser(new UserDTO(student.getUser()));
        studentDTO.getUser().setPerson(new PersonDTO(student.getUser().getPerson()));
        return studentDTO;
    }

    @PreAuthorize("hasPermission(#id, 'Student', 'READ')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public StudentDTO getStudent(@PathVariable long id, Principal principal) {
        Student student = studentRepository.findOne(id);
        StudentDTO studentDTO = new StudentDTO(student);
        studentDTO.setCourse(new CourseDTO(student.getCourse()));
        studentDTO.setClassroom(new ClassroomDTO(student.getClassroom()));
        studentDTO.setUser(new UserDTO(student.getUser()));
        studentDTO.getUser().setPerson(new PersonDTO(student.getUser().getPerson()));
        return studentDTO;
    }

    @PreAuthorize("hasAuthority('MANCIPLE')")
    @RequestMapping(method = RequestMethod.POST)
    public StudentDTO registerStudent(@Validated(StudentRegistration.class) @RequestBody StudentDTO studentDTO, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        Student student = studentDTO.convert();
        student.setSchool(user.getSchool());
        student = mancipleService.registerStudent(student);
        String studentPassword = userService.resetPassword(student.getUser().getUsername());
        userService.sendRegistrationEmail(student.getUser(), studentPassword);
        String parentPassword = userService.resetPassword(student.getParent().getUser().getUsername());
        userService.sendRegistrationEmail(student.getParent().getUser(), parentPassword);
        StudentDTO ret = new StudentDTO(student);
        ret.setUser(new UserDTO(student.getUser()));
        ret.getUser().setPerson(new PersonDTO(student.getUser().getPerson()));
        ret.setParent(new ParentDTO(student.getParent()));
        ret.getParent().setUser(new UserDTO(student.getParent().getUser()));
        ret.getParent().getUser().setPerson(new PersonDTO(student.getParent().getUser().getPerson()));
        return ret;
    }

    @PreAuthorize("hasPermission(#studentDTO.id, 'Student', 'UPDATE')")
    @RequestMapping(method = RequestMethod.PUT)
    public StudentDTO updateStudent(@RequestBody StudentDTO studentDTO, Principal principal) {
        Student student = studentDTO.convert();
        student = mancipleService.updateStudent(student);
        StudentDTO ret = new StudentDTO(student);
        ret.setUser(new UserDTO(student.getUser()));
        ret.getUser().setPerson(new PersonDTO(student.getUser().getPerson()));
        ret.setParent(new ParentDTO(student.getParent()));
        ret.getParent().setUser(new UserDTO(student.getParent().getUser()));
        ret.getParent().getUser().setPerson(new PersonDTO(student.getParent().getUser().getPerson()));
        return ret;
    }

    @PreAuthorize("hasPermission(#id, 'Student', 'DELETE')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteStudent(@PathVariable long id) {
        Student student = studentRepository.findOne(id);
        mancipleService.deleteStudent(student);
    }

}
