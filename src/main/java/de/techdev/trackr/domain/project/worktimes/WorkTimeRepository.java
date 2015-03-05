package de.techdev.trackr.domain.project.worktimes;

import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.project.Project;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

public interface WorkTimeRepository extends CrudRepository<WorkTime, Long> {

    @Override
    @RestResource(exported = false)
    List<WorkTime> findAll();

    @Override
    @RestResource(exported = false)
    List<WorkTime> findAll(Iterable<Long> longs);

    @Override
    @PostAuthorize("hasRole('ROLE_SUPERVISOR') or returnObject.employee.email == principal?.username")
    WorkTime findOne(Long aLong);

    @PreAuthorize("hasRole('ROLE_SUPERVISOR') or #employee.email == principal?.username")
    List<WorkTime> findByEmployeeAndDateOrderByStartTimeAsc(@Param("employee") Employee employee, @Param("date") @Temporal(TemporalType.DATE) Date date);

    @PreAuthorize("hasRole('ROLE_SUPERVISOR') or #employee.email == principal?.username")
    List<WorkTime> findByEmployeeAndDateBetweenOrderByDateAscStartTimeAsc(@Param("employee") Employee employee,
                                                                          @Param("start") @Temporal(TemporalType.DATE) Date start,
                                                                          @Param("end") @Temporal(TemporalType.DATE) Date end);

    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    List<WorkTime> findByProjectAndDateBetweenOrderByDateAscStartTimeAsc(@Param("project") Project project,
                                                                         @Param("start") @Temporal(TemporalType.DATE) Date start,
                                                                         @Param("end") @Temporal(TemporalType.DATE) Date end);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<WorkTime> findByDateBetween(@Param("start") Date start, @Param("end") Date end);
}
