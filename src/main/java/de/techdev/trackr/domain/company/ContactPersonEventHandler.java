package de.techdev.trackr.domain.company;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Moritz Schulze
 */
@RepositoryEventHandler(ContactPerson.class)
@Slf4j
public class ContactPersonEventHandler {

    @HandleBeforeCreate
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    public void checkCreateAuthority(ContactPerson contactPerson) {
        log.debug("Creating contact person {}", contactPerson);
    }

    @HandleBeforeSave
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    public void checkUpdateAuthority(ContactPerson contactPerson) {
        log.debug("Updating contact person {}", contactPerson);
    }

    @HandleBeforeDelete
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    public void checkDeleteAuthority(ContactPerson contactPerson) {
        log.debug("Deleting contact person {}", contactPerson);
    }
}
