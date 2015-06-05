package de.techdev.trackr.domain.employee.vacation;

import de.techdev.trackr.domain.common.FederalState;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Date;
import java.util.List;

/**
 * @author Moritz Schulze
 */
public interface HolidayRepository extends CrudRepository<Holiday, Long> {

    @Override
    @RestResource(exported = false)
    <S extends Holiday> S save(S entity);

    @Override
    @RestResource(exported = false)
    void delete(Long id);

    List<Holiday> findByFederalStateAndDayBetween(@Param("state") FederalState federalState, @Param("start") Date start, @Param("end") Date end);

}
