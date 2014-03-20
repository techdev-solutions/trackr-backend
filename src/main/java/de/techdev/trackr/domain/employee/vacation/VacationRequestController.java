package de.techdev.trackr.domain.employee.vacation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @author Moritz Schulze
 */
@Controller
@RequestMapping("/vacationRequests")
@Slf4j
public class VacationRequestController {

    @Autowired
    private VacationRequestApproveService vacationRequestApproveService;

    /**
     * Approve a vacation request. Can only be done by supervisors, but they are not allowed to approve their own requests.
     * Only works on pending requests.
     *
     * @param id        The id of the vacation request to approve.
     * @param principal The Spring Security principal to extract the user from.
     * @return The vacation request object.
     */
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    @RequestMapping(value = "/{id}/approve", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public VacationRequest approve(@PathVariable("id") Long id, Principal principal) {
        return vacationRequestApproveService.approve(id, principal.getName());
    }

    /**
     * Reject a vacation request. Can only be done by supervisors, but they are not allowed to reject their own requests.
     * Only works on pending requests.
     *
     * @param id        The id of the vacation request to approve.
     * @param principal The Spring Security principal to extract the user from.
     * @return The vacation request object.
     */
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    @RequestMapping(value = "/{id}/reject", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public VacationRequest reject(@PathVariable("id") Long id, Principal principal) {
        return vacationRequestApproveService.reject(id, principal.getName());
    }
}
