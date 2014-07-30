package de.techdev.trackr.domain.employee.expenses;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Moritz Schulze
 */
@Transactional
public interface TravelExpenseReportService {

    @PreAuthorize("#travelExpenseReport.employee.id == principal.id")
    TravelExpenseReport submit(TravelExpenseReport travelExpenseReport);

    @PreAuthorize("hasRole('ROLE_SUPERVISOR') and #travelExpenseReport.employee.id != principal.id")
    TravelExpenseReport accept(TravelExpenseReport travelExpenseReport, String approverName);

    @PreAuthorize("hasRole('ROLE_SUPERVISOR') and #travelExpenseReport.employee.id != principal.id")
    TravelExpenseReport reject(TravelExpenseReport travelExpenseReport, String rejecterName);
}
