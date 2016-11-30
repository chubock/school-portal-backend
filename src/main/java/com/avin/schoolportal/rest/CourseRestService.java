package com.avin.schoolportal.rest;

import com.avin.schoolportal.domain.Course;
import com.avin.schoolportal.dto.CourseDTO;
import com.avin.schoolportal.dto.StudyDTO;
import com.avin.schoolportal.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Yubar on 10/21/2016.
 */

@RestController
@RequestMapping("/courses")
public class CourseRestService {

    @Autowired
    CourseRepository courseRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<CourseDTO> getCoursesTree() {
        List<CourseDTO> courseDTOs = new ArrayList<>();
        Iterable<Course> courseGroups = courseRepository.findAll();

        courseGroups.forEach(courseGroup -> {
            if (courseGroup.getParent() == null)
                courseDTOs.add(new CourseDTO(courseGroup));
        });

        Consumer<CourseDTO> fillChildren = new Consumer<CourseDTO>() {
            @Override
            public void accept(CourseDTO courseGroupDTO) {
                for (Course courseGroup : courseGroups) {
                    if (courseGroup.getParent() != null && courseGroup.getParent().getId() == courseGroupDTO.getId()) {
                        courseGroupDTO.getChildren().add(new CourseDTO(courseGroup));
                    }
                }
                courseGroupDTO.getChildren().forEach(this);
            }
        };

        courseDTOs.forEach(fillChildren);

        return courseDTOs;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CourseDTO getCourse(@PathVariable long id) {
        Course course = courseRepository.findOneWithStudies(id);
        CourseDTO courseDTO = new CourseDTO(course);
        course.getStudies().forEach(study -> {
            courseDTO.getStudies().add(new StudyDTO(study));
        });
        return courseDTO;
    }
}
