package de.techdev.trackr.domain.project.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;

/**
 * @author Moritz Schulze
 */
public class InvoiceScheduledJob {

    @Autowired
    private InvoiceOverdueService invoiceOverdueService;

    @Scheduled(cron = "0 0 1 * * *")
    public void markOverdueInvoices() {
        LocalDate today = LocalDate.now();
        invoiceOverdueService.markOverdueInvoices(today);
    }
}
