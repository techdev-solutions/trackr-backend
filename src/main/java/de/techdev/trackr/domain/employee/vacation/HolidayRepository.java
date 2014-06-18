package de.techdev.trackr.domain.employee.vacation;

import de.techdev.trackr.domain.common.FederalState;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;
import java.util.List;

/**
 * @author Moritz Schulze
 */
public interface HolidayRepository extends CrudRepository<Holiday, Long> {

    List<Holiday> findByFederalStateAndDayBetween(@Param("state") FederalState federalState, @Param("start") Date start, @Param("end") Date end);

}
