package de.techdev.trackr.domain.employee.expenses.support;

import de.techdev.trackr.domain.employee.expenses.TravelExpenseReport;
import de.techdev.trackr.domain.employee.expenses.TravelExpenseReportRepository;
import de.techdev.trackr.domain.employee.expenses.TravelExpenseReportService;
import de.techdev.trackr.domain.employee.expenses.TravelExpenseReportStatus;
import de.techdev.trackr.domain.employee.login.Credential;
import de.techdev.trackr.domain.employee.login.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author Moritz Schulze
 */
public class TravelExpenseReportServiceImpl implements TravelExpenseReportService {

    @Autowired
    private TravelExpenseReportRepository travelExpenseReportRepository;

    @Autowired
    private CredentialRepository credentialRepository;

    @Override
    public TravelExpenseReport submit(TravelExpenseReport travelExpenseReport) {
        return setStatusOnTravelExpenseReport(travelExpenseReport, TravelExpenseReportStatus.SUBMITTED, null);
    }

    @Override
    public TravelExpenseReport accept(TravelExpenseReport travelExpenseReport, String approverName) {
        return setStatusOnTravelExpenseReport(travelExpenseReport, TravelExpenseReportStatus.APPROVED, approverName);
    }

    @Override
    public TravelExpenseReport reject(TravelExpenseReport travelExpenseReport, String rejecterName) {
        return setStatusOnTravelExpenseReport(travelExpenseReport, TravelExpenseReportStatus.REJECTED, rejecterName);
    }

    private TravelExpenseReport setStatusOnTravelExpenseReport(TravelExpenseReport travelExpenseReport, TravelExpenseReportStatus status, String approverName) {
        if (status == TravelExpenseReportStatus.SUBMITTED) {
            travelExpenseReport.setSubmissionDate(new Date());
        } else {
            Credential approver = credentialRepository.findByEmail(approverName);
            travelExpenseReport.setApprover(approver.getEmployee());
            travelExpenseReport.setApprovalDate(new Date());
        }
        travelExpenseReport.setStatus(status);
        return travelExpenseReportRepository.save(travelExpenseReport);
    }
}
