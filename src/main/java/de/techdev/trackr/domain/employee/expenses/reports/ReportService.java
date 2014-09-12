package de.techdev.trackr.domain.employee.expenses.reports;

import de.techdev.trackr.domain.employee.login.Credential;
import de.techdev.trackr.domain.employee.login.CredentialRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * @author Moritz Schulze
 */
@Transactional
public class ReportService {

    @Autowired
    private ReportRepository travelExpenseReportRepository;

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private ReportNotifyService travelExpenseReportNotifyService;

    @PreAuthorize("#travelExpenseReport.employee.id == principal?.id")
    public Report submit(Report travelExpenseReport) {
        travelExpenseReportNotifyService.sendSubmittedReportMail(travelExpenseReport);
        return setStatusOnTravelExpenseReport(travelExpenseReport, Report.Status.SUBMITTED, null);
    }

    @PreAuthorize("hasRole('ROLE_SUPERVISOR') and #travelExpenseReport.employee.id != principal?.id")
    public Report accept(Report travelExpenseReport, String approverName) {
        return setStatusOnTravelExpenseReport(travelExpenseReport, Report.Status.APPROVED, approverName);
    }

    @PreAuthorize("hasRole('ROLE_SUPERVISOR') and #travelExpenseReport.employee.id != principal?.id")
    public Report reject(Report travelExpenseReport, String rejecterName) {
        return setStatusOnTravelExpenseReport(travelExpenseReport, Report.Status.REJECTED, rejecterName);
    }

    private Report setStatusOnTravelExpenseReport(Report travelExpenseReport, Report.Status status, String approverName) {
        if (status == Report.Status.SUBMITTED) {
            travelExpenseReport.setSubmissionDate(Instant.now());
        } else {
            Credential approver = credentialRepository.findByEmail(approverName);
            travelExpenseReport.setApprover(approver.getEmployee());
            travelExpenseReport.setApprovalDate(Instant.now());
        }
        travelExpenseReport.setStatus(status);
        return travelExpenseReportRepository.save(travelExpenseReport);
    }
}
