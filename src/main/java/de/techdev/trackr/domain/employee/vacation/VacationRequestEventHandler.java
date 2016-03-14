package de.techdev.trackr.domain.employee.vacation;

import de.techdev.trackr.domain.common.UuidMapper;
import de.techdev.trackr.domain.employee.vacation.support.VacationRequestNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Date;
import java.util.UUID;

@RepositoryEventHandler(VacationRequest.class)
@SuppressWarnings("unused")
public class VacationRequestEventHandler {

    @Autowired
    private HolidayCalculator holidayCalculator;

    @Autowired
    private VacationRequestNotifyService vacationRequestNotifyService;

    @Autowired
    private UuidMapper uuidMapper;

    @HandleBeforeCreate
    @PreAuthorize("hasRole('ROLE_ADMIN') or principal?.username == #vacationRequest.employee.email")
    public void prepareVacationRequest(VacationRequest vacationRequest) {
        Integer difference = holidayCalculator.calculateDifferenceBetweenExcludingHolidaysAndWeekends(vacationRequest.getStartDate(),
                vacationRequest.getEndDate(),
                vacationRequest.getEmployee().getFederalState());
        vacationRequest.setNumberOfDays(difference);
        vacationRequest.setStatus(VacationRequest.VacationRequestStatus.PENDING);
        vacationRequest.setApprover(null);
        vacationRequest.setApprovalDate(null);
        vacationRequest.setSubmissionTime(new Date());
    }

    @HandleAfterCreate
    public void afterCreation(VacationRequest vacationRequest) {
        UUID uuid = uuidMapper.createUUID(vacationRequest.getId());
        vacationRequestNotifyService.notifySupervisors(vacationRequest, uuid);
    }

    @HandleBeforeSave
    @PreAuthorize("hasRole('ROLE_SUPERVISOR') and principal?.username != #vacationRequest.employee.email")
    public void authorizeUpdate(VacationRequest vacationRequest) {
    }

    @HandleBeforeDelete
    @PreAuthorize("( hasRole('ROLE_SUPERVISOR') and @vacationRequestEventHandler.supervisorCanDeleteRequest(principal?.username, #vacationRequest) ) " +
            "or @vacationRequestEventHandler.employeeCanDeleteRequest(principal?.username, #vacationRequest)")
    public void authorizeDelete(VacationRequest vacationRequest) {
    }

    @HandleBeforeLinkSave
    @PreAuthorize("denyAll()")
    public void denyLinksSave(VacationRequest vacationRequest, Object links) {
        //deny all, cannot be called
    }

    @HandleBeforeLinkDelete
    @PreAuthorize("denyAll()")
    public void denyLinks(VacationRequest vacationRequest, Object linkedEntity) {
        //deny all, cannot be called
    }

    /**
     * Test if an employee may delete a vacation request. This means it is his own request and it is pending.
     * @param username The email of the logged in user
     * @param request The vacation request
     * @return true if the user may delete, false otherwise.
     */
    public boolean employeeCanDeleteRequest(String username, VacationRequest request) {
        return username != null &&
                username.equals(request.getEmployee().getEmail()) && request.getStatus() == VacationRequest.VacationRequestStatus.PENDING;
    }

    public boolean supervisorCanDeleteRequest(String username, VacationRequest request) {
        return username != null &&
                !username.equals(request.getEmployee().getEmail());
    }
}
