package com.avin.schoolportal.rest;

import com.avin.schoolportal.domain.*;
import com.avin.schoolportal.dto.*;
import com.avin.schoolportal.repository.MancipleRepository;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
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

    @Autowired
    MancipleRepository mancipleRepository;

    @Autowired
    FileRenderer fileRenderer;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/queryUsername", method = RequestMethod.GET)
    public Page<StudentDTO> queryByUsername(@RequestParam String key, Principal principal, Pageable pageable) {
        SchoolUser user = schoolUserRepository.findByUsername(principal.getName());
        Page<Student> students = studentRepository.findByUsernameLikeAndSchool("%" + key + "%", user.getSchool(), pageable);
        return students.map(student ->{
            StudentDTO studentDTO = new StudentDTO(student);
            if (student.getPictureFile() != null) {
                studentDTO.setPictureFile(new FileDTO());
                studentDTO.getPictureFile().setId(student.getPictureFile().getId());
            }
            return studentDTO;
        });
    }

    @PreAuthorize("hasAuthority('MANCIPLE')")
    @RequestMapping(method = RequestMethod.GET)
    public Page<StudentDTO> getStudents(@RequestParam Map<String, String> params, Pageable pageable, Principal principal) {
        SchoolUser user = schoolUserRepository.findByUsername(principal.getName());
        Page<Student> students = studentRepository.findAll(new StudentSpecification(params, user.getSchool()), pageable);
        return students.map(student -> {
            StudentDTO studentDTO = new StudentDTO(student);
            studentDTO.setCourse(new CourseDTO(student.getCourse()));
            studentDTO.setClassroom(new ClassroomDTO(student.getClassroom()));
            if (student.getPictureFile() != null) {
                studentDTO.setPictureFile(new FileDTO());
                studentDTO.getPictureFile().setId(student.getPictureFile().getId());
            }
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
        if (student.getPictureFile() != null) {
            studentDTO.setPictureFile(new FileDTO());
            studentDTO.getPictureFile().setId(student.getPictureFile().getId());
        }
        return studentDTO;
    }

    @PreAuthorize("hasPermission(#id, 'Student', 'READ')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public StudentDTO getStudent(@PathVariable long id, Principal principal) {
        Student student = studentRepository.findOne(id);
        StudentDTO studentDTO = new StudentDTO(student);
        studentDTO.setCourse(new CourseDTO(student.getCourse()));
        studentDTO.setClassroom(new ClassroomDTO(student.getClassroom()));
        if (student.getClassroom() != null)
            studentDTO.getClassroom().setCourse(new CourseDTO(student.getClassroom().getCourse()));
        studentDTO.setParent(new ParentDTO(student.getParent()));
        if (student.getPictureFile() != null) {
            studentDTO.setPictureFile(new FileDTO());
            studentDTO.getPictureFile().setId(student.getPictureFile().getId());
        }
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
        ret.setParent(new ParentDTO(student.getParent()));
        ret.setCourse(new CourseDTO(student.getCourse()));
        ret.setClassroom(new ClassroomDTO(student.getClassroom()));
        if (student.getClassroom() != null)
            ret.getClassroom().setCourse(new CourseDTO(student.getClassroom().getCourse()));
        return ret;
    }

    @PreAuthorize("hasPermission(#studentDTO.id, 'Student', 'UPDATE')")
    @RequestMapping(method = RequestMethod.PUT)
    public StudentDTO updateStudent(@RequestBody StudentDTO studentDTO) {
        Student student = studentDTO.convert();
        student = mancipleService.updateStudent(student);
        StudentDTO ret = new StudentDTO(student);
        ret.setParent(new ParentDTO(student.getParent()));
        ret.setCourse(new CourseDTO(student.getCourse()));
        ret.setClassroom(new ClassroomDTO(student.getClassroom()));
        if (student.getClassroom() != null)
            ret.getClassroom().setCourse(new CourseDTO(student.getClassroom().getCourse()));
        return ret;
    }

    @PreAuthorize("hasPermission(#id, 'Student', 'DELETE')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteStudent(@PathVariable long id) {
        Student student = studentRepository.findOne(id);
        mancipleService.deleteStudent(student);
    }

    @PreAuthorize("hasPermission(#id, 'Student', 'READ')")
    @RequestMapping(value = {"/{id}/pictureFile", "/{id}/pictureFile/*"}, method = RequestMethod.GET)
    public void getQuestionFile(@PathVariable long id, HttpServletResponse response) throws IOException {
        Student student = studentRepository.findOne(id);
        fileRenderer.renderFile(student == null ? null : student.getPictureFile(), response);
    }

    @PreAuthorize("hasPermission(#id, 'Student', 'UPDATE')")
    @RequestMapping(value = {"/{id}/pictureFile"}, method = RequestMethod.POST)
    public FileDTO uploadPictureFile(MultipartHttpServletRequest request, @PathVariable long id, Principal principal) throws IOException {
        Manciple manciple = mancipleRepository.findByUsername(principal.getName());
        Student student = studentRepository.getOne(id);
        MultipartFile file = request.getFile("file");
        File f = new File(file);
        f.setSchool(manciple.getSchool());
        student.setPictureFile(f);
        schoolUserService.updateSchoolUserPictureFile(student);
        return new FileDTO(f);
    }

}
