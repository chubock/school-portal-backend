package com.avin.schoolportal.rest;

import com.avin.schoolportal.domain.Book;
import com.avin.schoolportal.domain.Course;
import com.avin.schoolportal.domain.File;
import com.avin.schoolportal.dto.CourseDTO;
import com.avin.schoolportal.dto.StudyDTO;
import com.avin.schoolportal.repository.BookRepository;
import com.avin.schoolportal.repository.CourseRepository;
import com.avin.schoolportal.repository.FileRepository;
import com.avin.schoolportal.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

    @Autowired
    BookRepository bookRepository;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    ManagerService managerService;

    @Autowired
    FileRenderer fileRenderer;

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

    @RequestMapping(value = "/fetchImages", method = RequestMethod.GET)
    public void fetchBooksImages() {
        managerService.fetchBooksImages();
    }

    @RequestMapping(value = "/bookImage/{id}", method = RequestMethod.GET)
    public void bookImage(@PathVariable long id, HttpServletResponse response) throws IOException {
        Book book = bookRepository.findOne(id);
        fileRenderer.renderFile(book == null ? null : book.getCover(), response, "inline");
    }
}
