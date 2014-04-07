package de.techdev.trackr.domain.employee.expenses;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * @author Moritz Schulze
 */
public interface TravelExpenseRepository extends CrudRepository<TravelExpense, Long> {

    @Override
    @RestResource(exported = false)
    Iterable<TravelExpense> findAll();

    @Override
    @RestResource(exported = false)
    TravelExpense findOne(Long aLong);
}
