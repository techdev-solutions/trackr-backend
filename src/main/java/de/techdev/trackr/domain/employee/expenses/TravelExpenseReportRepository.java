package de.techdev.trackr.domain.employee.expenses;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostAuthorize;

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
}
