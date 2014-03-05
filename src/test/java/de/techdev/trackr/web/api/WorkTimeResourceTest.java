package de.techdev.trackr.web.api;

import de.techdev.trackr.domain.Project;
import de.techdev.trackr.domain.WorkTime;
import de.techdev.trackr.domain.support.ProjectDataOnDemand;
import de.techdev.trackr.domain.support.WorkTimeDataOnDemand;
import de.techdev.trackr.repository.WorkTimeRepository;
import de.techdev.trackr.web.MockMvcTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Moritz Schulze
 */
public class WorkTimeResourceTest extends MockMvcTest {

    @Autowired
    private WorkTimeDataOnDemand workTimeDataOnDemand;

    @Autowired
    private ProjectDataOnDemand projectDataOnDemand;

    @Autowired
    private WorkTimeRepository workTimeRepository;

    @Before
    public void setUp() throws Exception {
        workTimeDataOnDemand.init();
        projectDataOnDemand.init();
    }

    @Test
    public void rootNotExported() throws Exception {
        mockMvc.perform(
                get("/workTimes")
                        .session(employeeSession()))
               .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void oneAllowedForOwner() throws Exception {
        WorkTime workTime = workTimeDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/workTimes/" + workTime.getId())
                        .session(employeeSession(workTime.getEmployee().getId())))
               .andExpect(status().isOk());
    }

