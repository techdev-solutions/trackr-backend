package de.techdev.trackr.domain.employee.sickdays;

import static java.time.format.DateTimeFormatter.ofPattern;
import de.techdev.trackr.core.mail.MailService;
import de.techdev.trackr.domain.employee.login.support.SupervisorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

import java.time.format.DateTimeFormatter;

/**
 * @author Moritz Schulze
 */
public class SickDaysNotifyService {

    @Autowired
    private MailService mailService;

    @Autowired
    private SupervisorService supervisorService;
    DateTimeFormatter GERMAN_DATE_FORMAT = ofPattern("dd.MM.yyyy");

    public void notifySupervisorsAboutNew(SickDays sickDays) {
        String subject = "Sick days reported by " + sickDays.getEmployee().fullName();
        String text = sickDays.getEmployee().fullName() + " reported sick days from " + GERMAN_DATE_FORMAT.format(sickDays.getStartDate())
                + " to " + (sickDays.getEndDate() != null ? GERMAN_DATE_FORMAT.format(sickDays.getEndDate()) : " (not set)" ) + ".";
        notifySupervisors(subject, text);
    }

    public void notifySupervisorsAboutUpdate(SickDays sickDays) {
        String subject = "Sick days updated by " + sickDays.getEmployee().fullName();
        String text = sickDays.getEmployee().fullName() + " updated his sick days. Interval is now from " + GERMAN_DATE_FORMAT.format(sickDays.getStartDate())
                + " to " + (sickDays.getEndDate() != null ? GERMAN_DATE_FORMAT.format(sickDays.getEndDate()) : " (not set)") + ".";
        notifySupervisors(subject, text);
    }

    protected void notifySupervisors(String subject, String text) {
        String[] receiver = supervisorService.getSupervisorEmailsAsArray();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject(subject);
        mailMessage.setTo(receiver);
        mailMessage.setText(text);
        mailMessage.setFrom("no-reply@techdev.de");
        mailService.sendMail(mailMessage);
    }
}
