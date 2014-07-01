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
    public TravelExpenseReport submit(TravelExpenseReport travelExpenseReport) {
        return setStatusOnTravelExpenseReport(travelExpenseReport, TravelExpenseReportStatus.SUBMITTED);
    }

    @Override
    public TravelExpenseReport accept(TravelExpenseReport travelExpenseReport) {
        return setStatusOnTravelExpenseReport(travelExpenseReport, TravelExpenseReportStatus.APPROVED);
    }

    @Override
    public TravelExpenseReport reject(TravelExpenseReport travelExpenseReport) {
        return setStatusOnTravelExpenseReport(travelExpenseReport, TravelExpenseReportStatus.REJECTED);
    }

    private TravelExpenseReport setStatusOnTravelExpenseReport(TravelExpenseReport travelExpenseReport, TravelExpenseReportStatus status) {
        travelExpenseReport.setStatus(status);
        return travelExpenseReportRepository.save(travelExpenseReport);
    }
}
