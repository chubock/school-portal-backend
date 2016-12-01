package com.avin.schoolportal.rest;

import com.avin.schoolportal.domain.Classroom;
import com.avin.schoolportal.domain.SchoolUser;
import com.avin.schoolportal.domain.User;
import com.avin.schoolportal.dto.*;
import com.avin.schoolportal.repository.ClassroomRepository;
import com.avin.schoolportal.repository.SchoolUserRepository;
import com.avin.schoolportal.repository.UserRepository;
import com.avin.schoolportal.service.MancipleService;
import com.avin.schoolportal.specification.ClassroomSpecification;
import com.avin.schoolportal.validationgroups.ClassroomRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

/**
 * Created by Yubar on 11/24/2016.
 */

@RestController
@RequestMapping("/classrooms")
public class ClassroomRestService {

    @Autowired
    ClassroomRepository classroomRepository;

    @Autowired
    MancipleService mancipleService;

    @Autowired
    SchoolUserRepository schoolUserRepository;

    @PreAuthorize("hasAuthority('MANCIPLE')")
    @RequestMapping(method = RequestMethod.GET)
    public Page<ClassroomDTO> getClassrooms(@RequestParam Map<String, String> params, Pageable pageable, Principal principal) {

        SchoolUser user = schoolUserRepository.findByUsername(principal.getName());
        Page<Classroom> classrooms = classroomRepository.findAll(new ClassroomSpecification(params, user.getSchool()), pageable);

        return classrooms.map(classroom -> {
            ClassroomDTO classroomDTO = new ClassroomDTO(classroom);
            classroomDTO.setCourse(new CourseDTO(classroom.getCourse()));
            return classroomDTO;
        });

    }

    @PreAuthorize("hasPermission(#id, 'Classroom', 'READ')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ClassroomDTO getClassroom(@PathVariable long id) {

        Classroom classroom = classroomRepository.findOne(id);
        ClassroomDTO classroomDTO = new ClassroomDTO(classroom);

        classroom.getClassTimes().forEach(classTime -> {
            ClassTimeDTO classTimeDTO = new ClassTimeDTO(classTime);
            classTimeDTO.setStudy(new StudyDTO(classTime.getStudy()));
            classTimeDTO.setTeacher(new TeacherDTO(classTime.getTeacher()));
            classroomDTO.getClassTimes().add(classTimeDTO);
        });

        classroom.getStudents().forEach(student -> {
            classroomDTO.getStudents().add(new StudentDTO(student));
        });

        return classroomDTO;
    }

    @PreAuthorize("hasAuthority('MANCIPLE')")
    @RequestMapping(method = RequestMethod.POST)
    public ClassroomDTO registerClassroom(@Validated(ClassroomRegistration.class) @RequestBody ClassroomDTO classroomDTO, Principal principal) {
        SchoolUser user = schoolUserRepository.findByUsername(principal.getName());
        Classroom classroom = classroomDTO.convert();
        classroom.setSchool(user.getSchool());
        classroom = mancipleService.registerClassroom(classroom);
        ClassroomDTO ret = new ClassroomDTO(classroom);
        ret.setCourse(new CourseDTO(classroom.getCourse()));
        return ret;
    }

    @PreAuthorize("hasPermission(#classroomDTO.id, 'Classroom', 'UPDATE')")
    @RequestMapping(method = RequestMethod.PUT)
    public ClassroomDTO updateClassroom(@RequestBody ClassroomDTO classroomDTO) {
        Classroom classroom = classroomDTO.convert();
        classroom = mancipleService.updateClassroom(classroom);
        ClassroomDTO ret = new ClassroomDTO(classroom);
        ret.setCourse(new CourseDTO(classroom.getCourse()));
        return ret;
    }

    @PreAuthorize("hasPermission(#id, 'Classroom', 'DELETE')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteClassroom(@PathVariable long id) {
        Classroom classroom = classroomRepository.findOne(id);
        mancipleService.deleteClassroom(classroom);
    }


}
