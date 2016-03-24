package de.techdev.trackr.domain.employee.expenses;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostAuthorize;

/**
 * @author Moritz Schulze
 */
public interface TravelExpenseRepository extends CrudRepository<TravelExpense, Long> {

    @Override
    @RestResource(exported = false)
    Iterable<TravelExpense> findAll();

    @Override
    @PostAuthorize("returnObject?.report.employee.email == principal?.username")
    TravelExpense findOne(Long aLong);
}
