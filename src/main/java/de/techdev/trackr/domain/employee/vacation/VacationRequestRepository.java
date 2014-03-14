package de.techdev.trackr.domain.employee.vacation;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Moritz Schulze
 */
public interface VacationRequestRepository extends CrudRepository<VacationRequest, Long> {

}
