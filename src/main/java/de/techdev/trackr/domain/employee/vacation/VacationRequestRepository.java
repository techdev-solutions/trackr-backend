package de.techdev.trackr.domain.employee.vacation;

import de.techdev.trackr.domain.employee.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Date;
import java.util.List;

public interface VacationRequestRepository extends CrudRepository<VacationRequest, Long> {

    @Override
    @PostAuthorize("hasRole('ROLE_SUPERVISOR') or principal?.username == returnObject.employee.email")
    VacationRequest findOne(Long aLong);

    @Override
    @RestResource(exported = false)
    Iterable<VacationRequest> findAll();

    @PreAuthorize("hasRole('ROLE_SUPERVISOR') or principal?.username == #employee.email")
    List<VacationRequest> findByEmployeeOrderByStartDateAsc(@Param("employee") Employee employee);

    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    List<VacationRequest> findByStatusOrderBySubmissionTimeAsc(@Param("status") VacationRequest.VacationRequestStatus status);

    @RestResource(exported = false)
    List<VacationRequest> findBySubmissionTimeBeforeAndStatus(Date date, VacationRequest.VacationRequestStatus status);

    /**
     * Find vacation requests of a certain status that overlap with a period.
     */
    @RestResource(exported = false)
    @Query("SELECT vr FROM VacationRequest vr WHERE (vr.startDate BETWEEN :startLower AND :startHigher OR vr.endDate BETWEEN :endLower AND :endHigher) AND vr.status = :status")
    List<VacationRequest> findByStartDateBetweenOrEndDateBetweenAndStatus(
            @Param("startLower") Date startLower, @Param("startHigher") Date startHigher,
            @Param("endLower") Date endLower, @Param("endHigher") Date endHigher,
            @Param("status") VacationRequest.VacationRequestStatus status);

    /**
     * Find one by id without security.
     * @param id The id of the vacation request to find.
     */
    @RestResource(exported = false)
    @Query("SELECT vr FROM VacationRequest vr WHERE vr.id = :id")
    VacationRequest findOneWithoutSecurity(@Param("id") Long id);
}
