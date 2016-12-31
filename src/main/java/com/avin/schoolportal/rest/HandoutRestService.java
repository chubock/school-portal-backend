package com.avin.schoolportal.rest;

import com.avin.schoolportal.domain.File;
import com.avin.schoolportal.domain.Handout;
import com.avin.schoolportal.domain.Teacher;
import com.avin.schoolportal.domain.User;
import com.avin.schoolportal.dto.*;
import com.avin.schoolportal.repository.HandoutRepository;
import com.avin.schoolportal.repository.TeacherRepository;
import com.avin.schoolportal.repository.UserRepository;
import com.avin.schoolportal.service.TeacherService;
import com.avin.schoolportal.specification.HandoutSpecification;
import com.avin.schoolportal.validationgroups.HandoutRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

/**
 * Created by Yubar on 12/30/2016.
 */

@RestController
@RequestMapping("/handouts")
public class HandoutRestService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    HandoutRepository handoutRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    TeacherService teacherService;

    @Autowired
    FileRenderer fileRenderer;

    @RequestMapping(method = RequestMethod.GET)
    public Page<HandoutDTO> getExercises(@RequestParam Map<String, String> params, Pageable pageable, Principal principal){
        User user = userRepository.findByUsername(principal.getName());
        if (user instanceof Teacher)
            return getTeacherHandouts(params, (Teacher) user, pageable);
        return null;
    }

    @PreAuthorize("hasPermission(#id, 'Handout', 'READ')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HandoutDTO getHandout(@PathVariable long id) {
        Handout handout = handoutRepository.findOneCompletely(id);
        HandoutDTO handoutDTO = new HandoutDTO(handout);
        handoutDTO.setStudy(new StudyDTO(handout.getStudy()));
        handoutDTO.setCourse(new CourseDTO(handout.getCourse()));
        handoutDTO.setFile(new FileDTO(handout.getFile()));
        handoutDTO.setTeacher(new TeacherDTO(handout.getTeacher()));
        return handoutDTO;
    }

    @RequestMapping(method = RequestMethod.POST)
    public HandoutDTO registerHandout(@Validated(HandoutRegistration.class) @RequestBody HandoutDTO handoutDTO, Principal principal) {
        Teacher user = teacherRepository.findByUsername(principal.getName());
        Handout handout = handoutDTO.convert();
        handout.setSchool(user.getSchool());
        handout.setTeacher(user);
        handoutDTO = new HandoutDTO(teacherService.registerHandout(handout));
        return handoutDTO;
    }

    @PreAuthorize("hasPermission(#handoutDTO.id, 'Handout', 'UPDATE')")
    @RequestMapping(method = RequestMethod.PUT)
    public HandoutDTO updateHandout(@RequestBody HandoutDTO handoutDTO) {
        Handout handout = handoutDTO.convert();
        return new HandoutDTO(teacherService.updateHandout(handout));
    }

    @PreAuthorize("hasPermission(#id, 'Handout', 'DELETE')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeHandout(@PathVariable long id) {
        Handout handout = new Handout();
        handout.setId(id);
        teacherService.removeHandout(handout);
    }

    @PreAuthorize("hasPermission(#id, 'Handout', 'READ')")
    @RequestMapping(value = "/{id}/file", method = RequestMethod.GET)
    public void getFile(@PathVariable long id, HttpServletResponse response) throws IOException {
        Handout handout = handoutRepository.findOne(id);
        fileRenderer.renderFile(handout == null ? null : handout.getFile(), response);
    }

    @PreAuthorize("hasPermission(#id, 'Handout', 'UPDATE')")
    @RequestMapping(value = "/{id}/file", method = RequestMethod.POST)
    public FileDTO uploadFile(MultipartHttpServletRequest request, @PathVariable long id, Principal principal) throws IOException {
        Teacher teacher = teacherRepository.findByUsername(principal.getName());
        MultipartFile file = request.getFile("file");
        File f = new File(file);
        f.setSchool(teacher.getSchool());
        teacherService.updateHandoutFile(id, f);
        return new FileDTO(f);
    }

    private Page<HandoutDTO> getTeacherHandouts(Map<String, String> params, Teacher teacher, Pageable pageable) {
        return handoutRepository.findAll(new HandoutSpecification(params, teacher), pageable).map(handout -> {
            HandoutDTO handoutDTO = new HandoutDTO(handout);
            handoutDTO.setCourse(new CourseDTO(handout.getCourse()));
            handoutDTO.setStudy(new StudyDTO(handout.getStudy()));
            return handoutDTO;
        });
    }
}
