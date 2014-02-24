package de.techdev.trackr.repository;

import de.techdev.trackr.domain.ContactPerson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Moritz Schulze
 */
@RepositoryEventHandler(ContactPerson.class)
public class ContactPersonEventHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @HandleBeforeCreate
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    public void checkCreateAuthority(ContactPerson contactPerson) {
        logger.debug("Creating contact person {}", contactPerson);
    }

    @HandleBeforeDelete
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    public void checkDeleteAuthority(ContactPerson contactPerson) {
        logger.debug("Deleting contact person {}", contactPerson);
    }
}
