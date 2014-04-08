package de.techdev.trackr.domain.employee.expenses;

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
    @PreAuthorize("#travelExpense.report.employee.id == principal.id")
    public void checkCreateAuthority(TravelExpense travelExpense) {
        log.debug("Creating travel expense {}", travelExpense);
    }

    @HandleBeforeSave
    @PreAuthorize("hasRole('ROLE_SUPERVISOR') or ( #travelExpense.report.employee.id == principal.id )")
    public void checkUpdateAuthority(TravelExpense travelExpense) {
        log.debug("Updating travel expense {}", travelExpense);
    }

    @HandleBeforeDelete
    @PreAuthorize("hasRole('ROLE_SUPERVISOR') or ( #travelExpense.report.employee.id == principal.id )")
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
}

