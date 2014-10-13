package de.techdev.trackr.domain.project.invoice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;
import  java.util.List;

/**
 * @author Moritz Schulze
 */
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Invoice findOne(Long aLong);

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Page<Invoice> findAll(Pageable pageable);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Page<Invoice> findByInvoiceState(@Param("state") Invoice.InvoiceState state, Pageable pageable);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Page<Invoice> findByIdentifierLikeIgnoreCaseAndInvoiceState(@Param("identifier") String identifier, @Param("state") Invoice.InvoiceState state, Pageable pageable);

    //TODO Test
    @RestResource(exported = false)
    List<Invoice> findByDueDateBeforeAndInvoiceState(LocalDate date, Invoice.InvoiceState invoiceState);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<Invoice> findByCreationDateBetween(@Param("start") LocalDate start, @Param("end") LocalDate end, @Param("sort") Sort sort);
}