    @Test
    public void oneForbiddenForOther() throws Exception {
        WorkTime workTime = workTimeDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/workTimes/" + workTime.getId())
                        .session(employeeSession(workTime.getEmployee().getId() + 1)))
               .andExpect(status().isForbidden());
    }

    @Test
    public void createAllowedForEveryoneIfIsEmployee() throws Exception {
        WorkTime workTime = workTimeDataOnDemand.getNewTransientObject(500);
        mockMvc.perform(
                post("/workTimes")
                        .session(employeeSession(workTime.getEmployee().getId()))
                        .content(createWorkTimeJson(workTime)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("id", isNotNull()));
    }

    @Test
    public void createAllowedForAdmin() throws Exception {
        WorkTime workTime = workTimeDataOnDemand.getNewTransientObject(500);
        mockMvc.perform(
                post("/workTimes")
                        .session(adminSession())
                        .content(createWorkTimeJson(workTime)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("id", isNotNull()));
    }

    @Test
    public void updateAllowedForOwner() throws Exception {
        WorkTime workTime = workTimeDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/workTimes/" + workTime.getId())
                        .session(employeeSession(workTime.getEmployee().getId()))
                        .content(createWorkTimeJson(workTime)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("id", is(workTime.getId().intValue())));
    }

    @Test
    public void updateAllowedForAdmin() throws Exception {
        WorkTime workTime = workTimeDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/workTimes/" + workTime.getId())
                        .session(adminSession())
                        .content(createWorkTimeJson(workTime)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("id", is(workTime.getId().intValue())));
    }

    @Test
    public void updateNotAllowedForSupervisor() throws Exception {
        WorkTime workTime = workTimeDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/workTimes/" + workTime.getId())
                        .session(supervisorSession())
                        .content(createWorkTimeJson(workTime)))
               .andExpect(status().isForbidden());
    }

    @Test
    public void deleteAllowedForOwner() throws Exception {
        WorkTime workTime = workTimeDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/workTimes/" + workTime.getId())
                        .session(employeeSession(workTime.getEmployee().getId())))
               .andExpect(status().isNoContent());
    }

    @Test
    public void deleteAllowedForAdmin() throws Exception {
        WorkTime workTime = workTimeDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/workTimes/" + workTime.getId())
                        .session(adminSession()))
               .andExpect(status().isNoContent());
    }

    @Test
    public void deleteNotAllowedForSupervisor() throws Exception {
        WorkTime workTime = workTimeDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/workTimes/" + workTime.getId())
                        .session(supervisorSession()))
               .andExpect(status().isForbidden());
    }

    /**
     * TODO: when trying to set to another employee the EmployeeRepository will deny accessing that employee which will result in a HTTP 400
     * TODO: When trying to set to the same employee our Exception thrown in the {@link de.techdev.trackr.repository.WorkTimeEventHandler}
     * TODO: won't be propagated to Web MVC.
     * TODO: so in conclusion updating does not work but will produce a 500 or 400 instead of a 405.
     * @throws Exception
     */
    @Test
    @Ignore
    public void updateEmployeeNotAllowed() throws Exception {
        WorkTime workTime = workTimeDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/workTimes/" + workTime.getId() + "/employee")
                        .session(employeeSession(workTime.getEmployee().getId()))
                        .header("Content-Type", "text/uri-list")
                        .content("/employees/" + workTime.getEmployee().getId()))
               .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void deleteEmployeeNotAllowed() throws Exception {
        WorkTime workTime = workTimeDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/workTimes/" + workTime.getId() + "/employee")
                        .session(adminSession()))
               .andExpect(status().isForbidden());
    }

    @Test
    public void deleteProjectNotAllowed() throws Exception {
        WorkTime workTime = workTimeDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/workTimes/" + workTime.getId() + "/project")
                        .session(adminSession()))
               .andExpect(status().isForbidden());
    }

    @Test
    public void updateProjectAllowedForOwner() throws Exception {
        WorkTime workTime = workTimeDataOnDemand.getRandomObject();
        Project project = projectDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/workTimes/" + workTime.getId() + "/project")
                        .session(employeeSession(workTime.getEmployee().getId()))
                        .header("Content-Type", "text/uri-list")
                        .content("/projects/" + project.getId()))
               .andExpect(status().isNoContent());
    }

    @Test
    public void updateProjectAllowedForAdmin() throws Exception {
        WorkTime workTime = workTimeDataOnDemand.getRandomObject();
        Project project = projectDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/workTimes/" + workTime.getId() + "/project")
                        .session(adminSession())
                        .header("Content-Type", "text/uri-list")
                        .content("/projects/" + project.getId()))
               .andExpect(status().isNoContent());
    }

    @Test
    public void updateProjectForbiddenForSupervisor() throws Exception {
        WorkTime workTime = workTimeDataOnDemand.getRandomObject();
        Project project = projectDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/workTimes/" + workTime.getId() + "/project")
                        .session(supervisorSession())
                        .header("Content-Type", "text/uri-list")
                        .content("/projects/" + project.getId()))
               .andExpect(status().isForbidden());
    }

    @Test
    public void findByEmployeeAndDateOrderByStartTimeAscAllowedForOwner() throws Exception {
        WorkTime workTime = workTimeDataOnDemand.getRandomObject();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        mockMvc.perform(
                get("/workTimes/search/findByEmployeeAndDateOrderByStartTimeAsc")
                        .session(employeeSession(workTime.getEmployee().getId()))
                        .param("employee", workTime.getEmployee().getId().toString())
                        .param("date", sdf.format(workTime.getDate())))
               .andExpect(status().isOk())
               .andExpect(jsonPath("_embedded.workTimes[0].id", isNotNull()));
    }

    @Test
    public void findByEmployeeAndDateOrderByStartTimeAscAllowedForSupervisor() throws Exception {
        WorkTime workTime = workTimeDataOnDemand.getRandomObject();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        mockMvc.perform(
                get("/workTimes/search/findByEmployeeAndDateOrderByStartTimeAsc")
                        .session(supervisorSession())
                        .param("employee", workTime.getEmployee().getId().toString())
                        .param("date", sdf.format(workTime.getDate())))
               .andExpect(status().isOk())
               .andExpect(jsonPath("_embedded.workTimes[0].id", isNotNull()));
    }

    @Test
    public void findByEmployeeAndDateBetweenOrderByDateAscStartTimeAscAllowedForOwner() throws Exception {
        WorkTime workTime1 = workTimeDataOnDemand.getRandomObject();
        WorkTime workTime2 = workTimeDataOnDemand.getRandomObject();
        workTime2.setEmployee(workTime1.getEmployee());
        workTimeRepository.saveAndFlush(workTime2);
        Date low, high;
        if(workTime1.getDate().compareTo(workTime2.getDate()) <= 0) {
            low = workTime1.getDate();
            high = workTime2.getDate();
        } else {
            low = workTime2.getDate();
            high = workTime1.getDate();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        mockMvc.perform(
                get("/workTimes/search/findByEmployeeAndDateBetweenOrderByDateAscStartTimeAsc")
                        .session(employeeSession(workTime1.getEmployee().getId()))
                        .param("employee", workTime1.getEmployee().getId().toString())
                        .param("start", sdf.format(low))
                        .param("end", sdf.format(high)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("_embedded.workTimes[0].id", isNotNull()));
    }

    @Test
    public void findByEmployeeAndDateBetweenOrderByDateAscStartTimeAscAllowedForSupervisor() throws Exception {
        WorkTime workTime1 = workTimeDataOnDemand.getRandomObject();
        WorkTime workTime2 = workTimeDataOnDemand.getRandomObject();
        workTime2.setEmployee(workTime1.getEmployee());
        workTimeRepository.saveAndFlush(workTime2);
        Date low, high;
        if(workTime1.getDate().compareTo(workTime2.getDate()) <= 0) {
            low = workTime1.getDate();
            high = workTime2.getDate();
        } else {
            low = workTime2.getDate();
            high = workTime1.getDate();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        mockMvc.perform(
                get("/workTimes/search/findByEmployeeAndDateBetweenOrderByDateAscStartTimeAsc")
                        .session(supervisorSession())
                        .param("employee", workTime1.getEmployee().getId().toString())
                        .param("start", sdf.format(low))
                        .param("end", sdf.format(high)))
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
        WorkTime workTime1 = workTimeDataOnDemand.getRandomObject();
        WorkTime workTime2 = workTimeDataOnDemand.getRandomObject();
        workTime2.setEmployee(workTime1.getEmployee());
        workTimeRepository.saveAndFlush(workTime2);
        Date low, high;
        if(workTime1.getDate().compareTo(workTime2.getDate()) <= 0) {
            low = workTime1.getDate();
            high = workTime2.getDate();
        } else {
            low = workTime2.getDate();
            high = workTime1.getDate();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        mockMvc.perform(
                get("/workTimes/search/findByEmployeeAndDateBetweenOrderByDateAscStartTimeAsc")
                        .session(employeeSession(workTime1.getEmployee().getId() + 1))
                        .param("employee", workTime1.getEmployee().getId().toString())
                        .param("start", sdf.format(low))
                        .param("end", sdf.format(high)))
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
        WorkTime workTime = workTimeDataOnDemand.getRandomObject();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        mockMvc.perform(
                get("/workTimes/search/findByEmployeeAndDateOrderByStartTimeAsc")
                        .session(employeeSession(workTime.getEmployee().getId()))
                        .param("employee", workTime.getEmployee().getId().toString())
                        .param("date", sdf.format(workTime.getDate())))
               .andExpect(status().isForbidden());
    }


    private String createWorkTimeJson(WorkTime workTime) throws Exception {
        StringWriter writer = new StringWriter();
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        jg.writeStartObject()
          .write("date", sdf.format(workTime.getDate()))
          .write("startTime", workTime.getStartTime().toString())
          .write("endTime", workTime.getEndTime().toString())
          .write("employee", "/employees/" + workTime.getEmployee().getId())
          .write("project", "/projects/" + workTime.getProject().getId())
          .write("comment", workTime.getComment());

        if (workTime.getId() != null) {
            jg.write("id", workTime.getId());
        }
        jg.writeEnd().close();
        return writer.toString();
    }
}
