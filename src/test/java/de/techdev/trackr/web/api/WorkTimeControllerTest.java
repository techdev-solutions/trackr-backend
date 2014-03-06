package de.techdev.trackr.web.api;

import de.techdev.trackr.domain.Employee;
import de.techdev.trackr.domain.WorkTime;
import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Moritz Schulze
 */
public class WorkTimeControllerTest {

    private WorkTimeController workTimeController;

    @Before
    public void setUp() throws Exception {
        workTimeController = new WorkTimeController();
    }

    @Test
    public void convertStreamOfWorkTimesToMap() throws Exception {
        Map<Long,WorkTimeController.WorkTimeEmployee> map = workTimeController.convertStreamOfWorkTimesToMap(createTestWorktimes());
        assertThat("The map must contain two employee mappings", map.keySet().size(), is(2));
        assertThat("One mapping must be for id 1", map.get(1L), isNotNull());
        assertThat("One mapping must be for id 2", map.get(2L), isNotNull());
        assertThat("The mapping for id 1 must have two work times", map.get(1L).getWorkTimes().size(), is(2));
        assertThat("The mapping for id 2 must have one work time", map.get(2L).getWorkTimes().size(), is(1));
    }

    private List<WorkTime> createTestWorktimes() {
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
        workTime11.setDate(new Date());
        workTime11.setStartTime(Time.valueOf("09:00:00"));
        workTime11.setEndTime(Time.valueOf("15:00:00"));

        WorkTime workTime12 = new WorkTime();
        workTime12.setEmployee(employee1);
        workTime12.setDate(new Date());
        workTime12.setStartTime(Time.valueOf("16:00:00"));
        workTime12.setEndTime(Time.valueOf("17:00:00"));

        WorkTime workTime21 = new WorkTime();
        workTime21.setEmployee(employee2);
        workTime21.setDate(new Date());
        workTime21.setStartTime(Time.valueOf("09:00:00"));
        workTime21.setEndTime(Time.valueOf("17:00:00"));
        return asList(workTime11, workTime12, workTime21);
    }
}
