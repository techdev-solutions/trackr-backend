package de.techdev.trackr.domain.project.invoice;

import de.techdev.trackr.domain.company.Company;

import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Moritz Schulze
 */
@Projection(types = Invoice.class, name = "withDebitor")
public interface InvoiceWithDebitorProjection {
    Long getId();

    Integer getVersion();

    String getIdentifier();

    LocalDate getCreationDate();

    BigDecimal getInvoiceTotal();

    Company getDebitor();

    LocalDate getDueDate();

    Invoice.InvoiceState getInvoiceState();
}
