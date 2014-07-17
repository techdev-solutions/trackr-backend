package de.techdev.trackr.domain.employee.expenses;

import de.techdev.trackr.domain.employee.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Date;
import java.util.List;

/**
 * @author Moritz Schulze
 */
public interface TravelExpenseReportRepository extends CrudRepository<TravelExpenseReport, Long> {

    @Override
    @RestResource(exported = false)
    Iterable<TravelExpenseReport> findAll();

    @Override
    @PostAuthorize("hasRole('ROLE_SUPERVISOR') or ( isAuthenticated() and returnObject.employee.id == principal.id )")
    TravelExpenseReport findOne(Long aLong);

    @PreAuthorize("isAuthenticated() and #employee.id == principal.id")
    List<TravelExpenseReport> findByEmployeeOrderByStatusAsc(@Param("employee") Employee employee);

    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    Page<TravelExpenseReport> findByStatusOrderByEmployee_LastNameAsc(@Param("status") TravelExpenseReportStatus status, Pageable pageable);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<TravelExpenseReport> findBySubmissionDateBetween(@Param("start") Date start, @Param("end") Date end);
}

