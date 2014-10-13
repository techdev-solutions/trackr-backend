package de.techdev.trackr.domain.project.billtimes;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import de.techdev.trackr.domain.project.Project;

/**
 * @author Moritz Schulze
 */
public interface BillableTimeRepository extends CrudRepository<BillableTime, Long> {
    @Override
    @RestResource(exported = false)
    Iterable<BillableTime> findAll();

    @Override
    @RestResource(exported = false)
    Iterable<BillableTime> findAll(Iterable<Long> longs);

    @Override
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    BillableTime findOne(Long aLong);

    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    List<BillableTime> findByProjectAndDateBetweenOrderByDateAsc(@Param("project") Project project,
                                                                 @Param("start") LocalDate start,
                                                                 @Param("end") LocalDate end);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<BillableTime> findByDateBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);
}
