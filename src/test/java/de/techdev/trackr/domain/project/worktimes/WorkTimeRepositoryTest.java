package de.techdev.trackr.domain.project.worktimes;

import de.techdev.test.TransactionalIntegrationTest;
import de.techdev.test.rest.AbstractDomainResourceSecurityTest;
import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.employee.EmployeeRepository;
import de.techdev.trackr.domain.project.Project;
import de.techdev.trackr.domain.project.ProjectRepository;
import de.techdev.trackr.util.LocalDateUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


@Sql("repositoryTest.sql")
@Sql(value = AbstractDomainResourceSecurityTest.EMPTY_DATABASE_FILE, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class WorkTimeRepositoryTest extends TransactionalIntegrationTest {

    @Autowired
    private WorkTimeRepository workTimeRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    /**
     * This finder must only respect the date but not time part of the second parameter.
     */
    @Test
    public void findByEmployeeAndDateOnlyRespectsDatePart() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Employee employee = employeeRepository.findOne(0L);
        List<WorkTime> workTimes = workTimeRepository.findByEmployeeAndDateOrderByStartTimeAsc(employee, sdf.parse("2014-03-04 09:00:00"));
        assertThat(workTimes.size(), is(2));
    }

    @Test
    public void findByEmployeeAndDateBetweenOrderByDateAscStartTimeAsc() throws Exception {
        Employee employee = employeeRepository.findOne(0L);

        Date low = LocalDateUtil.fromLocalDate(LocalDate.of(2014, 3, 4));
        Date high = LocalDateUtil.fromLocalDate(LocalDate.of(2014, 3, 5));

        List<WorkTime> all = workTimeRepository.findByEmployeeAndDateBetweenOrderByDateAscStartTimeAsc(employee, low, high);
        assertThat(all.size(), is(3));
    }

    @Test
    public void findByProjectAndDateBetweenOrderByDateAscStartTimeAsc() throws Exception {
        Project project = projectRepository.findOne(0L);

        Date low = LocalDateUtil.fromLocalDate(LocalDate.of(2014, 3, 4));
        Date high = LocalDateUtil.fromLocalDate(LocalDate.of(2014, 3, 5));

        List<WorkTime> all = workTimeRepository.findByProjectAndDateBetweenOrderByDateAscStartTimeAsc(project, low, high);
        assertThat(all.size(), is(3));
    }
}
