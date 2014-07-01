package de.techdev.trackr.domain.employee.vacation;

import de.techdev.trackr.IntegrationTest;
import de.techdev.trackr.core.mail.MailConfiguration;
import de.techdev.trackr.core.security.AuthorityMocks;
import de.techdev.trackr.core.security.MethodSecurityConfiguration;
import de.techdev.trackr.core.security.SecurityConfiguration;
import de.techdev.trackr.domain.ApiBeansConfiguration;
import de.techdev.trackr.util.LocalDateUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author Moritz Schulze
 */
@ContextConfiguration(classes = {SecurityConfiguration.class, MethodSecurityConfiguration.class, ApiBeansConfiguration.class, MailConfiguration.class})
public class VacationRequestApproveServiceIntegrationTest extends IntegrationTest {

    @Autowired
    private VacationRequestDataOnDemand vacationRequestDataOnDemand;

    @Autowired
    private VacationRequestApproveService vacationRequestApproveService;

    @Autowired
    private VacationRequestRepository vacationRequestRepository;

    @Test
    public void approveNotAllowedForSelfDoesRollback() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        vacationRequest.setStatus(VacationRequestStatus.PENDING);
        vacationRequestRepository.save(vacationRequest);
        SecurityContextHolder.getContext().setAuthentication(AuthorityMocks.supervisorAuthentication(vacationRequest.getEmployee().getId()));
        try {
            vacationRequestApproveService.approve(vacationRequest, "");
            fail("An exception must be thrown.");
        } catch (Exception e) {
            VacationRequest one = vacationRequestRepository.findOne(vacationRequest.getId());
            assertThat(one.getStatus(), is(VacationRequestStatus.PENDING));
        }
    }

    @Test
    public void rejectNotAllowedForSelfDoesRollback() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        vacationRequest.setStatus(VacationRequestStatus.PENDING);
        vacationRequestRepository.save(vacationRequest);
        SecurityContextHolder.getContext().setAuthentication(AuthorityMocks.supervisorAuthentication(vacationRequest.getEmployee().getId()));
        try {
            vacationRequestApproveService.reject(vacationRequest, "");
            fail("An exception must be thrown.");
        } catch (Exception e) {
            VacationRequest one = vacationRequestRepository.findOne(vacationRequest.getId());
            assertThat(one.getStatus(), is(VacationRequestStatus.PENDING));
        }
    }

    @Test
    public void approveSevenDayOldRequests() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        vacationRequest.setStatus(VacationRequestStatus.PENDING);
        vacationRequest.setSubmissionTime(LocalDateUtil.fromLocalDate(LocalDate.now().minusDays(8)));
        vacationRequestRepository.save(vacationRequest);
        SecurityContextHolder.getContext().setAuthentication(AuthorityMocks.adminAuthentication());
        vacationRequestApproveService.approveSevenDayOldRequests();
        VacationRequest one = vacationRequestRepository.findOne(vacationRequest.getId());
        assertThat(one.getStatus(), is(VacationRequestStatus.APPROVED));
    }
}
