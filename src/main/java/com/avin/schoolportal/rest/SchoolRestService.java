package com.avin.schoolportal.rest;

import com.avin.schoolportal.domain.Course;
import com.avin.schoolportal.domain.User;
import com.avin.schoolportal.dto.CourseDTO;
import com.avin.schoolportal.repository.UserRepository;
import com.avin.schoolportal.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Yubar on 10/28/2016.
 */

@RestController
@RequestMapping("/school")
public class SchoolRestService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ManagerService managerService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public List<CourseDTO> getSchoolCourses(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        List<CourseDTO> courseDTOs = new ArrayList<>();
        user.getSchool().getCourses().forEach(course -> courseDTOs.add(new CourseDTO(course)));
        return courseDTOs;
    }


    @PreAuthorize("hasAuthority('MANAGER')")
    @RequestMapping(value = "/courses", method = RequestMethod.PUT)
    public List<CourseDTO> updateSchoolCourses(@RequestBody List<CourseDTO> courseDTOs, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        List<Course> courses = courseDTOs.stream().map(courseDTO -> courseDTO.convert()).collect(Collectors.toList());
        managerService.updateSchoolCourses(user.getSchool(), courses);
        courseDTOs.clear();
        user.getSchool().getCourses().forEach(course -> courseDTOs.add(new CourseDTO(course)));
        return courseDTOs;
    }
}
