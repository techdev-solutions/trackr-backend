package de.techdev.trackr.domain.employee.vacation.support;

import de.techdev.trackr.IntegrationTest;
import de.techdev.trackr.domain.ApiBeansConfiguration;
import de.techdev.trackr.domain.employee.vacation.VacationRequest;
import de.techdev.trackr.domain.employee.vacation.VacationRequestDataOnDemand;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {ApiBeansConfiguration.class})
public class VacationRequestNotifyServiceIntegrationTest extends IntegrationTest {

    @Autowired
    private VacationRequestDataOnDemand vacationRequestDataOnDemand;

    @Autowired
    private VacationRequestNotifyService vacationRequestNotifyService;

    @Test
    public void sendNotificationApproved() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        vacationRequest.setStatus(VacationRequest.VacationRequestStatus.APPROVED);
        vacationRequestNotifyService.sendEmailNotification(vacationRequest);
    }

    @Test
    public void sendNotificationRejected() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        vacationRequest.setStatus(VacationRequest.VacationRequestStatus.REJECTED);
        vacationRequestNotifyService.sendEmailNotification(vacationRequest);
    }
}
