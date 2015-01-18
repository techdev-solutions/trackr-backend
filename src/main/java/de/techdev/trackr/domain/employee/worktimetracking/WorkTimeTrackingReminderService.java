package de.techdev.trackr.domain.employee.worktimetracking;

import de.techdev.trackr.core.mail.MailService;
import de.techdev.trackr.domain.common.FederalState;
import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.util.List;

/**
 * Remind employees to track their working times.
 */
public class WorkTimeTrackingReminderService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MailService mailService;

    @Value("${trackr.frontendUrl}")
    private String frontendUrl;

    /**
     * Remind all employees of a federal state to track their working times via mail. Since holidays differ in different states this needs the state.
     * @param state The federal state to select employees from.
     */
    public void remindEmployeesToTrackWorkTimes(FederalState state) {
        List<Employee> allEmployees = employeeRepository.findByFederalState(state);
        allEmployees.forEach(employee -> {
            SimpleMailMessage mailMessage = getReminderMailMessage(employee);
            mailService.sendMail(mailMessage);
        });
    }

    protected SimpleMailMessage getReminderMailMessage(Employee employee) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(employee.getEmail());
        mailMessage.setFrom("no-reply@techdev.de");
        mailMessage.setSubject("Track your working time");
        mailMessage.setText("Please make sure to track your working time by the end of the month: " + frontendUrl + "/employee/timesheet");
        return mailMessage;
    }
}
