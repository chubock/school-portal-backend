package com.avin.schoolportal.rest;

import com.avin.schoolportal.domain.*;
import com.avin.schoolportal.dto.*;
import com.avin.schoolportal.repository.ExerciseRepository;
import com.avin.schoolportal.repository.TeacherRepository;
import com.avin.schoolportal.repository.UserRepository;
import com.avin.schoolportal.service.TeacherService;
import com.avin.schoolportal.specification.ExerciseSpecification;
import com.avin.schoolportal.validationgroups.ExerciseRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Yubar on 12/27/2016.
 */

@RestController
@RequestMapping("/exercises")
public class ExerciseRestService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    TeacherService teacherService;

    @Autowired
    FileRenderer fileRenderer;

    @RequestMapping(method = RequestMethod.GET)
    public Page<ExerciseDTO> getExercises(@RequestParam Map<String, String> params, Pageable pageable, Principal principal){
        User user = userRepository.findByUsername(principal.getName());
        if (user instanceof Teacher)
            return getTeacherExams(params, (Teacher) user, pageable);
        return null;
    }

    @PreAuthorize("hasPermission(#id, 'Exercise', 'READ')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ExerciseDTO getExercise(@PathVariable long id) {
        Exercise exercise = exerciseRepository.findOneCompletely(id);
        ExerciseDTO exerciseDTO = new ExerciseDTO(exercise);
        exerciseDTO.setStudy(new StudyDTO(exercise.getStudy()));
        exerciseDTO.setCourse(new CourseDTO(exercise.getCourse()));
        exerciseDTO.setQuestions(new FileDTO(exercise.getQuestions()));
        exerciseDTO.setAnswers(new FileDTO(exercise.getAnswers()));
        exercise.getClassrooms().forEach(classroom -> exerciseDTO.getClassrooms().add(new ClassroomDTO(classroom)));
        return exerciseDTO;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ExerciseDTO registerExercise(@Validated(ExerciseRegistration.class) @RequestBody ExerciseDTO exerciseDTO, Principal principal) {
        Teacher user = teacherRepository.findByUsername(principal.getName());
        Exercise exercise = exerciseDTO.convert();
        exercise.setSchool(user.getSchool());
        exercise.setTeacher(user);
        exerciseDTO = new ExerciseDTO(teacherService.registerExercise(exercise));
        return exerciseDTO;
    }

    @PreAuthorize("hasPermission(#exerciseDTO.id, 'Exercise', 'UPDATE')")
    @RequestMapping(method = RequestMethod.PUT)
    public ExerciseDTO updateExercise(@RequestBody ExerciseDTO exerciseDTO) {
        Exercise exercise = exerciseDTO.convert();
        return new ExerciseDTO(teacherService.updateExercise(exercise));
    }

    @PreAuthorize("hasPermission(#id, 'Exercise', 'DELETE')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeExercise(@PathVariable long id) {
        Exercise exercise = new Exercise();
        exercise.setId(id);
        teacherService.removeExercise(exercise);
    }

    @PreAuthorize("hasPermission(#id, 'Exercise', 'READ')")
    @RequestMapping(value = "/{id}/questionsFile", method = RequestMethod.GET)
    public void getQuestionFile(@PathVariable long id, HttpServletResponse response) throws IOException {
        Exercise exercise = exerciseRepository.findOne(id);
        fileRenderer.renderFile(exercise == null ? null : exercise.getQuestions(), response);
    }

    @PreAuthorize("hasPermission(#id, 'Exercise', 'UPDATE')")
    @RequestMapping(value = "/{id}/questionsFile", method = RequestMethod.POST)
    public FileDTO uploadQuestionsFile(MultipartHttpServletRequest request, @PathVariable long id, Principal principal) throws IOException {
        Teacher teacher = teacherRepository.findByUsername(principal.getName());
        MultipartFile file = request.getFile("file");
        File f = new File(file);
        f.setSchool(teacher.getSchool());
        teacherService.updateExerciseQuestionsFile(id, f);
        return new FileDTO(f);
    }

    @PreAuthorize("hasPermission(#id, 'Exercise', 'READ')")
    @RequestMapping(value = "/{id}/answersFile", method = RequestMethod.GET)
    public void getAnswersFile(@PathVariable long id, HttpServletResponse response) throws IOException {
        Exercise exercise = exerciseRepository.findOne(id);
        fileRenderer.renderFile(exercise == null ? null : exercise.getAnswers(), response);
    }

    @PreAuthorize("hasPermission(#id, 'Exercise', 'UPDATE')")
    @RequestMapping(value = "/{id}/answersFile", method = RequestMethod.POST)
    public FileDTO uploadAnswersFile(MultipartHttpServletRequest request, @PathVariable long id, Principal principal) throws IOException {
        Teacher teacher = teacherRepository.findByUsername(principal.getName());
        MultipartFile file = request.getFile("file");
        File f = new File(file);
        f.setSchool(teacher.getSchool());
        teacherService.updateExerciseAnswersFile(id, f);
        return new FileDTO(f);
    }


    @PreAuthorize("hasPermission(#id, 'Exercise', 'UPDATE')")
    @RequestMapping(value = "/{id}/wrongdoers", method = RequestMethod.GET)
    public List<StudentDTO> getWrongdoers(@PathVariable long id) {
        return exerciseRepository.findWrongdoers(id).stream().map(StudentDTO::new).collect(Collectors.toList());
    }

    private Page<ExerciseDTO> getTeacherExams(Map<String, String> params, Teacher teacher, Pageable pageable) {
        Page<Exercise> exercises = exerciseRepository.findAll(new ExerciseSpecification(params, teacher), pageable);
        return exercises.map(exercise -> {
            ExerciseDTO exerciseDTO = new ExerciseDTO(exercise);
            exerciseDTO.setCourse(new CourseDTO(exercise.getCourse()));
            exerciseDTO.setStudy(new StudyDTO(exercise.getStudy()));
            return exerciseDTO;
        });
    }

}
