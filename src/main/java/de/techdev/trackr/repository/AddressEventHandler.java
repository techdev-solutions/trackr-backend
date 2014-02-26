package de.techdev.trackr.repository;

import de.techdev.trackr.domain.Address;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Moritz Schulze
 */
@RepositoryEventHandler(Address.class)
@Slf4j
public class AddressEventHandler {

    @HandleBeforeCreate
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkSaveAuthority(Address address) {
        log.debug("Updating address {}", address);
    }
}
