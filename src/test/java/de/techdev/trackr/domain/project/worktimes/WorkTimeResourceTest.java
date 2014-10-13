package de.techdev.trackr.domain.project.worktimes;

import static de.techdev.trackr.domain.DomainResourceTestMatchers.isAccessible;
import static de.techdev.trackr.domain.DomainResourceTestMatchers.isCreated;
import static de.techdev.trackr.domain.DomainResourceTestMatchers.isForbidden;
import static de.techdev.trackr.domain.DomainResourceTestMatchers.isMethodNotAllowed;
import static de.techdev.trackr.domain.DomainResourceTestMatchers.isNoContent;
import static de.techdev.trackr.domain.DomainResourceTestMatchers.isUpdated;
import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.function.Function;

import javax.json.stream.JsonGenerator;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;

import de.techdev.trackr.domain.AbstractDomainResourceTest;

/**
 * @author Moritz Schulze
 */
public class WorkTimeResourceTest extends AbstractDomainResourceTest<WorkTime> {

	static final DateTimeFormatter LOCAL_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final Function<WorkTime, MockHttpSession> sameEmployeeSessionProvider;
    private final Function<WorkTime, MockHttpSession> otherEmployeeSessionProvider;

    public WorkTimeResourceTest() {
        sameEmployeeSessionProvider = workTime -> employeeSession(workTime.getEmployee().getId());
        otherEmployeeSessionProvider = workTime -> employeeSession(workTime.getEmployee().getId() + 1);
    }

    @Override
    protected String getResourceName() {
        return "workTimes";
    }

    @Test
    public void rootNotExported() throws Exception {
        assertThat(root(employeeSession()), isMethodNotAllowed());
    }

    @Test
    public void oneAllowedForOwner() throws Exception {
        assertThat(one(sameEmployeeSessionProvider), isAccessible());
    }

    @Test
    public void oneForbiddenForOther() throws Exception {
        assertThat(one(otherEmployeeSessionProvider), isForbidden());
    }

    @Test
    public void createAllowedForEveryoneIfIsEmployee() throws Exception {
        assertThat(create(sameEmployeeSessionProvider), isCreated());
    }

    @Test
    public void updateAllowedForOwner() throws Exception {
        assertThat(update(sameEmployeeSessionProvider), isUpdated());
    }

    @Test
    public void updateAllowedForAdmin() throws Exception {
        assertThat(update(adminSession()), isUpdated());
    }

    @Test
    public void updateNotAllowedForSupervisor() throws Exception {
        assertThat(update(supervisorSession()), isForbidden());
    }

    @Test
    public void deleteAllowedForOwner() throws Exception {
        assertThat(remove(sameEmployeeSessionProvider), isNoContent());
    }

    @Test
    public void deleteAllowedForAdmin() throws Exception {
        assertThat(remove(adminSession()), isNoContent());
    }

    @Test
    public void deleteNotAllowedForSupervisor() throws Exception {
        assertThat(remove(supervisorSession()), isForbidden());
    }

    /**
     * TODO: when trying to set to another employee the EmployeeRepository will deny accessing that employee which will result in a HTTP 400
     * TODO: When trying to set to the same employee our Exception thrown in the {@link de.techdev.trackr.domain.project.worktimes.WorkTimeEventHandler}
     * TODO: won't be propagated to Web MVC.
     * TODO: so in conclusion updating does not work but will produce a 500 or 400 instead of a 405.
     * @throws Exception
     */
    @Test
    @Ignore
    public void updateEmployeeNotAllowed() throws Exception {
        assertThat(updateLink(sameEmployeeSessionProvider, "employee", "/employees/0"), isMethodNotAllowed());
    }

    @Test
    public void deleteEmployeeNotAllowed() throws Exception {
        WorkTime workTime = dataOnDemand.getRandomObject();
        assertThat(removeUrl(adminSession(), "/workTimes/" + workTime.getId() + "/employee"), isForbidden());
    }

    @Test
    public void deleteProjectNotAllowed() throws Exception {
        WorkTime workTime = dataOnDemand.getRandomObject();
        assertThat(removeUrl(adminSession(), "/workTimes/" + workTime.getId() + "/project"), isForbidden());
    }

