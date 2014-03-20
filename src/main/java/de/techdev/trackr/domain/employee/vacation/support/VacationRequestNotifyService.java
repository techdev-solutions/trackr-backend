package de.techdev.trackr.domain.employee.vacation.support;

import de.techdev.trackr.core.mail.MailService;
import de.techdev.trackr.domain.employee.vacation.VacationRequest;
import de.techdev.trackr.domain.employee.vacation.VacationRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

/**
 * @author Moritz Schulze
 */
public class VacationRequestNotifyService {

    @Autowired
    private MailService mailService;

    public void sendEmailNotification(VacationRequest request) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("no-reply@techdev.de");
        mailMessage.setTo(request.getEmployee().getCredential().getEmail());
        mailMessage.setSubject("Your vacation request has been " + statusPastVerb(request.getStatus()));
        mailMessage.setText(getMailText(request));
        mailService.sendMail(mailMessage);
    }

    protected String statusPastVerb(VacationRequestStatus status) {
        if(status == VacationRequestStatus.APPROVED) {
            return "approved";
        } else if (status == VacationRequestStatus.REJECTED) {
            return "rejected";
        } else {
            return "is pending";
        }
    }

    protected String getMailText(VacationRequest request) {
        if(request.getApprover() == null) {
            return "Your vacation request has been automatically approved.";
        } else {
            return request.getApprover().fullName() + " has " + statusPastVerb(request.getStatus()) + " your vacation request from " + request.getSubmissionTime();
        }
    }
}
