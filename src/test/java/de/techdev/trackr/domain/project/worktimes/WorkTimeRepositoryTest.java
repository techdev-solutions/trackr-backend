package de.techdev.trackr.domain.project.worktimes;

import static org.echocat.jomon.testing.BaseMatchers.isGreaterThanOrEqualTo;
import static org.echocat.jomon.testing.BaseMatchers.isNotEmpty;
import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.techdev.trackr.TransactionalIntegrationTest;

/**
 * @author Moritz Schulze
 */
public class WorkTimeRepositoryTest extends TransactionalIntegrationTest {

    @Autowired
    private WorkTimeRepository workTimeRepository;

    @Autowired
    private WorkTimeDataOnDemand workTimeDataOnDemand;

    @Before
    public void setUp() throws Exception {
        workTimeDataOnDemand.init();
    }

    @Test
    public void one() throws Exception {
        WorkTime workTime = workTimeDataOnDemand.getRandomObject();
        WorkTime one = workTimeRepository.findOne(workTime.getId());
        assertThat(one, isNotNull());
    }

    @Test
    public void all() throws Exception {
        List<WorkTime> all = workTimeRepository.findAll();
        assertThat(all, isNotEmpty());
    }

    @Test
    public void findByEmployeeAndDate() throws Exception {
        WorkTime workTime = workTimeDataOnDemand.getRandomObject();
        List<WorkTime> workTimes = workTimeRepository.findByEmployeeAndDateOrderByStartTimeAsc(workTime.getEmployee(), workTime.getDate());
        assertThat(workTimes, isNotEmpty());
    }

    /**
     * This finder must only respect the date but not time part of the second parameter.
     *
     * @throws Exception
     */
    @Test
    public void findByEmployeeAndDateOnlyRespectsDatePart() throws Exception {
    	//TODO obsolete
        WorkTime workTime1 = workTimeRepository.findOne(20L);
        workTime1.setDate(LocalDate.parse("2014-03-04"));
        workTimeRepository.save(workTime1);
        WorkTime workTime2 = workTimeRepository.findOne(21L);
        workTime2.setEmployee(workTime1.getEmployee());
        workTime2.setDate(LocalDate.parse("2014-03-04"));
        workTimeRepository.save(workTime2);

        List<WorkTime> workTimes = workTimeRepository.findByEmployeeAndDateOrderByStartTimeAsc(workTime1.getEmployee(), LocalDate.parse("2014-03-04"));
        assertThat(workTimes.size(), isGreaterThanOrEqualTo(2));
    }

    @Test
    public void findByEmployeeAndDateBetweenOrderByDateAscStartTimeAsc() throws Exception {
        WorkTime workTime1 = workTimeRepository.findOne(20L);
        WorkTime workTime2 = workTimeRepository.findOne(21L);
        workTime2.setEmployee(workTime1.getEmployee());
        workTimeRepository.save(workTime2);
        LocalDate low, high;
        if(workTime1.getDate().compareTo(workTime2.getDate()) <= 0) {
            low = workTime1.getDate();
            high = workTime2.getDate();
        } else {
            low = workTime2.getDate();
            high = workTime1.getDate();
        }
        List<WorkTime> all = workTimeRepository.findByEmployeeAndDateBetweenOrderByDateAscStartTimeAsc(workTime1.getEmployee(), low, high);
        assertThat(all.size(), isGreaterThanOrEqualTo(2));
    }

    @Test
    public void findByProjectAndDateBetweenOrderByDateAscStartTimeAsc() throws Exception {
        WorkTime workTime1 = workTimeRepository.findOne(20L);
        WorkTime workTime2 = workTimeRepository.findOne(21L);
        workTime2.setProject(workTime1.getProject());
        workTimeRepository.save(workTime2);
        LocalDate low, high;
        if(workTime1.getDate().compareTo(workTime2.getDate()) <= 0) {
            low = workTime1.getDate();
            high = workTime2.getDate();
        } else {
            low = workTime2.getDate();
            high = workTime1.getDate();
        }
        List<WorkTime> all = workTimeRepository.findByProjectAndDateBetweenOrderByDateAscStartTimeAsc(workTime1.getProject(), low, high);
        assertThat(all.size(), isGreaterThanOrEqualTo(2));
    }
}
