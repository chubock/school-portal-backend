package com.avin.schoolportal.rest;

import com.avin.schoolportal.domain.*;
import com.avin.schoolportal.dto.*;
import com.avin.schoolportal.repository.ClassTimeRepository;
import com.avin.schoolportal.repository.ClassroomRepository;
import com.avin.schoolportal.repository.SchoolUserRepository;
import com.avin.schoolportal.repository.TeacherRepository;
import com.avin.schoolportal.service.MancipleService;
import com.avin.schoolportal.service.TeacherService;
import com.avin.schoolportal.specification.ClassTimeSpecification;
import com.avin.schoolportal.validationgroups.ExamRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Yubar on 12/2/2016.
 */

@RestController
@RequestMapping("/classTimes")
public class ClassTimeRestService {

    @Autowired
    SchoolUserRepository schoolUserRepository;

    @Autowired
    ClassTimeRepository classTimeRepository;

    @Autowired
    ClassroomRepository classroomRepository;

    @Autowired
    MancipleService mancipleService;

    @Autowired
    TeacherService teacherService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public Page<ClassTimeDTO> getClassTimes(@RequestParam Map<String, String> params, Pageable pageable, Principal principal) {
        SchoolUser schoolUser = schoolUserRepository.findByUsername(principal.getName());
        Page<ClassTime> classTimes = null;
        if (schoolUser instanceof Teacher)
            return getClassTimes(params, pageable, (Teacher) schoolUser);
        return null;
    }

    @PreAuthorize("hasPermission(#classTimeDTO.classroom.id, 'Classroom', 'UPDATE') and hasPermission(#classTimeDTO.teacher.id, 'Teacher', 'READ')")
    @RequestMapping(method = RequestMethod.POST)
    public ClassTimeDTO addClassTime(@RequestBody ClassTimeDTO classTimeDTO) {
        ClassTime classTime = classTimeDTO.convert();
        classTimeDTO = new ClassTimeDTO(mancipleService.addClassTime(classTime));
        classTimeDTO.setTeacher(new TeacherDTO(classTime.getTeacher()));
        classTimeDTO.setStudy(new StudyDTO(classTime.getStudy()));
        classTimeDTO.setClassroom(new ClassroomDTO(classTime.getClassroom()));
        return classTimeDTO;
    }

    @PreAuthorize("hasPermission(#id, 'ClassTime', 'DELETE')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeClassTime(@PathVariable long id) {
        ClassTime classTime = classTimeRepository.findOne(id);
        mancipleService.removeClassTime(classTime);
    }

    @RequestMapping(value = "/availableTeachers", method = RequestMethod.GET)
    public List<TeacherDTO> getAvailableTeachersFor(@RequestParam double from, @RequestParam double to, @RequestParam WeekDay weekDay) {
        return classTimeRepository.findTeacherAvailableFor(from, to, weekDay).stream().map(teacher -> {
            TeacherDTO teacherDTO = new TeacherDTO(teacher);
            if (teacher.getPictureFile() != null) {
                teacherDTO.setPictureFile(new FileDTO());
                teacherDTO.getPictureFile().setId(teacher.getPictureFile().getId());
            }
            return teacherDTO;
        }).collect(Collectors.toList());
    }

    private Page<ClassTimeDTO> getClassTimes(@RequestParam Map<String, String> params, Pageable pageable, Teacher teacher) {
        Page<ClassTime> classTimes = classTimeRepository.findAll(new ClassTimeSpecification(params, teacher), pageable);
        return classTimes.map(classTime -> {
            ClassTimeDTO classTimeDTO = new ClassTimeDTO(classTime);
            classTimeDTO.setStudy(new StudyDTO(classTime.getStudy()));
            classTimeDTO.setClassroom(new ClassroomDTO(classTime.getClassroom()));
            classTimeDTO.getClassroom().setCourse(new CourseDTO(classTime.getClassroom().getCourse()));
            return classTimeDTO;
        });
    }
}
