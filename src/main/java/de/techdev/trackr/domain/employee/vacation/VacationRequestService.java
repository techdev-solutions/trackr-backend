package de.techdev.trackr.domain.employee.vacation;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Moritz Schulze
 */
public interface VacationRequestService {

    /**
     * Approves a vacation request. The vacation request will be fetched from the repository, the approver by the name given in
     * supervisor email.
     * <p>
     * Will not approve pending vacation requests.
     *
     * @param vacationRequestId The id of the vacation request to approve.
     * @param supervisorEmail   The email address of the employee to use as the approver.
     * @return The approved (or not) vacation request.
     */
    @Transactional
    @PostAuthorize("hasRole('ROLE_SUPERVISOR') and principal.id != returnObject.employee.id")
    VacationRequest approve(Long vacationRequestId, String supervisorEmail);

    /**
     * Rejects a vacation request. The vacation request will be fetched from the repository, the rejector by the name given in
     * supervisor email.
     * <p>
     * Will only reject pending vacation requests.
     *
     * @param vacationRequestId The id of the vacation request to approve.
     * @param supervisorEmail   The email address of the employee to use as the approver.
     * @return The approved (or not) vacation request.
     */
    @Transactional
    @PostAuthorize("hasRole('ROLE_SUPERVISOR') and principal.id != returnObject.employee.id")
    VacationRequest reject(Long vacationRequestId, String supervisorEmail);

}
