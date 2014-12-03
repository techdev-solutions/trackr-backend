package de.techdev.trackr.domain.employee.expenses.reports;

import de.techdev.trackr.core.mail.MailService;
import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.employee.expenses.TravelExpense;
import de.techdev.trackr.domain.employee.login.support.SupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Moritz Schulze
 * @author Alexander Hanschke
 */
public class ReportNotifyService {

    @Autowired
    private MailService mailService;

    @Autowired
    private SupervisorService supervisorService;

    @Autowired
    private ReportRepository travelExpenseReportRepository;

    @Value("${trackr.frontendUrl}")
    private String frontendUrl;

    /**
     * Sends a mail to all supervisors except the owning for a submitted travel expense report.
     *
     * @param report The report for which the mail is to be sent.
     */
    public void sendSubmittedReportMail(Report report) {
        String[] emails = supervisorService.getSupervisorEmailsArrayWithout(credential ->
                        !report.getEmployee().getCredential().getEmail().equals(credential.getEmail())
        );

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("no-reply@techdev.de");
        mailMessage.setTo(emails);
        mailMessage.setSubject("New travel expense report by " + fullName(report.getEmployee()));
        mailMessage.setText(
                String.format("%s submitted a new travel expense report (#%d) totalling %.2f.\n\nGo to %s to approve or reject it.",
                        fullName(report.getEmployee()), report.getId(), getTotalAmount(report), getWebLink(report))
        );
        mailService.sendMail(mailMessage);
    }

    public void sendApprovedReportMail(Report report) {
        notifyEmployeeOnStatusChange(report, "approved");
    }

    public void sendRejectedReportMail(Report report) {
        notifyEmployeeOnStatusChange(report, "rejected");
    }

    private void notifyEmployeeOnStatusChange(Report report, String outcome) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("no-reply@techdev.de");
        mailMessage.setTo(report.getEmployee().getCredential().getEmail());
        mailMessage.setSubject(String.format("Your travel expense report has been %s", outcome));
        mailMessage.setText(
                String.format("%s has %s your travel expense report #%d.",
                        fullName(report.getApprover()), outcome, report.getId())
        );
        mailService.sendMail(mailMessage);
    }

    protected String getWebLink(Report report) {
        return frontendUrl + "/supervisor/expenses/" + report.getId();
    }

    @Transactional
    protected BigDecimal getTotalAmount(Report report) {
        // Since the report passed to this method is not attached to the JPA session anymore we have to reload it to get
        // the expenses which are lazily fetched.
        List<TravelExpense> expenses = travelExpenseReportRepository.findOne(report.getId()).getExpenses();
        return expenses.stream()
                .map(TravelExpense::getCost)
                .reduce(BigDecimal.ZERO, (bd1, bd2) -> bd1.add(bd2));
    }

    protected String fullName(Employee employee) {
        return employee.getFirstName() + " " + employee.getLastName();
    }

}
