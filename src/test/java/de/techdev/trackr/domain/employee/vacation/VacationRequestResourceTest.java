package de.techdev.trackr.domain.employee.vacation;

import de.techdev.trackr.core.security.AuthorityMocks;
import de.techdev.trackr.domain.AbstractDomainResourceTest;
import de.techdev.trackr.domain.employee.EmployeeDataOnDemand;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.json.stream.JsonGenerator;

import java.io.StringWriter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Function;

import static de.techdev.trackr.domain.DomainResourceTestMatchers.*;
import static java.time.format.DateTimeFormatter.ISO_INSTANT;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Moritz Schulze
 */
public class VacationRequestResourceTest extends AbstractDomainResourceTest<VacationRequest> {

    @Autowired
    private EmployeeDataOnDemand employeeDataOnDemand;

    private Function<VacationRequest, MockHttpSession> sameEmployeeSessionProvider;
    private Function<VacationRequest, MockHttpSession> otherEmployeeSessionProvider;
    private Function<VacationRequest, MockHttpSession> otherSupervisorSessionProvider;

    @Override
    protected String getResourceName() {
        return "vacationRequests";
    }

    public VacationRequestResourceTest() {
        sameEmployeeSessionProvider = vacationRequest -> employeeSession(vacationRequest.getEmployee().getId());
        otherEmployeeSessionProvider = vacationRequest -> employeeSession(vacationRequest.getEmployee().getId() + 1);
        otherSupervisorSessionProvider = vacationRequest -> supervisorSession(vacationRequest.getEmployee().getId() + 1);
    }

    @Test
    public void rootNotExported() throws Exception {
        assertThat(root(employeeSession()), isMethodNotAllowed());
    }

    @Test
    public void oneAllowedForEmployee() throws Exception {
        assertThat(one(sameEmployeeSessionProvider), isAccessible());
    }

    @Test
    public void oneForbiddenForOther() throws Exception {
        assertThat(one(otherEmployeeSessionProvider), isForbidden());
    }

