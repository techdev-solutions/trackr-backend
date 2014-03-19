package de.techdev.trackr.domain.employee.vacation;

import de.techdev.trackr.core.web.MockMvcTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Moritz Schulze
 */

public class VacationRequestControllerIntegrationTest extends MockMvcTest {

    @Autowired
    private VacationRequestDataOnDemand vacationRequestDataOnDemand;

    @Autowired
    private VacationRequestRepository vacationRequestRepository;

    @Before
    public void setUp() throws Exception {
        vacationRequestDataOnDemand.init();
    }

    @Test
    public void approveAllowedForSupervisor() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        vacationRequest.setStatus(VacationRequestStatus.PENDING);
        vacationRequestRepository.save(vacationRequest);
        mockMvc.perform(
                put("/vacationRequests/" + vacationRequest.getId() + "/approve")
                        .session(supervisorSession()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("approved", is(true)));
    }

    @Test
    public void selfApproveForbiddenForSupervisor() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        vacationRequest.setStatus(VacationRequestStatus.PENDING);
        vacationRequestRepository.save(vacationRequest);
        mockMvc.perform(
                put("/vacationRequests/" + vacationRequest.getId() + "/approve")
                        .session(supervisorSession(vacationRequest.getEmployee().getId())))
               .andExpect(status().isForbidden());
    }

    @Test
    public void approveForbiddenForEmployee() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        vacationRequest.setStatus(VacationRequestStatus.PENDING);
        vacationRequestRepository.save(vacationRequest);
        mockMvc.perform(
                put("/vacationRequests/" + vacationRequest.getId() + "/approve")
                        .session(employeeSession()))
               .andExpect(status().isForbidden());
    }
}
