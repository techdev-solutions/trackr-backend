package de.techdev.trackr.domain.employee.expenses;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * @author Moritz Schulze
 */
public interface TravelExpenseReportCommentRepository extends CrudRepository<TravelExpenseReportComment, Long> {

    @Override
    @RestResource(exported = false)
    Iterable<TravelExpenseReportComment> findAll();

    @Override
    @RestResource(exported = false)
    TravelExpenseReportComment findOne(Long aLong);

    @Override
    @RestResource(exported = false)
    void delete(Long aLong);

    @PreAuthorize("hasRole('ROLE_SUPERVISOR') or ( isAuthenticated() and  #travelExpenseReport.employee.id == principal.id )")
    List<TravelExpenseReportComment> findByTravelExpenseReportOrderBySubmissionDateAsc(@Param("report") TravelExpenseReport travelExpenseReport);
}
