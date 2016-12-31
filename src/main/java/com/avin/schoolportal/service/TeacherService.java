package com.avin.schoolportal.service;

import com.avin.schoolportal.domain.*;
import com.avin.schoolportal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Yubar on 12/11/2016.
 */

@Service
@Transactional
public class TeacherService {

    @Autowired
    ExamRepository examRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    StudyRepository studyRepository;

    @Autowired
    ClassroomRepository classroomRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ExamScoreRepository examScoreRepository;

    @Autowired
    HandoutRepository handoutRepository;

    @PreAuthorize("hasPermission(#exam, 'CREATE')")
    public Exam registerExam(Exam exam) {
        return examRepository.save(exam);
    }

    @PreAuthorize("hasPermission(#exam, 'UPDATE')")
    public Exam updateExam(Exam exam) {
        Exam e = examRepository.findOne(exam.getId());
        e.setTitle(exam.getTitle());
        e.setDueDate(exam.getDueDate());
        e.setType(exam.getType());
        e.setAcademicYear(exam.getAcademicYear());
        if (exam.getStudy() != null)
            e.setStudy(studyRepository.findOne(exam.getStudy().getId()));
        if (exam.getClassrooms() != null) {
            List<Classroom> classrooms = new ArrayList<>();
            exam.getClassrooms().forEach(classroom -> classrooms.add(classroomRepository.findOne(classroom.getId())));
            updateExamClassrooms(e, classrooms);
        }
        return e;
    }

    @PreAuthorize("hasPermission(#examId, 'Exam', 'UPDATE')")
    public void updateQuestionsFile(long examId, File questionsFile) {
        Exam exam = examRepository.findOne(examId);
        if (exam.getQuestions() != null) {
            fileRepository.delete(exam.getQuestions());
        }
        exam.setQuestions(fileRepository.save(questionsFile));
    }

    @PreAuthorize("hasPermission(#examId, 'Exam', 'UPDATE')")
    public void updateAnswersFile(long examId, File answersFile) {
        Exam exam = examRepository.findOne(examId);
        if (exam.getAnswers() != null) {
            fileRepository.delete(exam.getAnswers());
        }
        exam.setAnswers(fileRepository.save(answersFile));
    }

    @PreAuthorize("hasPermission(#exerciseId, 'Exercise', 'UPDATE')")
    public void updateExerciseQuestionsFile(long exerciseId, File questionsFile) {
        Exercise exercise = exerciseRepository.findOne(exerciseId);
        if (exercise.getQuestions() != null) {
            fileRepository.delete(exercise.getQuestions());
        }
        exercise.setQuestions(fileRepository.save(questionsFile));
    }

    @PreAuthorize("hasPermission(#exerciseId, 'Exercise', 'UPDATE')")
    public void updateExerciseAnswersFile(long exerciseId, File answersFile) {
        Exercise exercise = exerciseRepository.findOne(exerciseId);
        if (exercise.getAnswers() != null) {
            fileRepository.delete(exercise.getAnswers());
        }
        exercise.setAnswers(fileRepository.save(answersFile));
    }

    private void updateExamClassrooms(Exam e, List<Classroom> classrooms) {
        classrooms.forEach(classroom -> {
            if (! e.getClassrooms().contains(classroom))
                e.getClassrooms().add(classroom);
        });
        Iterator<Classroom> iterator = e.getClassrooms().iterator();
        while (iterator.hasNext())
            if (! classrooms.contains(iterator.next()))
                iterator.remove();
    }

    @PreAuthorize("hasPermission(#examScore.exercise, 'UPDATE')")
    public ExamScore saveScore(ExamScore examScore) {
        return examScoreRepository.save(examScore);
    }

    @PreAuthorize("hasPermission(#examScore.exercise, 'DELETE')")
    public void deleteScore(ExamScore examScore) {
        examScoreRepository.delete(examScoreRepository.getOne(examScore.getId()));
    }

    @PreAuthorize("hasPermission(#exam, 'DELETE')")
    public void removeExam(Exam exam) {
        examRepository.delete(examRepository.getOne(exam.getId()));
    }

    @PreAuthorize("hasPermission(#exercise, 'CREATE')")
    public Exercise registerExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    @PreAuthorize("hasPermission(#exercise, 'UPDATE')")
    public Exercise updateExercise(Exercise exercise) {
        Exercise e = exerciseRepository.findOne(exercise.getId());
        e.setTitle(exercise.getTitle());
        e.setDueDate(exercise.getDueDate());
        e.setAcademicYear(exercise.getAcademicYear());
        if (exercise.getStudy() != null) {
            e.setStudy(studyRepository.findOne(exercise.getStudy().getId()));
            e.setCourse(e.getStudy().getCourse());
        }
        if (exercise.getClassrooms() != null) {
            List<Classroom> classrooms = new ArrayList<>();
            exercise.getClassrooms().forEach(classroom -> classrooms.add(classroomRepository.getOne(classroom.getId())));
            updateExerciseClassrooms(e, classrooms);
        }
        if (exercise.getWrongdoers() != null) {
            List<Student> wrongdoers = new ArrayList<>();
            exercise.getWrongdoers().forEach(student -> wrongdoers.add(studentRepository.getOne(student.getId())));
            updateExerciseWrongdoers(e, wrongdoers);
        }
        return e;
    }

    private void updateExerciseClassrooms(Exercise e, List<Classroom> classrooms) {
        classrooms.forEach(classroom -> {
            if (! e.getClassrooms().contains(classroom))
                e.getClassrooms().add(classroom);
        });
        Iterator<Classroom> iterator = e.getClassrooms().iterator();
        while (iterator.hasNext())
            if (! classrooms.contains(iterator.next()))
                iterator.remove();
    }

    private void updateExerciseWrongdoers(Exercise e, List<Student> wrongdoers) {
        wrongdoers.forEach(student -> {
            if (! e.getWrongdoers().contains(student))
                e.getWrongdoers().add(student);
        });
        Iterator<Student> iterator = e.getWrongdoers().iterator();
        while (iterator.hasNext())
            if (! wrongdoers.contains(iterator.next()))
                iterator.remove();
    }

    @PreAuthorize("hasPermission(#exercise, 'DELETE')")
    public void removeExercise(Exercise exercise) {
        exerciseRepository.delete(exerciseRepository.getOne(exercise.getId()));
    }

    @PreAuthorize("hasPermission(#handout, 'CREATE')")
    public Handout registerHandout(Handout handout) {
        return handoutRepository.save(handout);
    }

    @PreAuthorize("hasPermission(#handout, 'UPDATE')")
    public Handout updateHandout(Handout handout) {
        Handout h = handoutRepository.findOne(handout.getId());
        h.setTitle(handout.getTitle());
        if (handout.getStudy() != null) {
            h.setStudy(studyRepository.findOne(handout.getStudy().getId()));
            h.setCourse(h.getStudy().getCourse());
        }
        return h;
    }

    @PreAuthorize("hasPermission(#handout, 'DELETE')")
    public void removeHandout(Handout handout) {
        handoutRepository.delete(handoutRepository.getOne(handout.getId()));
    }

    @PreAuthorize("hasPermission(#handoutId, 'Handout', 'UPDATE')")
    public void updateHandoutFile(long handoutId, File file) {
        Handout handout = handoutRepository.findOne(handoutId);
        if (handout.getFile() != null) {
            fileRepository.delete(handout.getFile());
        }
        handout.setFile(fileRepository.save(file));
    }

}
