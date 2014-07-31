package de.techdev.trackr.domain.project.invoice;

import de.techdev.trackr.domain.company.Company;
import de.techdev.trackr.util.LocalDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.*;
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
        setDueDateFromTimeForPayment(invoice);
        setInvoiceStateIfNecessary(invoice);
        log.debug("Creating invoice {}", invoice);
    }

    @HandleBeforeSave
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void authorizeUpdate(Invoice invoice) {
        setDueDateFromTimeForPayment(invoice);
        setInvoiceStateIfNecessary(invoice);
        log.debug("Updating invoice {}", invoice);
    }

    @HandleBeforeDelete
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void authorizeDelete(Invoice invoice) {
        log.debug("Deleting invoice {}", invoice);
    }

    @HandleBeforeLinkSave
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void linkSave(Invoice invoice, Object links) {
        if (links instanceof Company) {
            setDueDateFromTimeForPayment(invoice);
            setInvoiceStateIfNecessary(invoice);
        }
    }

    /**
     * Sets the invoice state to OVERDUE if the due date is before today.
     */
    protected void setInvoiceStateIfNecessary(Invoice invoice) {
        if (invoice.getDueDate() != null) {
            LocalDate today = LocalDate.now();
            if (invoice.getDueDate().before(fromLocalDate(today))) {
                invoice.setInvoiceState(InvoiceState.OVERDUE);
            } else {
                invoice.setInvoiceState(InvoiceState.OUTSTANDING);
            }
        }
    }

    /**
     * Set the due date automatically if it is not set and the debitor of the invoice has a timeForPayment.
     */
    protected void setDueDateFromTimeForPayment(Invoice invoice) {
        if (invoice.getDueDate() == null && invoice.getDebitor() != null && invoice.getDebitor().getTimeForPayment() != null) {
            LocalDate creationDate = LocalDateUtil.fromDate(invoice.getCreationDate());
            LocalDate dueDate = creationDate.plusDays(invoice.getDebitor().getTimeForPayment());
            invoice.setDueDate(LocalDateUtil.fromLocalDate(dueDate));
        }
    }
}
