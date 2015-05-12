package de.techdev.trackr.domain.employee.vacation.support;

import de.techdev.trackr.domain.employee.vacation.VacationRequest;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MailApproveServiceTest {

    private MailApproveService mailApproveService;

    @Before
    public void setUp() throws Exception {
        mailApproveService = new MailApproveService();
    }

    @Test
    public void approveOrReject_approve() throws Exception {
        VacationRequest.VacationRequestStatus status = mailApproveService.approveOrReject("approve\nyou can answer approve or reject");
        assertThat(status, is(VacationRequest.VacationRequestStatus.APPROVED));
    }

    @Test
    public void approveOrReject_reject() throws Exception {
        VacationRequest.VacationRequestStatus status = mailApproveService.approveOrReject("reject\nyou can answer approve or reject");
        assertThat(status, is(VacationRequest.VacationRequestStatus.REJECTED));
    }

    @Test(expected = IllegalStateException.class)
    public void approveOrReject_exception() throws Exception {
        mailApproveService.approveOrReject("you can answer approve or reject");
    }
}