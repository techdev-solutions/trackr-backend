package de.techdev.trackr.domain.employee.vacation.support;

import de.techdev.trackr.core.mail.MailService;
import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.employee.vacation.VacationRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;

import static org.echocat.jomon.testing.StringMatchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class VacationRequestNotifyServiceTest {

    @InjectMocks
    private VacationRequestNotifyService vacationRequestNotifyService;

    @Mock
    private MailService mailService;

    private VacationRequest vacationRequest;

    @Before
    public void setUp() throws Exception {
        vacationRequest = new VacationRequest();
        Employee employee = new Employee();
        employee.setEmail("employee@techdev.de");
        vacationRequest.setEmployee(employee);
    }

    @Test
    public void sendNotificationApproved() throws Exception {
        ArgumentCaptor<SimpleMailMessage> mail = ArgumentCaptor.forClass(SimpleMailMessage.class);

        vacationRequest.setStatus(VacationRequest.VacationRequestStatus.APPROVED);
        vacationRequestNotifyService.sendEmailNotification(vacationRequest);
        verify(mailService, times(1)).sendMail(mail.capture());

        assertThat(mail.getValue().getSubject(), contains("approved"));
    }

    @Test
    public void sendNotificationRejected() throws Exception {
        ArgumentCaptor<SimpleMailMessage> mail = ArgumentCaptor.forClass(SimpleMailMessage.class);

        vacationRequest.setStatus(VacationRequest.VacationRequestStatus.REJECTED);
        vacationRequestNotifyService.sendEmailNotification(vacationRequest);
        verify(mailService, times(1)).sendMail(mail.capture());

        assertThat(mail.getValue().getSubject(), contains("rejected"));
    }
}
