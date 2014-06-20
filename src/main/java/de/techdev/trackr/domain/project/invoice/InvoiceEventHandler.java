package de.techdev.trackr.domain.project.invoice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;

import static de.techdev.trackr.util.LocalDateUtil.fromLocalDate;

/**
 * @author Moritz Schulze
 */
@RepositoryEventHandler(Invoice.class)
@Slf4j
public class InvoiceEventHandler {

    @HandleBeforeCreate
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void authorizeCreate(Invoice invoice) {
        setInvocieStateIfNecessary(invoice);
        log.debug("Creating invoice {}", invoice);
    }

    @HandleBeforeSave
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void authorizeUpdate(Invoice invoice) {
        setInvocieStateIfNecessary(invoice);
        log.debug("Updating invoice {}", invoice);
    }

    @HandleBeforeDelete
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void authorizeDelete(Invoice invoice) {
        log.debug("Deleting invoice {}", invoice);
    }

    /**
     * Sets the invoice state to OVERDUE if the due date is before today.
     * @param invoice
     */
    private void setInvocieStateIfNecessary(Invoice invoice) {
        if (invoice.getDueDate() != null) {
            LocalDate today = LocalDate.now();
            if (invoice.getDueDate().before(fromLocalDate(today))) {
                invoice.setInvoiceState(InvoiceState.OVERDUE);
            } else {
                invoice.setInvoiceState(InvoiceState.OUTSTANDING);
            }
        }
    }
}
