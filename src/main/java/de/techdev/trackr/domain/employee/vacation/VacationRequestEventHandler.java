package de.techdev.trackr.domain.employee.vacation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

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
    public void calculateNumberOfDays(VacationRequest vacationRequest) {
        Integer difference = holidayCalculator.calculateDifferenceBetweenExcludingHolidaysAndWeekends(vacationRequest.getStartDate(),
                vacationRequest.getEndDate(),
                vacationRequest.getEmployee().getFederalState());
        vacationRequest.setNumberOfDays(difference);
        vacationRequest.setApproved(false);
        vacationRequest.setApprover(null);
        vacationRequest.setApprovalDate(null);
        log.debug("Creating vacation request {}", vacationRequest);
    }

    @HandleBeforeSave
    @PreAuthorize("hasRole('ROLE_SUPERVISOR') and principal.id != #vacationRequest.employee.id")
    public void authorizeUpdate(VacationRequest vacationRequest) {
        log.debug("Updating vacation request {}", vacationRequest);
    }

    @HandleBeforeDelete
    @PreAuthorize("hasRole('ROLE_SUPERVISOR') or ( isAuthenticated() and principal.id == #vacationRequest.employee.id )")
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
}
