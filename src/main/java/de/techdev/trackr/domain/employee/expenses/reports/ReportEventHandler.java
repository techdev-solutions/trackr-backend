package de.techdev.trackr.domain.employee.expenses.reports;

import de.techdev.trackr.domain.employee.Employee;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryEventHandler(Report.class)
@SuppressWarnings("unused")
public class ReportEventHandler {

    @HandleBeforeCreate
    @PreAuthorize("#travelExpenseReport.employee.email == principal?.username")
    public void checkCreateAuthority(Report travelExpenseReport) {
    }

    @HandleBeforeSave
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    public void checkUpdateAuthority(Report travelExpenseReport) {
    }

    @HandleBeforeDelete
    @PreAuthorize("@reportEventHandler.employeeCanDeleteReport(#travelExpenseReport, principal?.username) or hasRole('ROLE_ADMIN')")
    public void checkDeleteAuthority(Report travelExpenseReport) {
    }

    @HandleBeforeLinkSave
    @PreAuthorize("hasRole('ROLE_SUPERVISOR') or #travelExpenseReport.employee.email == principal?.username")
    public void checkLinkSaveAuthority(Report travelExpenseReport, Object links) {
        //TODO: links is the _old_ content of the link
        //TODO: how to check for security? the employee should not be able to edit debitor/project but how do we check that?
        //TODO: it is not possible to prohibit employees from editing links in general because it is used to add travel expenses.
        if (links != null && Employee.class.isAssignableFrom(links.getClass())) {
            throw new AccessDeniedException("Employee is not changeable on a travel expense report.");
        }
    }

    @HandleBeforeLinkDelete
    @PreAuthorize("hasRole('ROLE_SUPERVISOR') or #travelExpenseReport.employee.email == principal?.username")
    public void checkLinkDeleteAuthority(Report travelExpenseReport) {
        if(travelExpenseReport.getEmployee() == null) {
            throw new AccessDeniedException("Employee is not deletable on a travel expense report.");
        }
    }

    public boolean employeeCanDeleteReport(Report report, String username) {
        return username != null && report.getEmployee().getEmail().equals(username) &&
                (report.getStatus() == Report.Status.PENDING || report.getStatus() == Report.Status.REJECTED);
    }
}
