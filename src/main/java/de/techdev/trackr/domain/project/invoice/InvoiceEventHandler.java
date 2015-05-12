package de.techdev.trackr.domain.project.invoice;

import de.techdev.trackr.domain.company.Company;
import de.techdev.trackr.util.LocalDateUtil;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;

import static de.techdev.trackr.util.LocalDateUtil.fromLocalDate;

/**
 * @author Moritz Schulze
 */
@RepositoryEventHandler(Invoice.class)
@SuppressWarnings("unused")
public class InvoiceEventHandler {

    @HandleBeforeCreate
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void authorizeCreate(Invoice invoice) {
        setDueDateFromTimeForPayment(invoice);
        setInvoiceStateIfNecessary(invoice);
    }

    @HandleBeforeSave
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void authorizeUpdate(Invoice invoice) {
        setDueDateFromTimeForPayment(invoice);
        setInvoiceStateIfNecessary(invoice);
    }

    @HandleBeforeDelete
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void authorizeDelete(Invoice invoice) {
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
                invoice.setInvoiceState(Invoice.InvoiceState.OVERDUE);
            } else {
                invoice.setInvoiceState(Invoice.InvoiceState.OUTSTANDING);
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
