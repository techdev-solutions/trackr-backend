package de.techdev.trackr.domain.employee.vacation.support;

import de.techdev.trackr.IntegrationTest;
import de.techdev.trackr.core.mail.MailConfiguration;
import de.techdev.trackr.domain.ApiBeansConfiguration;
import de.techdev.trackr.domain.employee.vacation.VacationRequest;
import de.techdev.trackr.domain.employee.vacation.VacationRequestDataOnDemand;
import de.techdev.trackr.domain.employee.vacation.VacationRequestStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author Moritz Schulze
 */
@ContextConfiguration(classes = {MailConfiguration.class, ApiBeansConfiguration.class})
public class VacationRequestNotifyServiceIntegrationTest extends IntegrationTest {

    @Autowired
    private VacationRequestDataOnDemand vacationRequestDataOnDemand;

    @Autowired
    private VacationRequestNotifyService vacationRequestNotifyService;

    @Test
    public void sendNotificationApproved() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        vacationRequest.setStatus(VacationRequestStatus.APPROVED);
        vacationRequestNotifyService.sendEmailNotification(vacationRequest);
    }

    @Test
    public void sendNotificationRejected() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        vacationRequest.setStatus(VacationRequestStatus.REJECTED);
        vacationRequestNotifyService.sendEmailNotification(vacationRequest);
    }
}
