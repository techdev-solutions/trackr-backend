package de.techdev.trackr.domain.employee.vacation;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Moritz Schulze
 */
public interface VacationRequestApproveService {

    /**
     * Approves a vacation request. The vacation request will be fetched from the repository, the approver by the name given in
     * supervisor email.
     * <p>
     * Will not approve pending vacation requests.
     *
     * @param vacationRequest The vacation request to approve
     * @param supervisorEmail The email address of the employee to use as the approver.
     * @return The approved (or not) vacation request.
     */
    @Transactional
    @PreAuthorize("hasRole('ROLE_SUPERVISOR') and principal.id != #vacationRequest.employee.id")
    VacationRequest approve(VacationRequest vacationRequest, String supervisorEmail);

    /**
     * Rejects a vacation request. The vacation request will be fetched from the repository, the rejector by the name given in
     * supervisor email.
     * <p>
     * Will only reject pending vacation requests.
     *
     * @param vacationRequest   The vacation request to reject.
     * @param supervisorEmail   The email address of the employee to use as the approver.
     * @return The approved (or not) vacation request.
     */
    @Transactional
    @PreAuthorize("hasRole('ROLE_SUPERVISOR') and principal.id != #vacationRequest.employee.id")
    VacationRequest reject(VacationRequest vacationRequest, String supervisorEmail);

    /**
     * Approves all requests that are more than seven days old.
     */
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void approveSevenDayOldRequests();
}
