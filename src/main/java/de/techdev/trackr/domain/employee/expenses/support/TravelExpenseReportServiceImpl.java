package de.techdev.trackr.domain.employee.expenses.support;

import de.techdev.trackr.domain.employee.expenses.TravelExpenseReport;
import de.techdev.trackr.domain.employee.expenses.TravelExpenseReportRepository;
import de.techdev.trackr.domain.employee.expenses.TravelExpenseReportService;
import de.techdev.trackr.domain.employee.expenses.TravelExpenseReportStatus;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Moritz Schulze
 */
public class TravelExpenseReportServiceImpl implements TravelExpenseReportService {

    @Autowired
    private TravelExpenseReportRepository travelExpenseReportRepository;

    @Override
    public TravelExpenseReport submit(Long id) {
        return setStatusOnTravelExpenseReport(id, TravelExpenseReportStatus.SUBMITTED);
    }

    @Override
    public TravelExpenseReport accept(Long id) {
        return setStatusOnTravelExpenseReport(id, TravelExpenseReportStatus.ACCEPTED);
    }

    @Override
    public TravelExpenseReport reject(Long id) {
        return setStatusOnTravelExpenseReport(id, TravelExpenseReportStatus.REJECTED);
    }

    private TravelExpenseReport setStatusOnTravelExpenseReport(Long id, TravelExpenseReportStatus status) {
        TravelExpenseReport travelExpenseReport = travelExpenseReportRepository.findOne(id);
        travelExpenseReport.setStatus(status);
        return travelExpenseReportRepository.save(travelExpenseReport);
    }
}
