package de.techdev.trackr.repository;

import de.techdev.trackr.domain.Address;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * This class prevents other users than admins from creating, updating or deleting addresses.
 * <p>
 * As long as only {@link de.techdev.trackr.domain.Company} uses the Address class this is fine, if e.g. {@link de.techdev.trackr.employee.Employee}
 * gets an address too this will have to be thought over.
 *
 * @author Moritz Schulze
 */
@RepositoryEventHandler(Address.class)
@Slf4j
public class AddressEventHandler {

    @HandleBeforeCreate
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkSaveAuthority(Address address) {
        log.debug("Creating address {}", address);
    }

    @HandleBeforeSave
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkUpdateAuthority(Address address) {
        log.debug("Updating address {}", address);
    }

    @HandleBeforeDelete
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkDeleteAuthority(Address address) {
        log.debug("Deleting address {}", address);
    }
}
