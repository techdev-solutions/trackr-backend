package de.techdev.trackr.repository;

import de.techdev.trackr.domain.BillableTime;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

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
}