    @Test
    public void updateProjectAllowedForOwner() throws Exception {
        assertThat(updateLink(sameEmployeeSessionProvider, "project", "/projects/0"), isNoContent());
    }

    @Test
    public void updateProjectAllowedForAdmin() throws Exception {
        assertThat(updateLink(adminSession(), "project", "/projects/0"), isNoContent());
    }

    @Test
    public void updateProjectForbiddenForSupervisor() throws Exception {
        assertThat(updateLink(supervisorSession(), "project", "/projects/0"), isForbidden());
    }

    @Test
    public void findByEmployeeAndDateOrderByStartTimeAscAllowedForOwner() throws Exception {
        WorkTime workTime = dataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/workTimes/search/findByEmployeeAndDateOrderByStartTimeAsc")
                        .session(employeeSession(workTime.getEmployee().getId()))
                        .param("employee", workTime.getEmployee().getId().toString())
                        .param("date", LOCAL_DATE_FORMAT.format(workTime.getDate())))
               .andExpect(status().isOk())
               .andExpect(jsonPath("_embedded.workTimes[0].id", isNotNull()));
    }

    @Test
    public void findByEmployeeAndDateOrderByStartTimeAscAllowedForSupervisor() throws Exception {
        WorkTime workTime = dataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/workTimes/search/findByEmployeeAndDateOrderByStartTimeAsc")
                        .session(supervisorSession())
                        .param("employee", workTime.getEmployee().getId().toString())
                        .param("date", LOCAL_DATE_FORMAT.format(workTime.getDate())))
               .andExpect(status().isOk())
               .andExpect(jsonPath("_embedded.workTimes[0].id", isNotNull()));
    }

    @Test
    public void findByDateBetweenAllowedForAdmin() throws Exception {
        mockMvc.perform(
                get("/workTimes/search/findByDateBetween")
                        .session(adminSession())
                        .param("start", String.valueOf(new Date().getTime()))
                        .param("end", String.valueOf(new Date().getTime())))
                .andExpect(status().isOk());
    }

    @Test
    public void findByDateBetweenForbiddenForSupervisor() throws Exception {
        mockMvc.perform(
                get("/workTimes/search/findByDateBetween")
                        .session(supervisorSession())
                        .param("start", String.valueOf(new Date().getTime()))
                        .param("end", String.valueOf(new Date().getTime())))
                .andExpect(status().isForbidden());
    }

    @Test
    public void findByEmployeeAndDateBetweenOrderByDateAscStartTimeAscAllowedForOwner() throws Exception {
        WorkTime workTime1 = dataOnDemand.getRandomObject();
        WorkTime workTime2 = dataOnDemand.getRandomObject();
        workTime2.setEmployee(workTime1.getEmployee());
        repository.save(workTime2);
        LocalDate low, high;
        if(workTime1.getDate().compareTo(workTime2.getDate()) <= 0) {
            low = workTime1.getDate();
            high = workTime2.getDate();
        } else {
            low = workTime2.getDate();
            high = workTime1.getDate();
        }
        mockMvc.perform(
                get("/workTimes/search/findByEmployeeAndDateBetweenOrderByDateAscStartTimeAsc")
                        .session(employeeSession(workTime1.getEmployee().getId()))
                        .param("employee", workTime1.getEmployee().getId().toString())
                        .param("start", LOCAL_DATE_FORMAT.format(low))
                        .param("end", LOCAL_DATE_FORMAT.format(high)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("_embedded.workTimes[0].id", isNotNull()));
    }

    @Test
    public void findByEmployeeAndDateBetweenOrderByDateAscStartTimeAscAllowedForSupervisor() throws Exception {
        WorkTime workTime1 = dataOnDemand.getRandomObject();
        WorkTime workTime2 = dataOnDemand.getRandomObject();
        workTime2.setEmployee(workTime1.getEmployee());
        repository.save(workTime2);
        LocalDate low, high;
        if(workTime1.getDate().compareTo(workTime2.getDate()) <= 0) {
            low = workTime1.getDate();
            high = workTime2.getDate();
        } else {
            low = workTime2.getDate();
            high = workTime1.getDate();
        }
        mockMvc.perform(
                get("/workTimes/search/findByEmployeeAndDateBetweenOrderByDateAscStartTimeAsc")
                        .session(supervisorSession())
                        .param("employee", workTime1.getEmployee().getId().toString())
                        .param("start", LOCAL_DATE_FORMAT.format(low))
                        .param("end", LOCAL_DATE_FORMAT.format(high)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("_embedded.workTimes[0].id", isNotNull()));
    }

    /**
     * This does not work because the accessDeniedException is thrown somewhere where spring-data-rest does not catch it.
     * So we get HTTP 400 instead of 403.
     * TODO: find out how to get a 403
     * @throws Exception
     */
    @Test
    @Ignore
    public void findByEmployeeAndDateBetweenOrderByDateAscStartTimeAscForbiddenForOther() throws Exception {
        WorkTime workTime = dataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/workTimes/search/findByEmployeeAndDateBetweenOrderByDateAscStartTimeAsc")
                        .session(employeeSession(workTime.getEmployee().getId() + 1))
                        .param("employee", workTime.getEmployee().getId().toString())
                        .param("start", "2014-01-01")
                        .param("end", "2014-12-01"))
               .andExpect(status().isForbidden());
    }

    /**
     * This does not work because the accessDeniedException is thrown somewhere where spring-data-rest does not catch it.
     * So we get HTTP 400 instead of 403.
     * TODO: find out how to get a 403
     * @throws Exception
     */
    @Test
    @Ignore
    public void findByEmployeeAndDateOrderByStartTimeAscForbiddenForOther() throws Exception {
        WorkTime workTime = dataOnDemand.getRandomObject();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        mockMvc.perform(
                get("/workTimes/search/findByEmployeeAndDateOrderByStartTimeAsc")
                        .session(employeeSession(workTime.getEmployee().getId()))
                        .param("employee", workTime.getEmployee().getId().toString())
                        .param("date", sdf.format(workTime.getDate())))
               .andExpect(status().isForbidden());
    }

    @Test
    public void findByProjectAndDateBetweenOrderByDateAscStartTimeAscAllowedForSupervisor() throws Exception {
        WorkTime workTime1 = dataOnDemand.getRandomObject();
        WorkTime workTime2 = dataOnDemand.getRandomObject();
        workTime2.setProject(workTime1.getProject());
        repository.save(workTime2);
        LocalDate low, high;
        if(workTime1.getDate().compareTo(workTime2.getDate()) <= 0) {
            low = workTime1.getDate();
            high = workTime2.getDate();
        } else {
            low = workTime2.getDate();
            high = workTime1.getDate();
        }
        mockMvc.perform(
                get("/workTimes/search/findByProjectAndDateBetweenOrderByDateAscStartTimeAsc")
                        .session(supervisorSession())
                        .param("project", workTime1.getProject().getId().toString())
                        .param("start", LOCAL_DATE_FORMAT.format(low))
                        .param("end", LOCAL_DATE_FORMAT.format(high)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("_embedded.workTimes[0].id", isNotNull()));
    }

    @Test
    public void findByProjectAndDateBetweenOrderByDateAscStartTimeAscForbiddenForEmployee() throws Exception {
        WorkTime workTime = dataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/workTimes/search/findByProjectAndDateBetweenOrderByDateAscStartTimeAsc")
                        .session(employeeSession())
                        .param("project", workTime.getProject().getId().toString())
                        .param("start", "2014-01-01")
                        .param("end", "2014-12-01"))
               .andExpect(status().isForbidden());
    }


    @Override
    protected String getJsonRepresentation(WorkTime workTime) {
        StringWriter writer = new StringWriter();
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        jg.writeStartObject()
          .write("date", dtf.format(workTime.getDate()))
          .write("startTime", workTime.getStartTime().toString())
          .write("endTime", workTime.getEndTime().toString())
          .write("employee", "/employees/" + workTime.getEmployee().getId())
          .write("project", "/projects/" + workTime.getProject().getId());

        if (workTime.getComment() != null) {
            jg.write("comment", workTime.getComment());
        }

        if (workTime.getId() != null) {
            jg.write("id", workTime.getId());
        }
        jg.writeEnd().close();
        return writer.toString();
    }
}
