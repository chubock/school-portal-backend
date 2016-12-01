package com.avin.schoolportal.rest;

import com.avin.schoolportal.domain.SchoolUser;
import com.avin.schoolportal.domain.Teacher;
import com.avin.schoolportal.dto.PersonDTO;
import com.avin.schoolportal.dto.TeacherDTO;
import com.avin.schoolportal.repository.SchoolUserRepository;
import com.avin.schoolportal.repository.TeacherRepository;
import com.avin.schoolportal.service.ManagerService;
import com.avin.schoolportal.service.SchoolUserService;
import com.avin.schoolportal.service.UserService;
import com.avin.schoolportal.specification.TeacherSpecification;
import com.avin.schoolportal.validationgroups.EmployeeRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

/**
 * Created by Yubar on 11/3/2016.
 */

@RestController
@RequestMapping("/teachers")
public class TeacherRestService {
    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    SchoolUserRepository schoolUserRepository;

    @Autowired
    ManagerService managerService;

    @Autowired
    UserService userService;

    @Autowired
    SchoolUserService schoolUserService;

    @PreAuthorize("hasAuthority('MANAGER')")
    @RequestMapping(method = RequestMethod.GET)
    public Page<TeacherDTO> getTeachers(@RequestParam Map<String, String> params, Pageable pageable, Principal principal) {
        SchoolUser user = schoolUserRepository.findByUsername(principal.getName());
        Page<Teacher> teachers = teacherRepository.findAll(new TeacherSpecification(params, user.getSchool()), pageable);
        return teachers.map(e -> {
            TeacherDTO teacherDTO = new TeacherDTO(e);
            teacherDTO.setPerson(new PersonDTO(e.getPerson()));
            return teacherDTO;
        });
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public TeacherDTO getTeacher(Principal principal) {
        Teacher teacher = teacherRepository.findByUsername(principal.getName());
        TeacherDTO teacherDTO = new TeacherDTO(teacher);
        teacherDTO.setPerson(new PersonDTO(teacher.getPerson()));
        return teacherDTO;
    }

    @PreAuthorize("hasPermission(#id, 'Teacher', 'READ')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public TeacherDTO getTeacher(@PathVariable long id) {
        Teacher teacher = teacherRepository.findOne(id);
        TeacherDTO teacherDTO = new TeacherDTO(teacher);
        teacherDTO.setPerson(new PersonDTO(teacher.getPerson()));
        return teacherDTO;
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @RequestMapping(method = RequestMethod.POST)
    public TeacherDTO registerTeacher(@Validated(EmployeeRegistration.class) @RequestBody TeacherDTO teacherDTO, Principal principal) {
        Teacher teacher = teacherDTO.convert();
        SchoolUser user = schoolUserRepository.findByUsername(principal.getName());
        teacher.setSchool(user.getSchool());
        teacher = (Teacher) managerService.registerEmployee(teacher);
        String newPassword = userService.resetPassword(teacher.getUsername());
        schoolUserService.sendRegistrationEmail(teacher, newPassword);
        TeacherDTO ret = new TeacherDTO(teacher);
        ret.setPerson(new PersonDTO(teacher.getPerson()));
        return ret;
    }

    @PreAuthorize("hasPermission(#teacherDTO.id, 'Teacher', 'UPDATE')")
    @RequestMapping(method = RequestMethod.PUT)
    public TeacherDTO updateTeacher(@Validated(EmployeeRegistration.class) @RequestBody TeacherDTO teacherDTO) {
        Teacher teacher = teacherDTO.convert();
        teacher = (Teacher) managerService.updateEmployee(teacher);
        TeacherDTO ret = new TeacherDTO(teacher);
        ret.setPerson(new PersonDTO(teacher.getPerson()));
        return ret;
    }

    @PreAuthorize("hasPermission(#id, 'Teacher', 'DELETE')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteTeacher(@PathVariable long id) {
        Teacher teacher = teacherRepository.findOne(id);
        managerService.deleteEmployee(teacher);
    }
}
