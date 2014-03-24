package de.techdev.trackr.domain.employee.vacation;

import de.techdev.trackr.core.web.MockMvcTest;
import de.techdev.trackr.domain.employee.EmployeeDataOnDemand;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.echocat.jomon.testing.BaseMatchers.isNull;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Moritz Schulze
 */
public class VacationRequestResourceTest extends MockMvcTest {

    @Autowired
    private VacationRequestDataOnDemand vacationRequestDataOnDemand;

    @Autowired
    private VacationRequestRepository vacationRequestRepository;

    @Autowired
    private EmployeeDataOnDemand employeeDataOnDemand;

    @Before
    public void setUp() throws Exception {
        vacationRequestDataOnDemand.init();
    }

    @Test
    public void rootNotExported() throws Exception {
        mockMvc.perform(
                get("/vacationRequests")
                        .session(employeeSession()))
               .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void oneAllowedForEmployee() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/vacationRequests/" + vacationRequest.getId())
                        .session(employeeSession(vacationRequest.getEmployee().getId())))
               .andExpect(status().isOk());
    }

    @Test
    public void oneForbiddenForOther() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/vacationRequests/" + vacationRequest.getId())
                        .session(employeeSession(vacationRequest.getEmployee().getId() + 1)))
               .andExpect(status().isForbidden());
    }

    @Test
    public void findByEmployeeOrderByStartDateAscAllowedForEmployee() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/vacationRequests/search/findByEmployeeOrderByStartDateAsc")
                        .session(employeeSession(vacationRequest.getEmployee().getId()))
                        .param("employee", vacationRequest.getEmployee().getId().toString()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("_embedded.vacationRequests[0]", isNotNull()));
    }

    @Test
    public void findByEmployeeOrderByStartDateAscAllowedForSupervisor() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/vacationRequests/search/findByEmployeeOrderByStartDateAsc")
                        .session(supervisorSession())
                        .param("employee", vacationRequest.getEmployee().getId().toString()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("_embedded.vacationRequests[0]", isNotNull()));
    }

    /**
     * Access is forbidden, but currently spring-data-rest will throw a 400 because the employee cannot be unmarshalled from the id.
     *
     * @throws Exception
     */
    @Test
    @Ignore
    public void findByEmployeeOrderByStartDateAscForbiddenForOther() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/vacationRequests/search/findByEmployeeOrderByStartDateAsc")
                        .session(employeeSession(vacationRequest.getEmployee().getId() + 1))
                        .param("employee", vacationRequest.getEmployee().getId().toString()))
               .andExpect(status().isForbidden());
    }

    @Test
    public void createAllowedForEmployee() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getNewTransientObject(500);
        mockMvc.perform(
                post("/vacationRequests")
                        .session(employeeSession(vacationRequest.getEmployee().getId()))
                        .content(getVacationRequestJson(vacationRequest)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("id", isNotNull()));
    }

    @Test
    public void updateForbiddenForEmployee() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/vacationRequests/" + vacationRequest.getId())
                        .session(employeeSession(vacationRequest.getEmployee().getId()))
                        .content(getVacationRequestJson(vacationRequest))
        )
               .andExpect(status().isForbidden());
    }

    @Test
    public void updateAllowedForSupervisor() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/vacationRequests/" + vacationRequest.getId())
                        .session(supervisorSession())
                        .content(getVacationRequestJson(vacationRequest)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("id", is(vacationRequest.getId().intValue())));
    }

    @Test
    public void updateSelfNotAllowedForSupervisor() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/vacationRequests/" + vacationRequest.getId())
                        .session(supervisorSession(vacationRequest.getEmployee().getId()))
                        .content(getVacationRequestJson(vacationRequest)))
               .andExpect(status().isForbidden());
    }

    /**
     * Access is forbidden, but currently spring-data-rest will throw a 400 because the employee cannot be unmarshalled from the id.
     *
     * @throws Exception
     */
    @Test
    @Ignore
    public void updateForbiddenForOther() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/vacationRequests/" + vacationRequest.getId())
                        .session(employeeSession(vacationRequest.getEmployee().getId() + 1))
                        .content(getVacationRequestJson(vacationRequest))
        )
               .andExpect(status().isForbidden());
    }

    @Test
    public void deleteAllowedForEmployee() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        vacationRequest.setStatus(VacationRequestStatus.PENDING);
        vacationRequestRepository.save(vacationRequest);
        mockMvc.perform(
                delete("/vacationRequests/" + vacationRequest.getId())
                        .session(employeeSession(vacationRequest.getEmployee().getId())))
               .andExpect(status().isNoContent());
    }

    @Test
    public void deleteApprovedNotAllowedForEmployee() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        vacationRequest.setStatus(VacationRequestStatus.APPROVED);
        vacationRequestRepository.save(vacationRequest);
        mockMvc.perform(
                delete("/vacationRequests/" + vacationRequest.getId())
                        .session(employeeSession(vacationRequest.getEmployee().getId())))
               .andExpect(status().isForbidden());
    }

    @Test
    public void deleteRejectedNotAllowedForEmployee() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        vacationRequest.setStatus(VacationRequestStatus.REJECTED);
        vacationRequestRepository.save(vacationRequest);
        mockMvc.perform(
                delete("/vacationRequests/" + vacationRequest.getId())
                        .session(employeeSession(vacationRequest.getEmployee().getId())))
               .andExpect(status().isForbidden());
    }

    @Test
    public void deleteAllowedForSupervisor() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/vacationRequests/" + vacationRequest.getId())
                        .session(supervisorSession())
        )
               .andExpect(status().isNoContent());
    }

    @Test
    public void deleteForbiddenForOther() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/vacationRequests/" + vacationRequest.getId())
                        .session(employeeSession(vacationRequest.getEmployee().getId() + 1)))
               .andExpect(status().isForbidden());
    }

    @Test
    public void updateEmployeeIsForbidden() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/vacationRequests/" + vacationRequest.getId() + "/employee")
                        .session(employeeSession(vacationRequest.getEmployee().getId()))
                        .content("/employees/" + vacationRequest.getEmployee().getId())
                        .header("Content-Type", "text/uri-list"))
               .andExpect(status().isForbidden());
    }

    @Test
    public void updateApproverIsForbbiden() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/vacationRequests/" + vacationRequest.getId() + "/approver")
                        .session(employeeSession(vacationRequest.getEmployee().getId()))
                        .content("/employees/" + vacationRequest.getEmployee().getId())
                        .header("Content-Type", "text/uri-list"))
               .andExpect(status().isForbidden());
    }

    @Test
    public void deleteEmployeeIsForbidden() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/vacationRequests/" + vacationRequest.getId() + "/employee")
                        .session(employeeSession(vacationRequest.getEmployee().getId())))
               .andExpect(status().isForbidden());
    }

    @Test
    public void deleteApproverIsForbbiden() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        vacationRequest.setApprover(employeeDataOnDemand.getRandomObject());
        vacationRequestRepository.save(vacationRequest);
        mockMvc.perform(
                delete("/vacationRequests/" + vacationRequest.getId() + "/approver")
                        .session(employeeSession(vacationRequest.getEmployee().getId())))
               .andExpect(status().isForbidden());
    }

    @Test
    public void getApprover() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        vacationRequest.setApprover(employeeDataOnDemand.getRandomObject());
        vacationRequestRepository.save(vacationRequest);
        mockMvc.perform(
                get("/vacationRequests/" + vacationRequest.getId() + "/approver")
                        .session(employeeSession(vacationRequest.getEmployee().getId())))
               .andExpect(status().isOk());
    }

    @Test
    public void findByStatusOrderBySubmissionTimeAscForbiddenForEmployee() throws Exception {
        mockMvc.perform(
                get("/vacationRequests/search/findByStatusOrderBySubmissionTimeAsc")
                        .session(employeeSession())
                        .param("approved", VacationRequestStatus.APPROVED.toString()))
               .andExpect(status().isForbidden());
    }

    @Test
    public void findByStatusOrderBySubmissionTimeAscAllowedForSupervisor() throws Exception {
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/vacationRequests/search/findByStatusOrderBySubmissionTimeAsc")
                        .session(supervisorSession())
                        .param("status", vacationRequest.getStatus().toString()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("_embedded.vacationRequests[0]", isNotNull()));
    }

    @Test
    public void findByStatusOrderBySubmissionTimeAscWithSupervisorDoesNotContainOwnRequests() throws Exception {
        vacationRequestRepository.deleteAll();
        VacationRequest vacationRequest = vacationRequestDataOnDemand.getNewTransientObject(500);
        vacationRequestRepository.save(vacationRequest);
        mockMvc.perform(
                get("/vacationRequests/search/findByStatusOrderBySubmissionTimeAsc")
                        .session(supervisorSession(vacationRequest.getEmployee().getId()))
                        .param("approved", vacationRequest.getStatus().toString()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("_embedded", isNull()));
    }

    protected String getVacationRequestJson(VacationRequest vacationRequest) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        StringWriter writer = new StringWriter();
        JsonGenerator jsonGenerator = jsonGeneratorFactory.createGenerator(writer);
        JsonGenerator jg = jsonGenerator
                .writeStartObject()
                .write("startDate", sdf.format(vacationRequest.getStartDate()))
                .write("endDate", sdf.format(vacationRequest.getEndDate()))
                .write("status", vacationRequest.getStatus().toString())
                .write("employee", "/api/employees/" + vacationRequest.getEmployee().getId());

        if (vacationRequest.getId() != null) {
            jg.write("id", vacationRequest.getId());
        }
        if (vacationRequest.getApprovalDate() != null) {
            jg.write("approvalDate", sdf.format(vacationRequest.getApprovalDate()));
        }
        if (vacationRequest.getNumberOfDays() != null) {
            jg.write("numberOfDays", vacationRequest.getNumberOfDays());
        }
        if (vacationRequest.getSubmissionTime() != null) {
            jg.write("submissionTime", sdf.format(vacationRequest.getSubmissionTime()));
        }

        jg.writeEnd().close();
        return writer.toString();
    }

}
