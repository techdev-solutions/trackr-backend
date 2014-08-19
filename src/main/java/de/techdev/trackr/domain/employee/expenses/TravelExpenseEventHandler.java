package de.techdev.trackr.domain.employee.expenses;

import de.techdev.trackr.domain.employee.expenses.reports.Report;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Moritz Schulze
 */
@RepositoryEventHandler(TravelExpense.class)
@Slf4j
public class TravelExpenseEventHandler {

    @HandleBeforeCreate
    @PreAuthorize("@travelExpenseEventHandler.canCreate(principal?.id, #travelExpense)")
    public void checkCreateAuthority(TravelExpense travelExpense) {
        log.debug("Creating travel expense {}", travelExpense);
    }

    @HandleBeforeSave
    @PreAuthorize("hasRole('ROLE_SUPERVISOR') or @travelExpenseEventHandler.canEdit(principal?.id, #travelExpense)")
    public void checkUpdateAuthority(TravelExpense travelExpense) {
        log.debug("Updating travel expense {}", travelExpense);
    }

    @HandleBeforeDelete
    @PreAuthorize("hasRole('ROLE_SUPERVISOR') or @travelExpenseEventHandler.canDelete(principal?.id, #travelExpense)")
    public void checkDeleteAuthority(TravelExpense travelExpense) {
        log.debug("Deleting travel expense {}", travelExpense);
    }

    @HandleBeforeLinkSave
    @PreAuthorize("denyAll()")
    public void denyLinkSave(TravelExpense travelExpense, Object links) {
        //links are not settable
    }

    @HandleBeforeLinkDelete
    @PreAuthorize("denyAll()")
    public void denyLinkDelete(TravelExpense travelExpense) {
        //links are not deletable.
    }

    public boolean canCreate(Long id, TravelExpense travelExpense) {
        return id != null &&travelExpense.getReport().getEmployee().getId().equals(id) &&
                travelExpense.getReport().getStatus() != Report.Status.APPROVED &&
                travelExpense.getReport().getStatus() != Report.Status.SUBMITTED;
    }

    public boolean canEdit(Long id, TravelExpense travelExpense) {
        return id != null && travelExpense.getReport().getEmployee().getId().equals(id) &&
                travelExpense.getReport().getStatus() != Report.Status.APPROVED &&
                travelExpense.getReport().getStatus() != Report.Status.SUBMITTED;
    }

    public boolean canDelete(Long id, TravelExpense travelExpense) {
        return id != null && travelExpense.getReport().getEmployee().getId().equals(id) &&
                travelExpense.getReport().getStatus() != Report.Status.APPROVED &&
                travelExpense.getReport().getStatus() != Report.Status.SUBMITTED;
    }
}

