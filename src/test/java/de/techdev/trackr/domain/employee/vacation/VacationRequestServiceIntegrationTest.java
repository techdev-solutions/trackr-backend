package de.techdev.trackr.domain.employee.vacation;

import de.techdev.trackr.IntegrationTest;
import de.techdev.trackr.core.security.AuthorityMocks;
import de.techdev.trackr.core.security.MethodSecurityConfiguration;
import de.techdev.trackr.core.security.SecurityConfiguration;
import de.techdev.trackr.domain.ApiBeansConfiguration;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;

import static org.echocat.jomon.testing.BaseMatchers.isFalse;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author Moritz Schulze
 */
@ContextConfiguration(classes = {SecurityConfiguration.class, MethodSecurityConfiguration.class, ApiBeansConfiguration.class})
public class VacationRequestServiceIntegrationTest extends IntegrationTest {

    @Autowired
    private VacationRequestDataOnDemand vacationRequestDataOnDemand;

    @Autowired
    private VacationRequestService vacationRequestService;

    @Autowired
    private VacationRequestRepository vacationRequestRepository;

    @Test
    public void approveNotAllowedForSelfDoesRollback() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        vacationRequest.setStatus(VacationRequestStatus.PENDING);
        vacationRequestRepository.save(vacationRequest);
        SecurityContextHolder.getContext().setAuthentication(AuthorityMocks.supervisorAuthentication(vacationRequest.getEmployee().getId()));
        try {
            vacationRequestService.approve(vacationRequest.getId(), "");
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
            vacationRequestService.reject(vacationRequest.getId(), "");
            fail("An exception must be thrown.");
        } catch (Exception e) {
            VacationRequest one = vacationRequestRepository.findOne(vacationRequest.getId());
            assertThat(one.getStatus(), is(VacationRequestStatus.PENDING));
        }
    }
}
