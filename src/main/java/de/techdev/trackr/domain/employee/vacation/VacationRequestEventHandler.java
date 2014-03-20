package de.techdev.trackr.domain.employee.vacation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Date;

/**
 * @author Moritz Schulze
 */
@RepositoryEventHandler(VacationRequest.class)
@Slf4j
public class VacationRequestEventHandler {

    @Autowired
    private HolidayCalculator holidayCalculator;

    @HandleBeforeCreate
    @PreAuthorize("hasRole('ROLE_SUPERVISOR') or ( isAuthenticated() and principal.id == #vacationRequest.employee.id )")
    public void prepareVacationRequest(VacationRequest vacationRequest) {
        Integer difference = holidayCalculator.calculateDifferenceBetweenExcludingHolidaysAndWeekends(vacationRequest.getStartDate(),
                vacationRequest.getEndDate(),
                vacationRequest.getEmployee().getFederalState());
        vacationRequest.setNumberOfDays(difference);
        vacationRequest.setStatus(VacationRequestStatus.PENDING);
        vacationRequest.setApprover(null);
        vacationRequest.setApprovalDate(null);
        vacationRequest.setSubmissionTime(new Date());
        log.debug("Creating vacation request {}", vacationRequest);
    }

    @HandleBeforeSave
    @PreAuthorize("hasRole('ROLE_SUPERVISOR') and principal.id != #vacationRequest.employee.id")
    public void authorizeUpdate(VacationRequest vacationRequest) {
        log.debug("Updating vacation request {}", vacationRequest);
    }

    @HandleBeforeDelete
    @PreAuthorize("hasRole('ROLE_SUPERVISOR') or ( isAuthenticated() and @vacationRequestEventHandler.employeeCanDeleteRequest(principal.id, #vacationRequest) )")
    public void authorizeDelete(VacationRequest vacationRequest) {
        log.debug("Deleting vacation request {}", vacationRequest);
    }

    @HandleBeforeLinkSave
    @PreAuthorize("denyAll()")
    public void denyLinksSave(VacationRequest vacationRequest, Object links) {

    }

    @HandleBeforeLinkDelete
    @PreAuthorize("denyAll()")
    public void denyLinks(VacationRequest vacationRequest) {

    }

    /**
     * Test if an employee may delete a vacation request. This means it is his own request and it is pending.
     * @param principalId The id of the logged in user.
     * @param request The vacation request
     * @return true if the user may delete, false otherwise.
     */
    public boolean employeeCanDeleteRequest(Long principalId, VacationRequest request) {
        return principalId.equals(request.getEmployee().getId()) && request.getStatus() == VacationRequestStatus.PENDING;
    }
}
