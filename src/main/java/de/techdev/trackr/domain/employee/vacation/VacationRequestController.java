package de.techdev.trackr.domain.employee.vacation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Moritz Schulze
 */
@Controller
@RequestMapping("/vacationRequests")
@Slf4j
public class VacationRequestController {

    @Autowired
    private VacationRequestApproveService vacationRequestApproveService;

    @Autowired
    private VacationRequestRepository vacationRequestRepository;

    @Autowired
    private HolidayCalculator holidayCalculator;

    /**
     * Approve a vacation request. Can only be done by supervisors, but they are not allowed to approve their own requests.
     * Only works on pending requests.
     *
     * @param vacationRequest The vacation request to approve
     * @param principal       The Spring Security principal to extract the user from.
     * @return The vacation request object.
     */
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    @RequestMapping(value = "/{id}/approve", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public VacationRequest approve(@PathVariable("id") VacationRequest vacationRequest, Principal principal) {
        return vacationRequestApproveService.approve(vacationRequest, principal.getName());
    }

    /**
     * Reject a vacation request. Can only be done by supervisors, but they are not allowed to reject their own requests.
     * Only works on pending requests.
     *
     * @param vacationRequest The vacation request to reject.
     * @param principal       The Spring Security principal to extract the user from.
     * @return The vacation request object.
     */
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    @RequestMapping(value = "/{id}/reject", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public VacationRequest reject(@PathVariable("id") VacationRequest vacationRequest, Principal principal) {
        return vacationRequestApproveService.reject(vacationRequest, principal.getName());
    }

    /**
     * Create a mapping of employee names to the effective number of vacation days between a start and end date.
     *
     * All approved vacation requests that coincide with the period are collected. Only the cut of every vacation request with the period is used for the calculation.
     * Public holidays and weekends are exluded.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    @RequestMapping(value = "/daysPerEmployeeBetween", method = RequestMethod.GET, produces = "application/hal+json")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Integer> daysPerEmployeeBetween(
            @RequestParam("start") Date start,
            @RequestParam("end") Date end
    ) {
        List<VacationRequest> vacationRequests = vacationRequestRepository
                .findByStartDateBetweenOrEndDateBetweenAndStatus(start, end, start, end, VacationRequestStatus.APPROVED);
        return vacationRequests.stream().collect(
                Collectors.groupingBy(
                        vacationRequest -> vacationRequest.getEmployee().getFirstName() + " " + vacationRequest.getEmployee().getLastName(),
                        Collectors.summingInt(
                                vacationRequest -> getVacationDaysBetween(vacationRequest, start, end)
                        )
                )
        );
    }

    /**
     * @return Returns the number of days of the vacation request between start and end that aren't holidays or weekends.
     */
    protected Integer getVacationDaysBetween(VacationRequest vacationRequest, Date start, Date end) {
        return holidayCalculator.calculateDifferenceBetweenExcludingHolidaysAndWeekends(
                // If the start of the vacation request is before the desired period we use the period start
                getMaximum(start, vacationRequest.getStartDate()),
                // If the end of the vacation request is after the desired period we use the period end
                getMinimum(end, vacationRequest.getEndDate()),
                vacationRequest.getEmployee().getFederalState()
        );
    }

    /**
     * The minimum of the two dates. No null checks.
     */
    protected Date getMinimum(Date a, Date b) {
        return a.before(b) ? a : b;
    }

    /**
     * The maximum of the two dates. No null checks.
     */
    protected Date getMaximum(Date a, Date b) {
        return a.before(b) ? b : a;
    }
}