    @Test
    public void findByEmployeeOrderByStartDateAscAllowedForEmployee() throws Exception {
        VacationRequest vacationRequest = dataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/vacationRequests/search/findByEmployeeOrderByStartDateAsc")
                        .session(employeeSession(vacationRequest.getEmployee().getId()))
                        .param("employee", vacationRequest.getEmployee().getId().toString())
        )
               .andExpect(status().isOk())
               .andExpect(jsonPath("_embedded.vacationRequests[0]", isNotNull()));
    }

    @Test
    public void findByEmployeeOrderByStartDateAscAllowedForSupervisor() throws Exception {
        VacationRequest vacationRequest = dataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/vacationRequests/search/findByEmployeeOrderByStartDateAsc")
                        .session(supervisorSession())
                        .param("employee", vacationRequest.getEmployee().getId().toString())
        )
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
        VacationRequest vacationRequest = dataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/vacationRequests/search/findByEmployeeOrderByStartDateAsc")
                        .session(employeeSession(vacationRequest.getEmployee().getId() + 1))
                        .param("employee", vacationRequest.getEmployee().getId().toString())
        )
               .andExpect(status().isForbidden());
    }

    @Test
    public void createAllowedForEmployee() throws Exception {
        assertThat(create(sameEmployeeSessionProvider), isCreated());
    }

    @Test
    public void createForbiddenForSupervisorIfNotOwner() throws Exception {
        assertThat(create(otherSupervisorSessionProvider), isForbidden());
    }

    @Test
    public void updateForbiddenForEmployee() throws Exception {
        assertThat(update(sameEmployeeSessionProvider), isForbidden());
    }

    @Test
    public void updateAllowedForSupervisor() throws Exception {
        assertThat(update(supervisorSession()), isUpdated());
    }

    @Test
    public void updateSelfNotAllowedForSupervisor() throws Exception {
        assertThat(update((vr) -> supervisorSession(vr.getEmployee().getId())), isForbidden());
    }

    /**
     * Access is forbidden, but currently spring-data-rest will throw a 400 because the employee cannot be unmarshalled from the id.
     *
     * @throws Exception
     */
    @Test
    @Ignore
    public void updateForbiddenForOther() throws Exception {
        VacationRequest vacationRequest = dataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/vacationRequests/" + vacationRequest.getId())
                        .session(employeeSession(vacationRequest.getEmployee().getId() + 1))
                        .content(getJsonRepresentation(vacationRequest))
        )
               .andExpect(status().isForbidden());
    }

    @Test
    public void deleteAllowedForEmployee() throws Exception {
        VacationRequest vacationRequest = dataOnDemand.getRandomObject();
        vacationRequest.setStatus(VacationRequest.VacationRequestStatus.PENDING);
        repository.save(vacationRequest);
        mockMvc.perform(
                delete("/vacationRequests/" + vacationRequest.getId())
                        .session(employeeSession(vacationRequest.getEmployee().getId()))
        )
               .andExpect(status().isNoContent());
    }

    @Test
    public void deleteApprovedNotAllowedForEmployee() throws Exception {
        VacationRequest vacationRequest = dataOnDemand.getRandomObject();
        vacationRequest.setStatus(VacationRequest.VacationRequestStatus.APPROVED);
        repository.save(vacationRequest);
        mockMvc.perform(
                delete("/vacationRequests/" + vacationRequest.getId())
                        .session(employeeSession(vacationRequest.getEmployee().getId()))
        )
               .andExpect(status().isForbidden());
    }

    @Test
    public void deleteRejectedNotAllowedForEmployee() throws Exception {
        VacationRequest vacationRequest = dataOnDemand.getRandomObject();
        vacationRequest.setStatus(VacationRequest.VacationRequestStatus.REJECTED);
        repository.save(vacationRequest);
        mockMvc.perform(
                delete("/vacationRequests/" + vacationRequest.getId())
                        .session(employeeSession(vacationRequest.getEmployee().getId()))
        )
               .andExpect(status().isForbidden());
    }

    @Test
    public void deleteAllowedForSupervisor() throws Exception {
        assertThat(remove(supervisorSession()), isNoContent());
    }

    @Test
    public void deleteForbiddenForOther() throws Exception {
        assertThat(remove(otherEmployeeSessionProvider), isForbidden());
    }

    @Test
    public void updateEmployeeIsForbidden() throws Exception {
        VacationRequest vacationRequest = dataOnDemand.getRandomObject();
        assertThat(updateLink(adminSession(), "employee", "/employees/" + vacationRequest.getEmployee().getId()), isForbidden());
    }

    @Test
    public void updateApproverIsForbidden() throws Exception {
        VacationRequest vacationRequest = dataOnDemand.getRandomObject();
        assertThat(updateLink(adminSession(), "approver", "/employees/" + vacationRequest.getEmployee().getId()), isForbidden());
    }

    @Test
    public void deleteEmployeeIsForbidden() throws Exception {
        VacationRequest vacationRequest = dataOnDemand.getRandomObject();
        assertThat(removeUrl(employeeSession(vacationRequest.getEmployee().getId()), "/vacationRequests/" + vacationRequest.getId() + "/employee"), isForbidden());
    }

    @Test
    public void deleteApproverIsForbbiden() throws Exception {
        VacationRequest vacationRequest = dataOnDemand.getRandomObject();
        vacationRequest.setApprover(vacationRequest.getEmployee());
        repository.save(vacationRequest);
        assertThat(removeUrl(employeeSession(vacationRequest.getEmployee().getId()), "/vacationRequests/" + vacationRequest.getId() + "/approver"), isForbidden());
    }

    @Test
    public void getApprover() throws Exception {
        VacationRequest vacationRequest = dataOnDemand.getRandomObject();
        vacationRequest.setApprover(employeeDataOnDemand.getRandomObject());
        repository.save(vacationRequest);
        mockMvc.perform(
                get("/vacationRequests/" + vacationRequest.getId() + "/approver")
                        .session(employeeSession(vacationRequest.getEmployee().getId()))
        )
               .andExpect(status().isOk());
    }

    @Test
    public void findByStatusOrderBySubmissionTimeAscForbiddenForEmployee() throws Exception {
        mockMvc.perform(
                get("/vacationRequests/search/findByStatusOrderBySubmissionTimeAsc")
                        .session(employeeSession())
                        .param("approved", VacationRequest.VacationRequestStatus.APPROVED.toString())
        )
               .andExpect(status().isForbidden());
    }

    @Test
    public void findByStatusOrderBySubmissionTimeAscAllowedForSupervisor() throws Exception {
        VacationRequest vacationRequest = dataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/vacationRequests/search/findByStatusOrderBySubmissionTimeAsc")
                        .session(supervisorSession())
                        .param("status", vacationRequest.getStatus().toString())
        )
               .andExpect(status().isOk())
               .andExpect(jsonPath("_embedded.vacationRequests[0]", isNotNull()));
    }

    @Test
    public void approveNotAllowedForSupervisorOnOwnVacationRequest() throws Exception {
        VacationRequest vacationRequest = dataOnDemand.getRandomObject();
        vacationRequest.setStatus(VacationRequest.VacationRequestStatus.PENDING);
        repository.save(vacationRequest);
        mockMvc.perform(
                put("/vacationRequests/" + vacationRequest.getId() + "/approve")
                        .session(supervisorSession(vacationRequest.getEmployee().getId()))
        )
                .andExpect(status().isForbidden());

        SecurityContextHolder.getContext().setAuthentication(AuthorityMocks.supervisorAuthentication());
        VacationRequest one = repository.findOne(vacationRequest.getId());
        assertThat(one.getStatus(), is(VacationRequest.VacationRequestStatus.PENDING));
    }

    @Test
    public void approveNotAllowedForEmployees() throws Exception {
        VacationRequest vacationRequest = dataOnDemand.getRandomObject();
        vacationRequest.setStatus(VacationRequest.VacationRequestStatus.PENDING);
        repository.save(vacationRequest);
        mockMvc.perform(
                put("/vacationRequests/" + vacationRequest.getId() + "/approve")
                        .session(employeeSession(vacationRequest.getEmployee().getId()))
        )
                .andExpect(status().isForbidden());

        SecurityContextHolder.getContext().setAuthentication(AuthorityMocks.supervisorAuthentication());
        VacationRequest one = repository.findOne(vacationRequest.getId());
        assertThat(one.getStatus(), is(VacationRequest.VacationRequestStatus.PENDING));
    }

    @Test
    public void approveAllowedForOtherSupervisor() throws Exception {
        VacationRequest vacationRequest = dataOnDemand.getRandomObject();
        vacationRequest.setStatus(VacationRequest.VacationRequestStatus.PENDING);
        repository.save(vacationRequest);
        mockMvc.perform(
                put("/vacationRequests/" + vacationRequest.getId() + "/approve")
                        .session(supervisorSession(vacationRequest.getEmployee().getId() + 1))
        )
                .andExpect(status().isOk());

        SecurityContextHolder.getContext().setAuthentication(AuthorityMocks.supervisorAuthentication());
        VacationRequest one = repository.findOne(vacationRequest.getId());
        assertThat(one.getStatus(), is(VacationRequest.VacationRequestStatus.APPROVED));
    }

    @Test
    public void daysPerEmployeeBetweenAccessibleForAdmin() throws Exception {
        VacationRequest vacationRequest = dataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/vacationRequests/daysPerEmployeeBetween")
                .session(adminSession())
                .param("start", String.valueOf(toEpochMilli(vacationRequest.getStartDate())))
                .param("end", String.valueOf(toEpochMilli(vacationRequest.getEndDate())))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath(vacationRequest.getEmployee().getFirstName() + " " + vacationRequest.getEmployee().getLastName()).exists());
    }

	private long toEpochMilli(LocalDate startDate) {
		return startDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond()*1000;
	}

    @Test
    public void daysPerEmployeeBetweenForbiddenForSupervisor() throws Exception {
        mockMvc.perform(
                get("/vacationRequests/daysPerEmployeeBetween")
                        .session(supervisorSession())
                        .param("start", String.valueOf(new Date().getTime()))
                        .param("end", String.valueOf(new Date().getTime()))
        )
                .andExpect(status().isForbidden());
    }

    @Override
    protected String getJsonRepresentation(VacationRequest vacationRequest) {
        StringWriter writer = new StringWriter();
        JsonGenerator jsonGenerator = jsonGeneratorFactory.createGenerator(writer);
        JsonGenerator jg = jsonGenerator
                .writeStartObject()
                .write("startDate", ISO_LOCAL_DATE.format(vacationRequest.getStartDate()))
                .write("endDate", ISO_LOCAL_DATE.format(vacationRequest.getEndDate()))
                .write("status", vacationRequest.getStatus().toString())
                .write("employee", "/api/employees/" + vacationRequest.getEmployee().getId());

        if (vacationRequest.getId() != null) {
            jg.write("id", vacationRequest.getId());
        }
        if (vacationRequest.getApprovalDate() != null) {
            jg.write("approvalDate", ISO_INSTANT.format(vacationRequest.getApprovalDate()));
        } 
        if (vacationRequest.getNumberOfDays() != null) {
            jg.write("numberOfDays", vacationRequest.getNumberOfDays());
        }
        if (vacationRequest.getSubmissionTime() != null) {
            jg.write("submissionTime", ISO_INSTANT.format(vacationRequest.getSubmissionTime()));
        }

        jg.writeEnd().close();
        return writer.toString();
    }

}
