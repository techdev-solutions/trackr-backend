package de.techdev.trackr.domain.project.invoice;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Moritz Schulze
 */
public class ChangeStateService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public Invoice changeState(Invoice invoice, Invoice.InvoiceState invoiceState) {
        invoice.setInvoiceState(invoiceState);
        return invoiceRepository.save(invoice);
    }
}
