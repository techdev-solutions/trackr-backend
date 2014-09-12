package de.techdev.trackr.domain.project.invoice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


/**
 * Marks invoices as overdue if the due date is after today.
 * @author Moritz Schulze
 */
@Slf4j
public class InvoiceOverdueService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    /**
     * Mark all outstanding invoices with a due date after expiry date as overdue.
     * @param expiryDate The date to use as a bound.
     */
    @Transactional
    public void markOverdueInvoices(LocalDate expiryDate) {
        List<Invoice> invoices = invoiceRepository.findByDueDateBeforeAndInvoiceState(expiryDate, Invoice.InvoiceState.OUTSTANDING);
        for (Invoice invoice : invoices) {
            log.info("Setting state to overdue on invoice {}", invoice);
            invoice.setInvoiceState(Invoice.InvoiceState.OVERDUE);
            invoiceRepository.save(invoice);
        }
    }

}
