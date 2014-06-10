package de.techdev.trackr.domain.project.invoice;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Moritz Schulze
 */
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}
