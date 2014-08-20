package de.techdev.trackr.domain.project.worktimes;

import de.techdev.trackr.domain.project.worktimes.CustomWorkTime;
import de.techdev.trackr.domain.project.worktimes.WorkTime;
import org.junit.Test;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Moritz Schulze
 */
public class CustomWorkTimeTest {
    @Test
    public void reduceAndSortWorktimes() throws Exception {
        List<CustomWorkTime> reduced = CustomWorkTime.reduceAndSortWorktimes(createCustomWorkTimes());
        assertThat(reduced.size(), is(2));
        assertThat(reduced.get(0).getEnteredMinutes(), is(300L));
        assertThat(reduced.get(1).getEnteredMinutes(), is(480L));
    }

    private List<CustomWorkTime> createCustomWorkTimes() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        CustomWorkTime ctw1 = new CustomWorkTime();
        ctw1.setDate(sdf.parse("2014-01-01"));
        ctw1.setEnteredMinutes(240L);
        CustomWorkTime ctw2 = new CustomWorkTime();
        ctw2.setDate(sdf.parse("2014-01-01"));
        ctw2.setEnteredMinutes(60L);
        CustomWorkTime ctw3 = new CustomWorkTime();
        ctw3.setDate(sdf.parse("2014-01-02"));
        ctw3.setEnteredMinutes(480L);
        return asList(ctw1, ctw2, ctw3);
    }

    @Test
    public void customWorkTimeHourCalculation() throws Exception {
        WorkTime workTime21 = new WorkTime();
        workTime21.setDate(new Date());
        workTime21.setStartTime(Time.valueOf("09:00:00"));
        workTime21.setEndTime(Time.valueOf("17:00:00"));
        CustomWorkTime customWorkTime = CustomWorkTime.valueOf(workTime21);
        assertThat(customWorkTime.getEnteredMinutes(), is(480L));
    }
}
