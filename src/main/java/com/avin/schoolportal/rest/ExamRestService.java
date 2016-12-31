package com.avin.schoolportal.rest;

import com.avin.schoolportal.domain.*;
import com.avin.schoolportal.dto.*;
import com.avin.schoolportal.repository.*;
import com.avin.schoolportal.service.TeacherService;
import com.avin.schoolportal.specification.ExamSpecification;
import com.avin.schoolportal.validationgroups.ExamRegistration;
import com.avin.schoolportal.validationgroups.ExamScoreRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Yubar on 12/11/2016.
 */

@RestController
@RequestMapping("/exams")
public class ExamRestService {

    @Autowired
    ExamRepository examRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SchoolUserRepository schoolUserRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    TeacherService teacherService;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ExamScoreRepository examScoreRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Page<ExamDTO> getExams(@RequestParam Map<String, String> params, Principal principal, Pageable pageable) {
        User user = userRepository.findByUsername(principal.getName());
        if (user instanceof Teacher)
            return getTeacherExams(params, (Teacher) user, pageable);
        return null;
    }

    @PreAuthorize("hasPermission(#id, 'Exam', 'READ')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ExamDTO getExam(@PathVariable long id) {
        Exam exam = examRepository.findOneCompletely(id);
        ExamDTO examDTO = new ExamDTO(exam);
        examDTO.setStudy(new StudyDTO(exam.getStudy()));
        examDTO.setCourse(new CourseDTO(exam.getCourse()));
        if (exam.getQuestions() != null)
            examDTO.setQuestions(new FileDTO(exam.getQuestions()));
        if (exam.getAnswers() != null)
            examDTO.setAnswers(new FileDTO(exam.getAnswers()));
        exam.getClassrooms().forEach(classroom -> examDTO.getClassrooms().add(new ClassroomDTO(classroom)));
        return examDTO;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ExamDTO registerExam(@Validated(ExamRegistration.class) @RequestBody ExamDTO examDTO, Principal principal) {
        Teacher user = teacherRepository.findByUsername(principal.getName());
        Exam exam = examDTO.convert();
        exam.setSchool(user.getSchool());
        exam.setTeacher(user);
        examDTO = new ExamDTO(teacherService.registerExam(exam));
        return examDTO;
    }

    @PreAuthorize("hasPermission(#examDTO.id, 'Exam', 'UPDATE')")
    @RequestMapping(method = RequestMethod.PUT)
    public ExamDTO updateExam(@RequestBody ExamDTO examDTO) {
        Exam exam = examDTO.convert();
        exam.setQuestions(null);
        exam.setAnswers(null);
        return new ExamDTO(teacherService.updateExam(exam));
    }

    @PreAuthorize("hasPermission(#id, 'Exam', 'DELETE')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeExam(@PathVariable long id) {
        Exam exam = new Exam();
        exam.setId(id);
        teacherService.removeExam(exam);
    }

    @PreAuthorize("hasPermission(#id, 'Exam', 'READ')")
    @RequestMapping(value = "/{id}/questionsFile", method = RequestMethod.GET)
    public void getQuestionFile(@PathVariable long id, HttpServletResponse response) throws IOException {
        Exam exam = examRepository.findOne(id);
        if (exam.getQuestions() == null)
            response.sendError(404);
        else {
            response.setContentType(exam.getQuestions().getContentType());
            response.setContentLengthLong(exam.getQuestions().getSize());
            response.setHeader("Content-Disposition", "attachment; filename=" + exam.getQuestions().getName());
            response.getOutputStream().write(exam.getQuestions().getContent());
        }
    }

    @PreAuthorize("hasPermission(#id, 'Exam', 'UPDATE')")
    @RequestMapping(value = "/{id}/questionsFile", method = RequestMethod.POST)
    public FileDTO uploadQuestionsFile(MultipartHttpServletRequest request, @PathVariable long id, Principal principal) throws IOException {
        SchoolUser user = schoolUserRepository.findByUsername(principal.getName());
        MultipartFile file = request.getFile("file");
        File f = new File(file);
        f.setSchool(user.getSchool());
        teacherService.updateQuestionsFile(id, f);
        return new FileDTO(f);
    }

    @PreAuthorize("hasPermission(#id, 'Exam', 'READ')")
    @RequestMapping(value = "/{id}/answersFile", method = RequestMethod.GET)
    public void getAnswersFile(@PathVariable long id, HttpServletResponse response) throws IOException {
        Exam exam = examRepository.findOne(id);
        if (exam.getAnswers() == null)
            response.sendError(404);
        else {
            response.setContentType(exam.getAnswers().getContentType());
            response.setContentLengthLong(exam.getAnswers().getSize());
            response.setHeader("Content-Disposition", "attachment; filename=" + exam.getAnswers().getName());
            response.getOutputStream().write(exam.getAnswers().getContent());
        }
    }

    @PreAuthorize("hasPermission(#id, 'Exam', 'UPDATE')")
    @RequestMapping(value = "/{id}/answersFile", method = RequestMethod.POST)
    public FileDTO uploadAnswersFile(MultipartHttpServletRequest request, @PathVariable long id, Principal principal) throws IOException {
        SchoolUser user = schoolUserRepository.findByUsername(principal.getName());
        MultipartFile file = request.getFile("file");
        File f = new File(file);
        f.setSchool(user.getSchool());
        teacherService.updateAnswersFile(id, f);
        return new FileDTO(f);
    }


    @PreAuthorize("hasPermission(#id, 'Exam', 'UPDATE')")
    @RequestMapping(value = "/{id}/scores", method = RequestMethod.GET)
    public List<ExamScoreDTO> getScores(@PathVariable long id) {
        Exam exam = examRepository.getOne(id);
        return exam.getScores().stream().map(examScore -> {
            ExamScoreDTO examScoreDTO = new ExamScoreDTO(examScore);
            examScoreDTO.setStudent(new StudentDTO());
            examScoreDTO.getStudent().setId(examScore.getStudent().getId());
            return examScoreDTO;
        }).collect(Collectors.toList());
    }

    @PreAuthorize("hasPermission(#id, 'Exam', 'UPDATE')")
    @RequestMapping(value = "/{id}/student/{studentId}", method = RequestMethod.PUT)
    public ExamScoreDTO registerExamScore(@PathVariable long id, @PathVariable long studentId, @RequestBody double score) {
        Exam exam = examRepository.getOne(id);
        Student student = studentRepository.getOne(studentId);
        ExamScore examScore = examScoreRepository.findByExamAndStudent(exam, student);
        if (examScore == null)
            examScore = new ExamScore(exam, student, score);
        else
            examScore.setScore(score);
        ExamScoreDTO examScoreDTO = new ExamScoreDTO(teacherService.saveScore(examScore));
        examScoreDTO.setStudent(new StudentDTO());
        examScoreDTO.getStudent().setId(student.getId());
        return examScoreDTO;
    }

    @PreAuthorize("hasPermission(#id, 'Exam', 'UPDATE')")
    @RequestMapping(value = "/{id}/student/{studentId}", method = RequestMethod.DELETE)
    public void deleteExamScore(@PathVariable long id, @PathVariable long studentId){
        Exam exam = examRepository.getOne(id);
        Student student = studentRepository.getOne(studentId);
        ExamScore examScore = examScoreRepository.findByExamAndStudent(exam, student);
        teacherService.deleteScore(examScore);
    }

    private Page<ExamDTO> getTeacherExams(Map<String, String> params, Teacher teacher, Pageable pageable) {
        Page<Exam> exams = examRepository.findAll(new ExamSpecification(params, teacher), pageable);
        return exams.map(exam -> {
            ExamDTO examDTO = new ExamDTO(exam);
            examDTO.setCourse(new CourseDTO(exam.getCourse()));
            examDTO.setStudy(new StudyDTO(exam.getStudy()));
            return examDTO;
        });
    }

}
