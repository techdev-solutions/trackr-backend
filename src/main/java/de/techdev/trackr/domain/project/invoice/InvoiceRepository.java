package de.techdev.trackr.domain.project.invoice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Date;
import java.util.List;

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
    Page<Invoice> findByInvoiceState(@Param("state") InvoiceState state, Pageable pageable);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Page<Invoice> findByIdentifierLikeIgnoreCaseAndInvoiceState(@Param("identifier") String identifier, @Param("state") InvoiceState state, Pageable pageable);

    @RestResource(exported = false)
    List<Invoice> findByDueDateBeforeAndInvoiceState(Date date, InvoiceState invoiceState);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<Invoice> findByCreationDateBetween(@Param("start") Date start, @Param("end") Date end, @Param("sort") Sort sort);
}
