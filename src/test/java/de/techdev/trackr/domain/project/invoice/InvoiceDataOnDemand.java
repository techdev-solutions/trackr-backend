package de.techdev.trackr.domain.project.invoice;

import de.techdev.trackr.domain.AbstractDataOnDemand;
import de.techdev.trackr.domain.company.CompanyDataOnDemand;

import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Moritz Schulze
 */
public class InvoiceDataOnDemand extends AbstractDataOnDemand<Invoice> {

    @Autowired
    private CompanyDataOnDemand companyDataOnDemand;

    @Override
    public Invoice getNewTransientObject(int i) {
        Invoice invoice = new Invoice();
        invoice.setIdentifier("identifier_" + i);
        invoice.setCreationDate(LocalDate.now());
        invoice.setDebitor(companyDataOnDemand.getRandomObject());
        invoice.setDueDate(LocalDate.now());
        invoice.setInvoiceState(Invoice.InvoiceState.OUTSTANDING);
        invoice.setInvoiceTotal(BigDecimal.ONE);
        return invoice;
    }

}
