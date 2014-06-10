package de.techdev.trackr.domain.project.invoice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Date;
import java.util.List;

/**
 * @author Moritz Schulze
 */
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    public Page<Invoice> findByInvoiceState(@Param("state") InvoiceState state, Pageable pageable);

    @RestResource(exported = false)
    public List<Invoice> findByDueDateBeforeAndInvoiceState(Date date, InvoiceState invoiceState);
}
