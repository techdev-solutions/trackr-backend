package de.techdev.trackr.domain.employee.expenses;

import de.techdev.trackr.domain.employee.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Moritz Schulze
 */
@RepositoryEventHandler(TravelExpenseReport.class)
@Slf4j
public class TravelExpenseReportEventHandler {

    @HandleBeforeCreate
    @PreAuthorize("#travelExpenseReport.employee.id == principal.id")
    public void checkCreateAuthority(TravelExpenseReport travelExpenseReport) {
        log.debug("Creating travel expense report {}", travelExpenseReport);
    }

    @HandleBeforeSave
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    public void checkUpdateAuthority(TravelExpenseReport travelExpenseReport) {
        log.debug("Updating travel expense report {}", travelExpenseReport);
    }

    @HandleBeforeDelete
    @PreAuthorize("(isAuthenticated() and @travelExpenseReportEventHandler.employeeCanDeleteReport(#travelExpenseReport, principal.id)) or hasRole('ROLE_ADMIN')")
    public void checkDeleteAuthority(TravelExpenseReport travelExpenseReport) {
        log.debug("Deleting travel expense report {}", travelExpenseReport);
    }

    @HandleBeforeLinkSave
    @PreAuthorize("hasRole('ROLE_SUPERVISOR') or ( isAuthenticated() and #travelExpenseReport.employee.id == principal.id )")
    public void checkLinkSaveAuthority(TravelExpenseReport travelExpenseReport, Object links) {
        //TODO: links is the _old_ content of the link
        //TODO: how to check for security? the employee should not be able to edit debitor/project but how do we check that?
        //TODO: it is not possible to prohibit employees from editing links in general because it is used to add travel expenses.
        if (links != null && Employee.class.isAssignableFrom(links.getClass())) {
            throw new AccessDeniedException("Employee is not changeable on a travel expense report.");
        }
    }

    @HandleBeforeLinkDelete
    @PreAuthorize("hasRole('ROLE_SUPERVISOR') or ( isAuthenticated() and #travelExpenseReport.employee.id == principal.id )")
    public void checkLinkDeleteAuthority(TravelExpenseReport travelExpenseReport) {
        if(travelExpenseReport.getEmployee() == null) {
            throw new AccessDeniedException("Employee is not deletable on a travel expense report.");
        }
    }

    public boolean employeeCanDeleteReport(TravelExpenseReport report, Long principalId) {
        return report.getEmployee().getId().equals(principalId) &&
                (report.getStatus() == TravelExpenseReportStatus.PENDING || report.getStatus() == TravelExpenseReportStatus.REJECTED);
    }
}
