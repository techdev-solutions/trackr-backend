package de.techdev.trackr.domain.project.worktimes;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.project.Project;

/**
 * @author Moritz Schulze
 */
public interface WorkTimeRepository extends CrudRepository<WorkTime, Long> {

    @Override
    @RestResource(exported = false)
    List<WorkTime> findAll();

    @Override
    @RestResource(exported = false)
    List<WorkTime> findAll(Iterable<Long> longs);

    @Override
    @PostAuthorize("hasRole('ROLE_SUPERVISOR') or returnObject.employee.id == principal?.id")
    WorkTime findOne(Long aLong);

    @PreAuthorize("hasRole('ROLE_SUPERVISOR') or #employee.id == principal?.id")
    List<WorkTime> findByEmployeeAndDateOrderByStartTimeAsc(@Param("employee") Employee employee, @Param("date") LocalDate date);

    @PreAuthorize("hasRole('ROLE_SUPERVISOR') or #employee.id == principal?.id")
    List<WorkTime> findByEmployeeAndDateBetweenOrderByDateAscStartTimeAsc(@Param("employee") Employee employee,
                                                                          @Param("start") LocalDate start,
                                                                          @Param("end") LocalDate end);

    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    List<WorkTime> findByProjectAndDateBetweenOrderByDateAscStartTimeAsc(@Param("project") Project project,
                                                                         @Param("start") LocalDate start,
                                                                         @Param("end") LocalDate end);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<WorkTime> findByDateBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);
}
