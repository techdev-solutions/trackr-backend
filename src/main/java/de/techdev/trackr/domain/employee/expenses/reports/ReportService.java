package de.techdev.trackr.domain.employee.expenses.reports;

import de.techdev.trackr.domain.employee.login.Credential;
import de.techdev.trackr.domain.employee.login.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
        Report acceptedReport = setStatusOnTravelExpenseReport(travelExpenseReport, Report.Status.APPROVED, approverName);
        travelExpenseReportNotifyService.sendApprovedReportMail(acceptedReport);
        return acceptedReport;
    }

    @PreAuthorize("hasRole('ROLE_SUPERVISOR') and #travelExpenseReport.employee.id != principal?.id")
    public Report reject(Report travelExpenseReport, String rejecterName) {
        Report rejectedReport = setStatusOnTravelExpenseReport(travelExpenseReport, Report.Status.REJECTED, rejecterName);
        travelExpenseReportNotifyService.sendRejectedReportMail(rejectedReport);
        return rejectedReport;
    }

    private Report setStatusOnTravelExpenseReport(Report travelExpenseReport, Report.Status status, String approverName) {
        if (status == Report.Status.SUBMITTED) {
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
