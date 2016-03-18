package de.techdev.trackr.domain.company;

import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Moritz Schulze
 */
@RepositoryEventHandler(ContactPerson.class)
@SuppressWarnings("unused")
public class ContactPersonEventHandler {

    @HandleBeforeCreate
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    public void checkCreateAuthority(ContactPerson contactPerson) {
    }

    @HandleBeforeSave
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    public void checkUpdateAuthority(ContactPerson contactPerson) {
    }

    @HandleBeforeDelete
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    public void checkDeleteAuthority(ContactPerson contactPerson) {
    }

    @HandleBeforeLinkSave
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    public void checkLinkUpdateAuthority(ContactPerson contactPerson, Object links) {
        // only security check
    }

    @HandleBeforeLinkDelete
    @PreAuthorize("denyAll()")
    public void checkLinkDeleteAuthority(ContactPerson contactPerson, Object linkedEntity) {
        // only security check
    }
}
