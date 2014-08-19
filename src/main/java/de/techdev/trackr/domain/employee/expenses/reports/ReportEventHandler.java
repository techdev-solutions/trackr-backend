package de.techdev.trackr.domain.employee.expenses.reports;

import de.techdev.trackr.domain.employee.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Moritz Schulze
 */
@RepositoryEventHandler(Report.class)
@Slf4j
public class ReportEventHandler {

    @HandleBeforeCreate
    @PreAuthorize("#travelExpenseReport.employee.id == principal?.id")
    public void checkCreateAuthority(Report travelExpenseReport) {
        log.debug("Creating travel expense report {}", travelExpenseReport);
    }

    @HandleBeforeSave
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    public void checkUpdateAuthority(Report travelExpenseReport) {
        log.debug("Updating travel expense report {}", travelExpenseReport);
    }

    @HandleBeforeDelete
    @PreAuthorize("@reportEventHandler.employeeCanDeleteReport(#travelExpenseReport, principal?.id) or hasRole('ROLE_ADMIN')")
    public void checkDeleteAuthority(Report travelExpenseReport) {
        log.debug("Deleting travel expense report {}", travelExpenseReport);
    }

    @HandleBeforeLinkSave
    @PreAuthorize("hasRole('ROLE_SUPERVISOR') or #travelExpenseReport.employee.id == principal?.id")
    public void checkLinkSaveAuthority(Report travelExpenseReport, Object links) {
        //TODO: links is the _old_ content of the link
        //TODO: how to check for security? the employee should not be able to edit debitor/project but how do we check that?
        //TODO: it is not possible to prohibit employees from editing links in general because it is used to add travel expenses.
        if (links != null && Employee.class.isAssignableFrom(links.getClass())) {
            throw new AccessDeniedException("Employee is not changeable on a travel expense report.");
        }
    }

    @HandleBeforeLinkDelete
    @PreAuthorize("hasRole('ROLE_SUPERVISOR') or #travelExpenseReport.employee.id == principal?.id")
    public void checkLinkDeleteAuthority(Report travelExpenseReport) {
        if(travelExpenseReport.getEmployee() == null) {
            throw new AccessDeniedException("Employee is not deletable on a travel expense report.");
        }
    }

    public boolean employeeCanDeleteReport(Report report, Long principalId) {
        return principalId != null && report.getEmployee().getId().equals(principalId) &&
                (report.getStatus() == Report.Status.PENDING || report.getStatus() == Report.Status.REJECTED);
    }
}
