package de.techdev.trackr.domain.project;

import de.techdev.trackr.domain.employee.Employee;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Moritz Schulze
 */
public class WorkTimeControllerTest {

    private WorkTimeController workTimeController;

    @Before
    public void setUp() throws Exception {
        workTimeController = new WorkTimeController();
        workTimeController.repositoryEntityLinks = mock(EntityLinks.class);
        Link link = mock(Link.class);
        when(link.withSelfRel()).thenReturn(link);
        when(workTimeController.repositoryEntityLinks.linkToSingleResource(Matchers.any(), Matchers.any())).thenReturn(link);
    }

    @Test
    public void convertStreamOfWorkTimesToMap() throws Exception {
        Map<Long,WorkTimeController.WorkTimeEmployee> map = workTimeController.convertStreamOfWorkTimesToMap(createTestWorktimes(), new HashMap<>());
        assertThat("The map must contain two employee mappings", map.keySet().size(), is(2));
        assertThat("One mapping must be for id 1", map.get(1L), isNotNull());
        assertThat("One mapping must be for id 2", map.get(2L), isNotNull());
        assertThat("The mapping for id 1 must have two work times", map.get(1L).getWorkTimes().size(), is(2));
        assertThat("The mapping for id 2 must have one work time", map.get(2L).getWorkTimes().size(), is(1));
    }

    private List<WorkTime> createTestWorktimes() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setFirstName("employee_1");
        employee1.setLastName("employee_1");

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setFirstName("employee_2");
        employee2.setLastName("employee_2");

        WorkTime workTime11 = new WorkTime();
        workTime11.setEmployee(employee1);
        workTime11.setDate(sdf.parse("2014-01-01"));
        workTime11.setStartTime(Time.valueOf("09:00:00"));
        workTime11.setEndTime(Time.valueOf("15:00:00"));

        WorkTime workTime12 = new WorkTime();
        workTime12.setEmployee(employee1);
        workTime12.setDate(sdf.parse("2014-02-02"));
        workTime12.setStartTime(Time.valueOf("16:00:00"));
        workTime12.setEndTime(Time.valueOf("17:00:00"));

        WorkTime workTime21 = new WorkTime();
        workTime21.setEmployee(employee2);
        workTime21.setDate(sdf.parse("2014-01-01"));
        workTime21.setStartTime(Time.valueOf("09:00:00"));
        workTime21.setEndTime(Time.valueOf("17:00:00"));
        return asList(workTime11, workTime12, workTime21);
    }

    @Test
    public void customWorkTimeHourCalculation() throws Exception {
        WorkTime workTime21 = new WorkTime();
        workTime21.setDate(new Date());
        workTime21.setStartTime(Time.valueOf("09:00:00"));
        workTime21.setEndTime(Time.valueOf("17:00:00"));
        WorkTimeController.CustomWorkTime customWorkTime = WorkTimeController.CustomWorkTime.valueOf(workTime21);
        assertThat(customWorkTime.getEnteredMinutes(), is(480L));
    }

    @Test
    public void reduceWorkTimes() throws Exception {
        List<WorkTimeController.CustomWorkTime> reduced = WorkTimeController.WorkTimeEmployee.reduceAndSortWorktimes(createCustomWorkTimes());
        assertThat(reduced.size(), is(2));
        assertThat(reduced.get(0).getEnteredMinutes(), is(300L));
        assertThat(reduced.get(1).getEnteredMinutes(), is(480L));
    }

    private List<WorkTimeController.CustomWorkTime> createCustomWorkTimes() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        WorkTimeController.CustomWorkTime ctw1 = new WorkTimeController.CustomWorkTime();
        ctw1.setDate(sdf.parse("2014-01-01"));
        ctw1.setEnteredMinutes(240L);
        WorkTimeController.CustomWorkTime ctw2 = new WorkTimeController.CustomWorkTime();
        ctw2.setDate(sdf.parse("2014-01-01"));
        ctw2.setEnteredMinutes(60L);
        WorkTimeController.CustomWorkTime ctw3 = new WorkTimeController.CustomWorkTime();
        ctw3.setDate(sdf.parse("2014-01-02"));
        ctw3.setEnteredMinutes(480L);
        return asList(ctw1, ctw2, ctw3);
    }
}
