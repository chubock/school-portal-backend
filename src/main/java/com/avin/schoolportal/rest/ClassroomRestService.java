package com.avin.schoolportal.rest;

import com.avin.schoolportal.domain.*;
import com.avin.schoolportal.dto.*;
import com.avin.schoolportal.repository.*;
import com.avin.schoolportal.service.MancipleService;
import com.avin.schoolportal.specification.ClassroomSpecification;
import com.avin.schoolportal.validationgroups.ClassroomRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    StudyRepository studyRepository;

    @Autowired
    ClassTimeRepository classTimeRepository;

    @PreAuthorize("isAuthenticated()")
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
        classroomDTO.setCourse(new CourseDTO(classroom.getCourse()));

        List<ClassTime> classTimes = classTimeRepository.findByClassroom(classroom, new Sort("weekDay", "from"));
        for (ClassTime classTime : classTimes) {
            ClassTimeDTO classTimeDTO = new ClassTimeDTO(classTime);
            classTimeDTO.setStudy(new StudyDTO(classTime.getStudy()));
            classTimeDTO.setTeacher(new TeacherDTO(classTime.getTeacher()));
            classroomDTO.getClassTimes().add(classTimeDTO);
        }

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

    @RequestMapping(value = "/{id}/students")
    public List<StudentDTO> getStudents(@PathVariable long id) {
        Classroom classroom = classroomRepository.findOne(id);
        return classroom.getStudents().stream().map(StudentDTO::new).collect(Collectors.toList());
    }

}
