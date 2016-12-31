package com.avin.schoolportal.repository;

import com.avin.schoolportal.domain.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Yubar on 12/1/2016.
 */
public interface ClassTimeRepository extends PagingAndSortingRepository<ClassTime, Long>, JpaSpecificationExecutor<ClassTime> {

    @Query("select c from ClassTime c where c.classroom = ?1 and c.weekDay = ?4 and ( (c.from <= ?2 and c.to > ?2) or (c.from < ?3 and c.to >= ?3) or (c.from >= ?2 and c.to <= ?3))")
    List<ClassTime> findConflictClassTimeByClassroom(Classroom classroom, double from, double to, WeekDay weekDay);

    @Query("select c from ClassTime c where c.teacher = ?1 and c.weekDay = ?4 and ( (c.from <= ?2 and c.to > ?2) or (c.from < ?3 and c.to >= ?3) or (c.from >= ?2 and c.to <= ?3))")
    List<ClassTime> findConflictClassTimeByTeacher(Teacher teacher, double from, double to, WeekDay weekDay);

    @Query("select t from Teacher t where t not in (select c.teacher from ClassTime c where c.weekDay = ?3 and ((c.from <= ?1 and c.to > ?1) or (c.from < ?2 and c.to >= ?2) or (c.from >= ?1 and c.to <= ?2)))")
    List<Teacher> findTeacherAvailableFor(double from, double to, WeekDay weekDay);

    List<ClassTime> findByClassroomAndStudy(Classroom classroom, Study study);

    @EntityGraph(attributePaths = {"study", "teacher"})
    List<ClassTime> findByClassroom(Classroom classroom, Sort sort);

}
