package de.techdev.trackr.domain.project.invoice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author Moritz Schulze
 */
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    public Page<Invoice> findByInvoiceState(@Param("state") InvoiceState state, Pageable pageable);

}
