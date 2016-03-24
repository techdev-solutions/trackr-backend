package de.techdev.trackr.domain.employee.expenses;

import de.techdev.trackr.domain.employee.expenses.reports.Report;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryEventHandler(TravelExpense.class)
@SuppressWarnings("unused")
public class TravelExpenseEventHandler {

    @HandleBeforeCreate
    @PreAuthorize("@travelExpenseEventHandler.canCreate(principal?.username, #travelExpense)")
    public void checkCreateAuthority(TravelExpense travelExpense) {
    }

    @HandleBeforeSave
    @PreAuthorize("hasRole('ROLE_SUPERVISOR') or @travelExpenseEventHandler.canEdit(principal?.username, #travelExpense)")
    public void checkUpdateAuthority(TravelExpense travelExpense) {
    }

    @HandleBeforeDelete
    @PreAuthorize("hasRole('ROLE_SUPERVISOR') or @travelExpenseEventHandler.canDelete(principal?.username, #travelExpense)")
    public void checkDeleteAuthority(TravelExpense travelExpense) {
    }

    @HandleBeforeLinkSave
    @PreAuthorize("denyAll()")
    public void denyLinkSave(TravelExpense travelExpense, Object links) {
        //links are not settable
    }

    @HandleBeforeLinkDelete
    @PreAuthorize("denyAll()")
    public void denyLinkDelete(TravelExpense travelExpense, Object linkedEntity) {
        //links are not deletable.
    }

    public boolean canCreate(String email, TravelExpense travelExpense) {
        return email != null && email.equals(travelExpense.getReport().getEmployee().getEmail()) &&
                travelExpense.getReport().getStatus() != Report.Status.APPROVED &&
                travelExpense.getReport().getStatus() != Report.Status.SUBMITTED;
    }

    public boolean canEdit(String email, TravelExpense travelExpense) {
        return email != null && email.equals(travelExpense.getReport().getEmployee().getEmail()) &&
                travelExpense.getReport().getStatus() != Report.Status.APPROVED &&
                travelExpense.getReport().getStatus() != Report.Status.SUBMITTED;
    }

    public boolean canDelete(String email, TravelExpense travelExpense) {
        return email != null && email.equals(travelExpense.getReport().getEmployee().getEmail()) &&
                travelExpense.getReport().getStatus() != Report.Status.APPROVED &&
                travelExpense.getReport().getStatus() != Report.Status.SUBMITTED;
    }
}

