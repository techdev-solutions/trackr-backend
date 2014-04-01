package de.techdev.trackr.domain.employee.worktimetracking;

import de.techdev.trackr.core.mail.MailService;
import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

import java.util.List;

/**
 * Remind employees to track their working times.
 *
 * @author Moritz Schulze
 */
public class WorkTimeTrackingReminderService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MailService mailService;

    public void remindEmployeesToTrackWorkTimes() {
        List<Employee> allEmployees = employeeRepository.findAll();
        allEmployees.forEach(employee -> {
            SimpleMailMessage mailMessage = getReminderMailMessage(employee);
            mailService.sendMail(mailMessage);
        });
    }

    protected SimpleMailMessage getReminderMailMessage(Employee employee) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(employee.getCredential().getEmail());
        mailMessage.setFrom("no-reply@techdev.de");
        mailMessage.setSubject("Track your working time");
        mailMessage.setText("Please make sure to track your working time by the end of the month.");
        return mailMessage;
    }
}
