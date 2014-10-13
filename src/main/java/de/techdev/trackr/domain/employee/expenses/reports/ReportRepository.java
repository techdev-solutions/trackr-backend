package de.techdev.trackr.domain.employee.expenses.reports;

import de.techdev.trackr.domain.employee.Employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.Instant;
import java.util.List;

/**
 * @author Moritz Schulze
 */
@RepositoryRestResource(path = "travelExpenseReports")
public interface ReportRepository extends CrudRepository<Report, Long> {

    @Override
    @RestResource(exported = false)
    Iterable<Report> findAll();

    @Override
    @PostAuthorize("hasRole('ROLE_SUPERVISOR') or returnObject.employee.id == principal?.id")
    Report findOne(Long aLong);

    @PreAuthorize("#employee.id == principal?.id")
    Page<Report> findByEmployeeAndStatusOrderByStatusAsc(@Param("employee") Employee employee, @Param("status") Report.Status status, Pageable pageable);

    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    Page<Report> findByStatusOrderByEmployee_LastNameAsc(@Param("status") Report.Status status, Pageable pageable);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<Report> findBySubmissionDateBetween(@Param("start") Instant start, @Param("end") Instant end);
}

