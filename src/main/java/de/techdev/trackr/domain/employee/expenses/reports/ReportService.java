package de.techdev.trackr.domain.employee.expenses.reports;

import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
public class ReportService {

    @Autowired
    private ReportRepository travelExpenseReportRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ReportNotifyService travelExpenseReportNotifyService;

    @PreAuthorize("#travelExpenseReport.employee.email == principal?.username")
    public Report submit(Report travelExpenseReport) {
        travelExpenseReportNotifyService.notifySupervisorsOnSubmission(travelExpenseReport);
        return setStatusOnTravelExpenseReport(travelExpenseReport, Report.Status.SUBMITTED, null);
    }

    @PreAuthorize("hasRole('ROLE_SUPERVISOR') and #travelExpenseReport.employee.email != principal?.username")
    public Report accept(Report travelExpenseReport, String approverName) {
        Report acceptedReport = setStatusOnTravelExpenseReport(travelExpenseReport, Report.Status.APPROVED, approverName);
        travelExpenseReportNotifyService.notifyEmployeeOnApproval(acceptedReport);
        return acceptedReport;
    }

    @PreAuthorize("hasRole('ROLE_SUPERVISOR') and #travelExpenseReport.employee.email != principal?.username")
    public Report reject(Report travelExpenseReport, String rejecterName) {
        Report rejectedReport = setStatusOnTravelExpenseReport(travelExpenseReport, Report.Status.REJECTED, rejecterName);
        travelExpenseReportNotifyService.notifyEmployeeOnRejection(rejectedReport);
        return rejectedReport;
    }

    private Report setStatusOnTravelExpenseReport(Report travelExpenseReport, Report.Status status, String approverName) {
        if (status == Report.Status.SUBMITTED) {
            travelExpenseReport.setSubmissionDate(new Date());
        } else {
            Employee approver = employeeRepository.findByEmail(approverName);
            travelExpenseReport.setApprover(approver);
            travelExpenseReport.setApprovalDate(new Date());
        }
        travelExpenseReport.setStatus(status);
        return travelExpenseReportRepository.save(travelExpenseReport);
    }
}
