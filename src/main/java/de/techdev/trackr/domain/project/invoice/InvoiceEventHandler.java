package de.techdev.trackr.domain.project.invoice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Moritz Schulze
 */
@RepositoryEventHandler(Invoice.class)
@Slf4j
public class InvoiceEventHandler {

    @HandleBeforeCreate
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void authorizeCreate(Invoice invoice) {
        log.debug("Creating invoice {}", invoice);
    }

    @HandleBeforeSave
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void authorizeUpdate(Invoice invoice) {
        log.debug("Updating invoice {}", invoice);
    }
}
