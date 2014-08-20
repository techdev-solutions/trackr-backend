package de.techdev.trackr.domain.project.billtimes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Moritz Schulze
 */
@RepositoryEventHandler(BillableTime.class)
@Slf4j
public class BillableTimeEventHandler {

    @HandleBeforeCreate
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    public void checkCreateAuthority(BillableTime billableTime) {
        log.debug("Creating billable time {}", billableTime);
    }

    @HandleBeforeSave
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    public void checkUpdateAuthority(BillableTime billableTime) {
        log.debug("Updating billable time {}", billableTime);
    }

    @HandleBeforeDelete
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    public void checkDeleteAuthority(BillableTime billableTime) {
        log.debug("Deleting billable time {}", billableTime);
    }

    @HandleBeforeLinkSave
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    public void checkLinkSaveAuthority(BillableTime billableTime, Object links) {
        log.debug("Updating links on billable time {}", billableTime);
    }

    @HandleBeforeLinkDelete
    @PreAuthorize("denyAll()")
    public void checkLinkDeleteAuthority(BillableTime billableTime) {
        log.debug("Deleting links on {}", billableTime);
    }
}
