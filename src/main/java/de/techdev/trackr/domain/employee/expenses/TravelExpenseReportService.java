package de.techdev.trackr.domain.employee.expenses;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Moritz Schulze
 */
@Transactional
public interface TravelExpenseReportService {

    @PostAuthorize("returnObject.employee.id == principal.id")
    TravelExpenseReport submit(Long id);

    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    @PostAuthorize("returnObject.employee.id != principal.id")
    TravelExpenseReport accept(Long id);

    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    @PostAuthorize("returnObject.employee.id != principal.id")
    TravelExpenseReport reject(Long id);
}
