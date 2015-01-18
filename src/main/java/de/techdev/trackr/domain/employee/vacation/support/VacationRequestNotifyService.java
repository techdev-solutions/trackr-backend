package de.techdev.trackr.domain.employee.vacation.support;

import de.techdev.trackr.core.mail.MailService;
import de.techdev.trackr.domain.employee.login.support.SupervisorService;
import de.techdev.trackr.domain.employee.vacation.VacationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

import java.text.SimpleDateFormat;
import java.util.UUID;

public class VacationRequestNotifyService {

    @Autowired
    private MailService mailService;

    @Autowired
    private SupervisorService supervisorService;

    /**
     * Sends an approval or rejection mail depending on the status of the parameter.
     * @param request The request for which the mail is to sent.
     */
    public void sendEmailNotification(VacationRequest request) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("no-reply@techdev.de");
        mailMessage.setTo(request.getEmployee().getEmail());
        mailMessage.setSubject("Your vacation request has been " + statusPastVerb(request.getStatus()));
        mailMessage.setText(getStatusMailText(request));
        mailService.sendMail(mailMessage);
    }

    protected String statusPastVerb(VacationRequest.VacationRequestStatus status) {
        if(status == VacationRequest.VacationRequestStatus.APPROVED) {
            return "approved";
        } else if (status == VacationRequest.VacationRequestStatus.REJECTED) {
            return "rejected";
        } else {
            return "is pending";
        }
    }

    protected String getStatusMailText(VacationRequest request) {
        if(request.getApprover() == null) {
            return "Your vacation request has been automatically approved.";
        } else {
            return request.getApprover().fullName() + " has " + statusPastVerb(request.getStatus()) + " your vacation request from " + request.getSubmissionTime();
        }
    }

    /**
     * Send a new vacation request notification to all supervisors.
     */
    public void notifySupervisors(VacationRequest vacationRequest, UUID uuid) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String[] receiver = supervisorService.getSupervisorEmailsAsArray();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String subject = "New vacation request from " + vacationRequest.getEmployee().fullName() + "; " + uuid.toString();
        String text = "New vacation request from " + vacationRequest.getEmployee().fullName() + " for " + sdf.format(vacationRequest.getStartDate()) + " - " + sdf
                .format(vacationRequest.getEndDate()) + ". You can reply to this email with approve or reject to do that.";
        mailMessage.setSubject(subject);
        mailMessage.setTo(receiver);
        mailMessage.setText(text);
        mailMessage.setFrom("no-reply@techdev.de");
        mailService.sendMail(mailMessage);
    }
}
